package keretrendszer.beadando.Repositories;

import keretrendszer.beadando.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface UserRepo extends JpaRepository<User,Long> {

    //megadhatja a form-ban felhasználónév helyett az email címét is, esetleg nézze meg ott is, hogy létezik-e ilyen email cím
    User findByEmail(String email);
    User findByUsername(String username);
}
