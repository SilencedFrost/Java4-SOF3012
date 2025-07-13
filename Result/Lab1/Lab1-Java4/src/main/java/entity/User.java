package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

    public void printInfo() {
        System.out.println("-------------------------");
        System.out.println("ID: " + this.id);
        System.out.println("Password: " + this.password);
        System.out.println("Full Name: " + this.fullname);
        System.out.println("Email: " + this.email);
        System.out.println("Admin: " + (this.admin ? "Yes" : "No"));
    }
}
