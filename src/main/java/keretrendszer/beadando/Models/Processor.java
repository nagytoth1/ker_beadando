package keretrendszer.beadando.Models;

import javax.persistence.*;

@Entity
@Table(name = "cpus")
public class Processor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, columnDefinition = "tinyint default 4")
    private byte cores;
    @Column(nullable = false, columnDefinition = "tinyint default 12")
    private byte cachemb;
    @Column(nullable = false, scale = 10, precision = 4, columnDefinition = "float default 3.20")
    private float clockrate;
}
