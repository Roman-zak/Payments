package com.payments.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class User implements Serializable {
    private int id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private boolean blocked;
    private List<Account> accounts = new ArrayList<>();

    public User(String email, String password, String name, String surname, Role role, boolean blocked) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.blocked = blocked;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }



    public User() {
    }

    public User(int id, String email, String password, String name, String surname, Role role, boolean blocked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.blocked = blocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @NotBlank(message = "field should not be empty")
    @Email(message = "should contain \"@\" sign.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @NotBlank(message = "field should not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
            , message = "must contain minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @NotBlank(message = "field should not be empty")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotBlank(message = "field should not be empty")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return email.equals(user.email) && password.equals(user.password) && name.equals(user.name) && surname.equals(user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name, surname);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", role=" + role +
                ", blocked=" + blocked +
                '}';
    }
}
