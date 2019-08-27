package com.javagda25.jdbc;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode

public class Student {
    private Long id;
    private String name;
    private int age;
    private Double average;
    private boolean alive;


    public Student(String name, int age, Double average, boolean alive) {
        this.name = name;
        this.age = age;
        this.average = average;
        this.alive = alive;
    }
}
