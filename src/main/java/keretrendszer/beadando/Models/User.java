package keretrendszer.beadando.Models;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
@EntityScan
@EnableJpaRepositories
public class User {
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

    public void setUsername(String username) throws AuthenticationException {
        //TODO: setter-ben ha Exception van, akkor összetojja magát :'(
        //username must be between 4 and 40 characters
        if(username.length() < 4)
            throw new AuthenticationException("Túl rövid felhasználónév!");
        if(username.length() > 40)
            throw new AuthenticationException("Túl hosszú felhasználónév!");
        // Matches anything OTHER than an alphanumeric character including underscore. Equivalent to [^A-Za-z0-9_].
        Pattern p = Pattern.compile("\\W");
        Matcher m = p.matcher(username);
        if(m.find())
            throw new AuthenticationException("A felhasználónév nem tartalmazhat speciális karaktereket!");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    //since the passwd is stored not in plain text, but in a hashed way, therefore we can't validate here in setter
    public void setPassword(String password) { this.password = password; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String value) throws AuthenticationException {
        if(value.length() > 255)
            throw new AuthenticationException("A megadott e-mail cím túl hosszú");
        if(!isEmail(value))
            throw new AuthenticationException("A megadott adat nem e-mail cím!");
        this.email = value;
    }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    private static final String EMAIL_REGEX_PATTERN = "^[-a-z0-9~!$%^&*_=+}{\\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
    public static boolean isEmail(String value) {
        return Pattern.compile(EMAIL_REGEX_PATTERN)
                .matcher(value)
                .matches();
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
