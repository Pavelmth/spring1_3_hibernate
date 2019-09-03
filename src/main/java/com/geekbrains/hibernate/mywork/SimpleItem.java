package com.geekbrains.hibernate.mywork;

import javax.persistence.*;

@Entity
@Table(name = "simple_items")
public class SimpleItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "cost")
    private Integer cost;
}