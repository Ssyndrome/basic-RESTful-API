package com.thoughtworks.gradstepupspring.domain;

public class User {
    private Integer id;
    private String name;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User(Integer originalId, String originalName) {
        this.id = originalId;
        this.name = originalName;
    }
}
