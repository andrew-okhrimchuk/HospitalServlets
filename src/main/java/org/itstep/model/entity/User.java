package org.itstep.model.entity;


import org.itstep.model.entity.enums.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class User  {
    private Long id;
    private String username;
    private Role role;
    private String password;

    public Long getId() {
        return id;
    }

    public void setid(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setrole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

    public User() {
    }

    public User(Long id, String username, Role role, String password) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(username, user.username) &&
                role == user.role &&
                Objects.equals(password, user.password);
    }
    public static User.Builder builder() {
        return new User().new Builder();
    }

    public class Builder {

        public Builder() {}

        public User.Builder setId(Long id) {
            User. this.id = id;
            return this;
        }
        public User.Builder setUsername(String username) {
            User.  this.username = username;
            return this;
        }
        public User.Builder setRole(Role role) {
            User.  this.role = role;
            return this;
        }
        public User.Builder setPassword(String password) {
            User.  this.password = password;
            return this;
        }

        public User build() {
            return User.this;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, role, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
