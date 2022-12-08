package keretrendszer.beadando.Repositories;

import keretrendszer.beadando.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Role, Long> {
}
