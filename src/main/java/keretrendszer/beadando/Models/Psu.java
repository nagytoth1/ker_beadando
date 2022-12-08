package keretrendszer.beadando.Models;

import javax.persistence.*;

@Entity
@Table(name = "psus")
public class Psu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, columnDefinition = "varchar(100) default 'moduláris'")
    private String type;
    @Column(nullable = false, columnDefinition = "smallint default 500")
    private short performance;
}
