package keretrendszer.beadando.Repositories;

import keretrendszer.beadando.Models.Computer;
import keretrendszer.beadando.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
