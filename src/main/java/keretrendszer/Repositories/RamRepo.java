package keretrendszer.Repositories;

import keretrendszer.Models.Ram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RamRepo extends JpaRepository<Ram, Long> {
}
