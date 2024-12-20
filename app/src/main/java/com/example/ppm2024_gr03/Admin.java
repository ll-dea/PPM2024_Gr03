package com.example.ppm2024_gr03;

public class Admin {
    private final String email;
    private final String name;
    private final String surname;
    private final String phone;

    public Admin(String email, String name, String surname, String phone) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }
}
