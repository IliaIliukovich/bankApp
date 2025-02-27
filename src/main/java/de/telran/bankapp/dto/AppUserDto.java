package de.telran.bankapp.dto;

import de.telran.bankapp.entity.enums.UserRole;

public class AppUserDto {
    private String id;
    private String email;
    private String password;
    private UserRole role;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }


}
