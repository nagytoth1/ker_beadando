package keretrendszer.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "storages")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 40)
    private String port;
    @Column(nullable = false)
    private int capacitygb;
    @Column(nullable = false, scale = 10, precision = 2, columnDefinition = "float default 3.5")
    private float size;
    @Column(nullable = false, columnDefinition = "integer default 5000")
    private int rpm;
    @Column(nullable = false, columnDefinition = "smallint default 32")
    private short cachemb;

    @JsonBackReference
    @ManyToMany(mappedBy = "storageSet", fetch = FetchType.EAGER)
    private Set<Computer> computerSet;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getCapacitygb() {
        return capacitygb;
    }

    public void setCapacitygb(int capacitygb) {
        this.capacitygb = capacitygb;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public short getCachemb() {
        return cachemb;
    }

    public void setCachemb(short cachemb) {
        this.cachemb = cachemb;
    }

    @JsonIgnore
    public Set<Computer> getComputerSet() {
        return computerSet;
    }

    public void setComputerSet(Set<Computer> computerSet) {
        this.computerSet = computerSet;
    }
}
