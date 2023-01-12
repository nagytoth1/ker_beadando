package keretrendszer.Models;

import javax.persistence.*;

@Entity
@Table(name = "gpus")
public class Videocard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private float clockrate;

    @Column(nullable = false, columnDefinition = "varchar(50) default 'vízhűtés'")
    private String coolant;

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

    public float getClockrate() {
        return clockrate;
    }

    public void setClockrate(float clockrate) {
        this.clockrate = clockrate;
    }

    public String getCoolant() {
        return coolant;
    }

    public void setCoolant(String coolant) {
        this.coolant = coolant;
    }
}
