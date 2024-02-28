package com.example.fptemployee.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date birthday;
    private int numberphone;
    private String email;

    public Employee() {
    }

    public Employee(String name, Date birthday, int numberphone, String email) {
        this.name = name;
        this.birthday = birthday;
        this.numberphone = numberphone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getNumberphone() {
        return numberphone;
    }

    public void setNumberphone(int numberphone) {
        this.numberphone = numberphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}