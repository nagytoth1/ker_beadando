package keretrendszer.beadando.Models;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@EntityScan
@EnableJpaRepositories
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false) //unique
    private String email;
    @Column(name = "username", nullable = false) //unique
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(targetEntity=Role.class, fetch=FetchType.EAGER)
    private Role role;

    public User(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
    public User(){}

    public long getId() {
        return id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User otherUser = (User) o;
        return  this.username.equals(otherUser.username) &&
                this.password.equals(otherUser.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    public User getShallowCopy(){
        return new User(this.username, this.password, this.email, this.role);
    }
}
