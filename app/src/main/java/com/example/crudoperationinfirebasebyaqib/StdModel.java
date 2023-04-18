package com.example.crudoperationinfirebasebyaqib;

public class StdModel {

    private String st_id ,first_name ,last_name, email , username , password;

    public StdModel() {
    }

    public StdModel(String st_id, String first_name, String last_name, String email, String username, String password) {
        this.st_id = st_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public StdModel(String st_id, String first_name, String last_name, String email, String username) {
        this.st_id = st_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
