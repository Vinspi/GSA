package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long alertId;

    private int seuil;

    @OneToMany(mappedBy = "aliquotAlert", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Collection<Aliquot> aliquots;

    public Alert() {
    }

    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public int getSeuil() {
        return seuil;
    }

    public void setSeuil(int seuil) {
        this.seuil = seuil;
    }

    public Collection<Aliquot> getAliquots() {
        return aliquots;
    }

    public void setAliquots(Collection<Aliquot> aliquots) {
        this.aliquots = aliquots;
    }
}
