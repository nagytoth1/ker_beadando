package keretrendszer.Models;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public short getPerformance() {
        return performance;
    }

    public void setPerformance(short performance) {
        this.performance = performance;
    }
}
