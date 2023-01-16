package keretrendszer.Repositories;

import keretrendszer.Models.Processor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessorRepo extends JpaRepository<Processor, Long> {
}
