package org.itstep.model.entity;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;

public class User  {
    private Long id;
    private String username;
    private List<Role> authorities = new ArrayList<>();
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
