package com.example.easytest;

import java.util.Date;

public class Test {
    private Date testDate;
    private String testLocation;
    private int testNo;
    private double testPrice;
    private String testDuration;

    public Test()
    {

    }
    public Test(Date testDate, String testLocation, int testNo, double testPrice, String testDuration) {
        this.testDate = testDate;
        this.testLocation = testLocation;
        this.testNo = testNo;
        this.testPrice = testPrice;
        this.testDuration = testDuration;
    }


    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getTestLocation() {
        return testLocation;
    }

    public void setTestLocation(String testLocation) {
        this.testLocation = testLocation;
    }

    public int getTestNo() {
        return testNo;
    }

    public void setTestNo(int testNo) {
        this.testNo = testNo;
    }

    public double getTestPrice() {
        return testPrice;
    }

    public void setTestPrice(double testPrice) {
        this.testPrice = testPrice;
    }

    public String getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(String testDuration) {
        this.testDuration = testDuration;
    }
}
