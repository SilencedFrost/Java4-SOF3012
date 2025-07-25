package com.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @Column(name = "RoleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(name = "RoleName", unique = true)
    private String roleName;
    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(Integer roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public void addUser(User user) {
        if (user == null) return;

        if (this.users == null) this.users = new ArrayList<>();

        Role currentRole = user.getRole();
        if (currentRole != null) {
            if (this.users.contains(user)) return;
            currentRole.removeUser(user);
        }

        this.users.add(user);
        user.setRole(this);
    }

    public void removeUser(User user) {
        if (user == null || this.users == null || this.users.isEmpty()) return;

        if (this.users.contains(user)) {
            this.users.remove(user);
            user.setRole(null);
        }
    }
    
    public boolean hasUser(User user) {
        return user != null && this.users != null && this.users.contains(user);
    }

    public int getUserCount() {
        return this.users != null ? this.users.size() : 0;
    }

    public List<User> getUsers() {
        return this.users != null ? new ArrayList<>(this.users) : new ArrayList<>();
    }
}
