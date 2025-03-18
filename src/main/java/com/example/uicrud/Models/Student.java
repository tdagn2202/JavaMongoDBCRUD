package com.example.uicrud.Models;

public class Student {
    private String studentName;
    private int studentAge;
    private int classId;
    private String className;

    public Student(String studentName, int studentAge, int classId, String className) {
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.classId = classId;
        this.className = className;
    }

    public Student() {

    }

    public String getStudentName() { return studentName; }
    public int getStudentAge() { return studentAge; }
    public int getClassId() { return classId; }
    public String getClassName() { return className; }

}
