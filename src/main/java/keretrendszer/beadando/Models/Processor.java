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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getCores() {
        return cores;
    }

    public void setCores(byte cores) {
        this.cores = cores;
    }

    public byte getCachemb() {
        return cachemb;
    }

    public void setCachemb(byte cachemb) {
        this.cachemb = cachemb;
    }

    public float getClockrate() {
        return clockrate;
    }

    public void setClockrate(float clockrate) {
        this.clockrate = clockrate;
    }
}
