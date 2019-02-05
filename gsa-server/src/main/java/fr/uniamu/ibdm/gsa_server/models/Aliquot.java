package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Aliquot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long aliquotId;

    private String aliquotName;
    private long aliquotNLot;
    private LocalDate aliquotExpirationDate;
    private long aliquotQuantity;

    @ManyToOne
    private Alert aliquotAlert;

    public Aliquot() {
    }

    public long getAliquotId() {
        return aliquotId;
    }

    public void setAliquotId(long aliquotId) {
        this.aliquotId = aliquotId;
    }

    public String getAliquotName() {
        return aliquotName;
    }

    public void setAliquotName(String aliquotName) {
        this.aliquotName = aliquotName;
    }

    public long getAliquotNLot() {
        return aliquotNLot;
    }

    public void setAliquotNLot(long aliquotNLot) {
        this.aliquotNLot = aliquotNLot;
    }

    public LocalDate getAliquotExpirationDate() {
        return aliquotExpirationDate;
    }

    public void setAliquotExpirationDate(LocalDate aliquotExpirationDate) {
        this.aliquotExpirationDate = aliquotExpirationDate;
    }

    public long getAliquotQuantity() {
        return aliquotQuantity;
    }

    public void setAliquotQuantity(long aliquotQuantity) {
        this.aliquotQuantity = aliquotQuantity;
    }

    public Alert getAliquotAlert() {
        return aliquotAlert;
    }

    public void setAliquotAlert(Alert aliquotAlert) {
        this.aliquotAlert = aliquotAlert;
    }
}
