package com.finley.module.user.entity;

/**
 * Created by 郑远锋 on 2017/2/28.
 */
public class Student extends User {
    private Integer studentId;      //学号
    private String  major;
    private String className;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
