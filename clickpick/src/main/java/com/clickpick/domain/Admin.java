package com.clickpick.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Admin {
    @Id
    @Column(name = "admin_id")
    private String id;
    @Column(name = "admin_pw",nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String phone;
}
