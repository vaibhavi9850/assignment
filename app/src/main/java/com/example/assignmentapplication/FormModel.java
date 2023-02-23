package com.example.assignmentapplication;

public class FormModel {
    int id;
    String name,email,number,file,clgname;

    public FormModel(int id, String name, String email, String number, String file, String clgname) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.number = number;
        this.file = file;
        this.clgname = clgname;
    }

    public FormModel(String name, String email, String number, String file, String clgname) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.file = file;
        this.clgname = clgname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getClgname() {
        return clgname;
    }

    public void setClgname(String clgname) {
        this.clgname = clgname;
    }
}
