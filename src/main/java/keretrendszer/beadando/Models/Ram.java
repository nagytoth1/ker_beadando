package keretrendszer.beadando.Models;

import javax.persistence.*;

@Entity
@Table(name = "rams")
public class Ram {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 10, nullable = false)
    private String product_type;
    @Column(nullable = false, columnDefinition = "integer default 2600")
    private int speed;
    @Column(nullable = false, columnDefinition = "tinyint default 4")
    private byte size;
}
