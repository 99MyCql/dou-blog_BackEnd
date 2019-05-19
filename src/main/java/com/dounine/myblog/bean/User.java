package com.dounine.myblog.bean;

public class User {

    private int id; // 主键

    private String phone;

    private String name;

    private String password;

    private int gender; // 0 女，1 男

    private String personalBrief;

    private String email;

    public User() {}

    public User(String phone, String name, String password, int gender, String personalBrief, String email) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.personalBrief = personalBrief;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void setPersonalBrief(String personalBrief) {
        this.personalBrief = personalBrief;
    }

    public String getPersonalBrief() {
        return personalBrief;
    }
}
