package keretrendszer.Repositories;

import keretrendszer.Models.Videocard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideocardRepo extends JpaRepository<Videocard, Long> {
}
