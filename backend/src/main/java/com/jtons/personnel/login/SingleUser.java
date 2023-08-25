package com.jtons.personnel.login;

public class SingleUser {
    private Long id;
    private String account;
    private String role;
    private static SingleUser singleUser = new SingleUser();
    //将构造器设置为private禁止通过new进行实例化
    private SingleUser() {

    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static SingleUser getInstance() {
        return singleUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }
}
