package keretrendszer.Models;

import keretrendszer.Validator.UserValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@EntityScan
@EnableJpaRepositories
public class User {
    private static final UserValidator validator = new UserValidator();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, length = 100) //unique
    private String email;
    @Column(name = "username", nullable = false, length = 100) //unique
    private String username;
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @ManyToOne(targetEntity=Role.class, fetch=FetchType.EAGER)
    private Role role;
    @Value("enabled:true")
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    public User(String username, String password, String email, Role role, boolean enabled){
        this(0, username,password,email,role, enabled);
    }
    public User(long id, String username, String password, String email, Role role, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.enabled = enabled;
    }
    public User(){}

    public long getId() {
        return id;
    }
    public void setId(long value) { this.id = value; }
    public String getUsername() {
        return username;
    }

    public void setUsername(String value) { this.username = value;}
    public String getPassword() {
        return password;
    }
    public void setPassword(String value) { this.password = value;}

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String value) { this.email = value; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public static UserValidator getValidator(){
        return validator;
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
        return new User(this.id, this.username, this.password, this.email, this.role, this.enabled);
    }

    @Override
    public String toString() {
        return String.format("User %d: %s [%s]", id, username, role == null ? "-empty-" : role.getName());
    }
}
