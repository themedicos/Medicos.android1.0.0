package com.example.medicos.Model;

public class UserInfo {


    private String log_in_as, username, year_of_birth, user_gender;

    public UserInfo(String log_in_as, String username, String year_of_birth, String user_gender) {
        this.log_in_as = log_in_as;
        this.username = username;
        this.year_of_birth = year_of_birth;
        this.user_gender = user_gender;
    }

    public UserInfo() {
    }

    public String getLog_in_as() {
        return log_in_as;
    }

    public void setLog_in_as(String log_in_as) {
        this.log_in_as = log_in_as;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(String year_of_birth) {
        this.year_of_birth = year_of_birth;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }
}
