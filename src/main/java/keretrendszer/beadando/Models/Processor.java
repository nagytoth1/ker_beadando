package keretrendszer.beadando.Models;

import javax.persistence.*;

@Entity
@Table(name = "cpus")
public class Processor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, columnDefinition = "tinyint default 4")
    private byte numOfCores;
    @Column(nullable = false, columnDefinition = "tinyint default 12")
    private byte cacheInMB;
    @Column(nullable = false, scale = 10, precision = 4, columnDefinition = "float default 3.20")
    private float clockrate;
}
