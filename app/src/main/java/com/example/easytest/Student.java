package com.example.easytest;

import java.util.Date;

public class Student {
    private String name;
    private Test testDetails;
    private int NumberOfLessons;
    private Date upComeLesson;
    private Boolean HasTeyoria;
    private double howMuchPaid;
    public Student ()
    {

    }

    public Student(String name, int numberOfLessons, Date upComeLesson, Boolean hasTeyoria,double howMuchPaid, Test testDetails) {
        this.name = name;
        this.testDetails=testDetails;
        this.NumberOfLessons = numberOfLessons;
        this.upComeLesson = upComeLesson;
        HasTeyoria = hasTeyoria;
        this.howMuchPaid=howMuchPaid;
    }
    public double getHowMuchPaid() {
        return howMuchPaid;
    }

    public void setHowMuchPaid(double howMuchPaid) {
        this.howMuchPaid = howMuchPaid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Test getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(Test testDetails) {
        this.testDetails = testDetails;
    }



    public int getNumberOfLessons() {
        return NumberOfLessons;
    }

    public void setNumberOfLessons(int numberOfLessons) {
        NumberOfLessons = numberOfLessons;
    }

    public Date getUpComeLesson() {
        return upComeLesson;
    }

    public void setUpComeLesson(Date upComeLesson) {
        this.upComeLesson = upComeLesson;
    }

    public Boolean getHasTeyoria() {
        return HasTeyoria;
    }

    public void setHasTeyoria(Boolean hasTeyoria) {
        HasTeyoria = hasTeyoria;
    }
}
