package com.clickpick.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Admin {
    @Id
    @Column(name = "admin_id")
    private String id;
    @Column(name = "admin_pw")
    private String password;
    private String name;
    private String phone;
}
