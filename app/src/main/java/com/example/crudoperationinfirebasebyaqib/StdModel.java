package com.example.crudoperationinfirebasebyaqib;

public class StdModel {

    private String st_id ,name , email , phone , password;

    public StdModel() {
    }

    public StdModel(String st_id, String name, String email, String phone, String password) {
        this.st_id = st_id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public String getSt_id() {
        return st_id;
    }

    public void setSt_id(String st_id) {
        this.st_id = st_id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
