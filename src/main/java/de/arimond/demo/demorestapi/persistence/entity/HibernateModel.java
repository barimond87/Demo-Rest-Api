package de.arimond.demo.demorestapi.persistence.entity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public class HibernateModel implements Serializable {

    private static final long serialVersionUID = -1011260024827829046L;

    private Integer id;
    private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }



}
