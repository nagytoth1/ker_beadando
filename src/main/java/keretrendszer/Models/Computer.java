package keretrendszer.Models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "computers")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String motherboard;

    @ManyToOne//ugyanaz a ram nem tartozhat más géphez
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
    private Psu powerSupply;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "computer_storage",
            joinColumns = @JoinColumn(name = "computer_id"),
            inverseJoinColumns = @JoinColumn(name = "storage_id"))
    private Set<Storage> storageSet;

    @ManyToOne
    @JoinColumn(name="system_id", nullable=false)
    private Opsystem opsystem;
    public void setComputer(Computer computer) {
        this.setName(computer.getName());
        this.setMotherboard(computer.getMotherboard());
        this.setRam(computer.getRam());
        this.setRam_quantity(computer.getRam_quantity());
        this.setProcessor(computer.getProcessor());
        this.setVideocard(computer.getVideocard());
        this.setPowerSupply(computer.getPowerSupply());
        this.setStorageSet(computer.getStorageSet());
        this.setOpsystem(computer.getOpsystem());
    }

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

    public String getMotherboard() {
        return motherboard;
    }

    public void setMotherboard(String motherboard) {
        this.motherboard = motherboard;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public byte getRam_quantity() {
        return ram_quantity;
    }

    public void setRam_quantity(byte ram_quantity) {
        this.ram_quantity = ram_quantity;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public Videocard getVideocard() {
        return videocard;
    }

    public void setVideocard(Videocard videocard) {
        this.videocard = videocard;
    }

    public Psu getPowerSupply() {
        return powerSupply;
    }

    public void setPowerSupply(Psu powerSupply) {
        this.powerSupply = powerSupply;
    }

    public Set<Storage> getStorageSet() {
        return storageSet;
    }

    public void setStorageSet(Set<Storage> storageSet) {
        this.storageSet = storageSet;
    }

    public Opsystem getOpsystem() {
        return opsystem;
    }

    public void setOpsystem(Opsystem opsystem) {
        this.opsystem = opsystem;
    }
}
