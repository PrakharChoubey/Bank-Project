package com.bank.registerloginservice.dto;

import java.util.Objects;

public class UserDetails {

    private long id;
    private String username;
    private String password;

    public UserDetails(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDetails() {
    }

    public UserDetails(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetails that = (UserDetails) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
