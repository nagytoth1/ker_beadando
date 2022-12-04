package keretrendszer.beadando.Repositories;

import keretrendszer.beadando.Models.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerRepo extends JpaRepository<Computer, Long> {
}
