package keretrendszer.beadando.Models;

import javax.persistence.*;
@Entity
@Table(name = "computers")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String motherboard;

    @ManyToOne //ugyanaz a ram nem tartozhat más géphez
    @JoinColumn(name="ram_id", nullable=false)
    private Ram ram;

    @Column(nullable = false, columnDefinition = "tinyint default 0")
    private byte ram_quantity;

    @ManyToOne
    @JoinColumn(name="cpu_id", nullable=false)
    private Processor processor;

    @ManyToOne
    @JoinColumn(name="gpu_id", nullable=false)
    private Videocard videocard;

    @ManyToOne
    @JoinColumn(name="psu_id", nullable=false)
    private Videocard powerSupply;

    @ManyToOne
    @JoinColumn(name="system_id", nullable=false)
    private Opsystem opsystem;
}
