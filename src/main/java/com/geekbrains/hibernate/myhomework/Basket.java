package com.geekbrains.hibernate.myhomework;

import javax.persistence.*;

@Entity
@Table(name = "basket")
public class Basket {

    private Integer costomerId;

    private Integer productId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Integer getCostomerId() {
        return costomerId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Product getProduct() {
        return product;
    }

    public Basket() {
    }
}
