package keretrendszer.beadando.Models;

import javax.persistence.*;

@Entity
@Table(name = "storages")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 40)
    private String port_type;
    @Column(nullable = false)
    private int capacity;
    @Column(nullable = false, scale = 10, precision = 2, columnDefinition = "float default 3.5")
    private float size;
    @Column(nullable = false, columnDefinition = "integer default 5000")
    private int rpm;
    @Column(nullable = false, columnDefinition = "smallint default 32")
    private short cacheInMB;
}
