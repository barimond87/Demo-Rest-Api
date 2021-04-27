
package de.arimond.demo.demorestapi.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;


/**
 * 
 * @author Ben Arimond
 */
@Entity
@Table(name = "user")
public class ApiUser extends HibernateModel {

    private String name;
    private String passwort;
    private Rolle rolle;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    @Enumerated(EnumType.STRING)
    public Rolle getRolle() {
        return rolle;
    }

    public void setRolle(Rolle rolle) {
        this.rolle = rolle;
    }
}
