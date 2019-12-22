package com.dounine.blog.bean;

public class User {

    private int id; // 主键

    private String phone;

    private String name;

    private String password;

    private int gender; // 0 女，1 男

    private String personalBrief;

    private String email;

    private String qq;

    private String birthday;

    private String role;

    public User() {}

    public User(String phone, String name, String password, int gender,
                String personalBrief, String email, String qq, String birthday, String role) {
        this.phone = phone;
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.personalBrief = personalBrief;
        this.email = email;
        this.qq = qq;
        this.birthday = birthday;
        this.role = role;
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

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQq() {
        return qq;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
