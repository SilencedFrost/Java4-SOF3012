package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleid")
    private String roleId;
    @Column(name = "UserId")
    private String userId;
    @Column(name = "roletype")
    private String role;

    public Role(String userId, String role) {
        this.userId = userId;
        setRole(role);
    }

    public void setRole(String role) {
        this.role = (role == null || role.trim().isEmpty()) ? "User" : role;
    }
}
