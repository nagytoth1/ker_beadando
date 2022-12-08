package keretrendszer.beadando.Models;

import javax.persistence.*;

@Entity
@Table(name = "systems")
public class Opsystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
