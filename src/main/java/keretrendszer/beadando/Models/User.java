package keretrendszer.beadando.Models;

import org.apache.tomcat.websocket.AuthenticationException;
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

    @Column(name = "email", nullable = false) //unique
    private String email;
    @Column(name = "username", nullable = false) //unique
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(targetEntity=Role.class, fetch=FetchType.EAGER)
    private Role role;

    public User(String username, String password, String email, Role role){
        this(0, username,password,email,role);
    }
    public User(long id, String username, String password, String email, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
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
    //mivel a jelszót nem plain text-ben tároljuk le, hanem hash-elve, ezért nem itt ellenőrizzük a megadott jelszót
    public void setPassword(String password) throws AuthenticationException { this.password = password; }

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
        if(!isEmail(value))
            throw new AuthenticationException("A megadott adat nem e-mail cím!");
    }

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
        return new User(this.id, this.username, this.password, this.email, this.role);
    }

    @Override
    public String toString() {
        return String.format("User %d: %s [%s]", id, username, role.getName());
    }

    /*public void setUser(User foundUser) throws AuthenticationException {
        this.setId(foundUser.getId());
        this.setUsername(foundUser.getUsername());
        this.setEmail(foundUser.getEmail());
        this.setRole(foundUser.getRole());
        this.setPassword(foundUser.getPassword());
    }*/
}
