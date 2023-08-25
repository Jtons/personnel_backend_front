package com.jtons.personnel.entity;


public class UserInfo {

  private long id;
  private String name;
  private String passward;
  private long sex;
  private String phone;
  private String email;
  private String area;
  private java.sql.Timestamp birthday;
  private String avatar;
  private String role;
  private java.sql.Timestamp createAt;
  private java.sql.Timestamp updateAt;
  private java.sql.Timestamp deleteAt;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassward() {
    return passward;
  }

  public void setPassward(String passward) {
    this.passward = passward;
  }

  public long getSex() {
    return sex;
  }

  public void setSex(long sex) {
    this.sex = sex;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }


  public java.sql.Timestamp getBirthday() {
    return birthday;
  }

  public void setBirthday(java.sql.Timestamp birthday) {
    this.birthday = birthday;
  }


  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }


  public java.sql.Timestamp getCreateAt() {
    return createAt;
  }

  public void setCreateAt(java.sql.Timestamp createAt) {
    this.createAt = createAt;
  }


  public java.sql.Timestamp getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(java.sql.Timestamp updateAt) {
    this.updateAt = updateAt;
  }


  public java.sql.Timestamp getDeleteAt() {
    return deleteAt;
  }

  public void setDeleteAt(java.sql.Timestamp deleteAt) {
    this.deleteAt = deleteAt;
  }

}
