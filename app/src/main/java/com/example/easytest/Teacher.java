package com.example.easytest;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.ArrayList;
public class Teacher {
    private String name;
    private String car;
    private double LessonPrice;
    private String drivingSchool;
    private String Notes;
    private int studentRating;
    private ArrayList<Student> students= new ArrayList <Student> ();

    public Teacher ()
    {

    }
    public Teacher(String name, double lessonPrice, String drivingSchool,String Notes, int studentRating, String car ) {
        this.name = name;
        this.LessonPrice = lessonPrice;
        this.drivingSchool = drivingSchool;
        this.Notes=Notes;
        this.studentRating=studentRating;
        this.car=car;
    }


    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public int getStudentRating() {
        return studentRating;
    }
    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public void setStudentRating(int studentRating) {
        this.studentRating = studentRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getLessonPrice() {
        return LessonPrice;
    }

    public void setLessonPrice(double lessonPrice) {
        LessonPrice = lessonPrice;
    }

    public String getDrivingSchool() {
        return drivingSchool;
    }

    public void setDrivingSchool(String drivingSchool) {
        this.drivingSchool = drivingSchool;
    }
}
