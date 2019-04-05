package com.example.mp8fbapp;

import java.io.Serializable;

public class Student implements Serializable {
    String email;
    int id;
    String name;
    String password;

    public Student() {}

    public Student(String stuEmail, int stuID, String stuName, String stuPass) {
        email = stuEmail;
        id =stuID;
        name = stuName;
        password = stuPass;
    }

    public int getID()       { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password;}

}
