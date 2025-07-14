package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "id")
    String id;
    @Column(name = "password")
    String password;
    @Column(name = "fullname")
    String fullname;
    @Column(name = "email")
    String email;
    @Column(name = "admin")
    Boolean admin = false;

    @PrePersist
    @PreUpdate
    private void ensureAdminNotNull() {
        if (admin == null) {
            admin = false;
        }
    }

    public void printInfo() {
        System.out.println("-------------------------");
        System.out.println("ID: " + this.id);
        System.out.println("Password: " + this.password);
        System.out.println("Full Name: " + this.fullname);
        System.out.println("Email: " + this.email);
        System.out.println("Admin: " + (this.admin ? "Yes" : "No"));
    }
}
