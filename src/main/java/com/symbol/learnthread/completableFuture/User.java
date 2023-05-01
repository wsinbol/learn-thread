package com.symbol.learnthread.completableFuture;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author SymbolWong
 * @description
 * @date 2023/5/1 15:59
 */
@Data
@AllArgsConstructor
public class User {
    private String name;
    private Integer age;
    private Double weight;
    private Double height;

    public User() {
        this.name = "default";
        this.age = 18;
        this.weight = 100.00;
        this.height = 10.00;
    }

    public User(String name, int i) {
        this.name = name;
        this.age = i;
        this.weight = 65.00;
        this.height = 180.00;
    }
}
