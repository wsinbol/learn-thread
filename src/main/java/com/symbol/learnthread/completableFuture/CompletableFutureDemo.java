package com.symbol.learnthread.completableFuture;

import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author SymbolWong
 * @description
 * @date 2023/5/1 15:02
 */
public class CompletableFutureDemo {

    /**
     * 有返回值的任务
     * 注意提交的参数是Supplier
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void runSupplyAsyncDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "Supply Test";
            }
        });
        String s = completableFuture.get();


        /**
         * 下方3个例子都是当得到结果之后，再接着执行callback。
         */
        /**
         * thenRun后面跟的是一个无参数、无返回值的方法，即 Runnable，所以最终的返回值是CompletableFuture<Void>类型。
         */
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 0;
            }
        }).thenRun(new Runnable() {
            @Override
            public void run() {

            }
        });

        /**
         * thenAccept 后面跟的是一个有参数、无返回值的方法，称为 Consumer，返回值也是CompletableFuture<Void>类型。
         * 顾名思义，只进不出，所以称为Consumer；前面的Supplier，是无参数，有返回值，只出不进，和Consumer刚好相反。
         */
        CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 0;
            }
        }).thenAccept(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.println("integer = " + integer);
            }
        });


        /**
         * thenApply 后面跟的是一个有参数、有返回值的方法，称为 Function。返回值是CompletableFuture<Integer>类型。
         */
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 0;
            }
        }).thenApply(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 3;
            }
        });

    }

    /**
     * 无返回值的任务
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void runAsyncDemo() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("\"Test task is running...\" = " + "Test task is running...");
                TimeUnit.SECONDS.sleep(1L);
            }
        });
        Void unused = completableFuture.get();
    }


    public static CompletableFuture<User> getUser(String name){
        CompletableFuture<User> future = CompletableFuture.supplyAsync(new Supplier<User>() {
            @SneakyThrows
            @Override
            public User get() {
                return new User(name,10);
            }
        });

        return future;
    }

    public static CompletableFuture<Integer> getAge(User user){
        // com.symbol.learnthread.completableFuture.RealData user = new com.symbol.learnthread.completableFuture.RealData("wang", 10);
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return user.getAge();
            }
        });
        return future;
    }

    public static CompletableFuture<String> plusOne(String num){
        return CompletableFuture.supplyAsync(() -> num + "a");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // CompletableFuture<String> completableFuture = new CompletableFuture<String>();
        // completableFuture.get();
        // completableFuture.complete("xxx");

        /**
         * 注意看下面2个的区别：thenCompose会将结果展平
         */
        CompletableFuture<CompletableFuture<Integer>> wang = getUser("wang").thenApply(user -> getAge(user));
        System.out.println("wang = " + wang.get().get());
        CompletableFuture<Integer> xin = getUser("xin").thenCompose(user -> getAge(user));
        System.out.println("xin = " + xin.get());


        /**
         * 下面展示 thenCombine 的用法
         * 第1个参数是一个CompletableFuture类型，第2个参数是一个函数，并且是一个BiFunction，也就是该函数有2个输入参数，1个返回值。
         * 从该接口的定义可以大致推测，它是要在2个 CompletableFuture完成之后，把2个CompletableFuture的返回值传进去，再额外做一些事情
         */
        CompletableFuture<Double> weightFuture = CompletableFuture.supplyAsync(new Supplier<Double>() {
            @Override
            public Double get() {
                return new User().getWeight();
            }
        });
        CompletableFuture<Double> heightFuture = CompletableFuture.supplyAsync(new Supplier<Double>() {
            @Override
            public Double get() {
                return new User().getHeight();
            }
        });

        CompletableFuture<Double> combine = weightFuture.thenCombine(heightFuture, (weight, height) -> weight / height);
        System.out.println("combine.get() = " + combine.get());


        /**
         * 上面的thenCompose和thenCombine只能组合2个CompletableFuture，而接下来的 allOf 和 anyOf 可以组合任意多个CompletableFuture
         * 下方是 allof 的例子
         */
        List<String> webPageLinks = Arrays.asList("1x","2","3x");
        // 并行处理N个任务
        List<CompletableFuture<String>> plusOneList = webPageLinks.stream().map(i -> plusOne(i)).collect(Collectors.toList());
        // 通过 allOf 等待所有任务完成，收集返回结果
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(plusOneList.toArray(new CompletableFuture[plusOneList.size()]));
        // CompletableFuture<Void> allOf = CompletableFuture.allOf(plusOneList.toArray(new CompletableFuture[0]));

        //因为allOf没有返回值，所以通过thenApply，给allFutures附上一个回调函数。
        // 在回调函数里面，依次调用每个future的get（）函数，获取到100个结果，存入List<String>
        CompletableFuture<List<String>> listCompletableFuture = allFutures.thenApply(i -> {
            return plusOneList.stream().map(j -> {
                try {
                    return j.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        });

        // 统计在这N个任务中，含有“x”的个数
        CompletableFuture<Long> xxxCount = listCompletableFuture.thenApply(m -> {
            return m.stream().filter(n -> n.contains("x")).count();
        });

        System.out.println("xxxCount.get() = " + xxxCount.get());

        /**
         * anyof 的用法示例
         */
        /**
         * anyOf 的含义是只要有任意一个 CompletableFuture 结束，就可以做接下来的事情，而无须像AllOf那样，等待所有的CompletableFuture结束
         * 但由于每个CompletableFuture的返回值类型都可能不同，任意一个，意味着无法判断是什么类型，所以anyOf的返回值是CompletableFuture<Object>类型
         */
        CompletableFuture<String> future1 = plusOne("1");
        CompletableFuture<String> future2 = plusOne("2");
        CompletableFuture<String> future3 = plusOne("3");
        CompletableFuture<Object> anyOf = CompletableFuture.anyOf(future1, future2, future3);
        System.out.println("anyOf.get() = " + anyOf.get());

    }
}
