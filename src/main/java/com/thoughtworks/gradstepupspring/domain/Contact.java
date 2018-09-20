package com.thoughtworks.gradstepupspring.domain;

public class Contact {
    private Integer id;
    private String name;
    private int age;
    private String gender;
    private long tel;

    public Contact() {
    }

    public Contact(Integer id, String name, int age, String gender, long tel) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.tel = tel;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public long getTel() {
        return tel;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public void updateField(Contact contact) {
        setName(contact.getName());
        setAge(contact.getAge());
        setGender(contact.getGender());
        setTel(contact.getTel());
    }
}
