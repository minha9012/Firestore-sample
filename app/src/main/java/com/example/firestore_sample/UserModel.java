package com.example.firestore_sample;

public class UserModel {
    String firstName;
    String lastName;
    Long born;

    public UserModel(String firstName, String lastName, Long born) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.born = born;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getBorn() {
        return born.intValue();
    }

    public void setBorn(Long born) {
        this.born = born;
    }
}
