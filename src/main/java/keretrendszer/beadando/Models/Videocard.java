package keretrendszer.beadando.Models;

import javax.persistence.*;

@Entity
@Table(name = "gpus")
public class Videocard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private float clockrate;

    @Column(nullable = false, columnDefinition = "varchar(50) default 'vízhűtés'")
    private String coolant;
}
