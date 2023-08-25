package com.jtons.personnel.entity;


public class Dict {

  private long id;
  private String type;
  private String name;
  private String value;
  private String remark;
  private long parentId;
  private java.sql.Timestamp createAt;
  private java.sql.Timestamp updateAt;
  private java.sql.Timestamp delectAt;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }



  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }


  public long getParentId() {
    return parentId;
  }

  public void setParentId(long parentId) {
    this.parentId = parentId;
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


  public java.sql.Timestamp getDelectAt() {
    return delectAt;
  }

  public void setDelectAt(java.sql.Timestamp delectAt) {
    this.delectAt = delectAt;
  }

}
