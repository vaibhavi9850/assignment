package com.example.assignmentapplication;

public class UniversityModel {
    String University_Name;
    String University_Logo;

    public UniversityModel(String university_Name, String university_Logo) {
        University_Name = university_Name;
        University_Logo = university_Logo;
    }

    public String getUniversity_Name() {
        return University_Name;
    }

    public void setUniversity_Name(String university_Name) {
        University_Name = university_Name;
    }

    public String getUniversity_Logo() {
        return University_Logo;
    }

    public void setUniversity_Logo(String university_Logo) {
        University_Logo = university_Logo;
    }
}
