package fr.uniamu.ibdm.gsa_server.requests.forms;

public class AddAliquoteForm {

    private int aliquotNLot;
    private String aliquotExpirationDate;
    private int aliquotQuantityVisibleStock;
    private int aliquotQuantityHiddenStock;
    private float aliquotPrice;
    private String aliquoteProvider;
    private String aliquoteproduct;


    public int getAliquotNLot() {
        return aliquotNLot;
    }

    public void setAliquotNLot(int aliquotNLot) {
        this.aliquotNLot = aliquotNLot;
    }

    public String getAliquotExpirationDate() {
        return aliquotExpirationDate;
    }

    public void setAliquotExpirationDate(String aliquotExpirationDate) {
        this.aliquotExpirationDate = aliquotExpirationDate;
    }

    public int getAliquotQuantityVisibleStock() {
        return aliquotQuantityVisibleStock;
    }

    public void setAliquotQuantityVisibleStock(int aliquotQuantityVisibleStock) {
        this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
    }

    public int getAliquotQuantityHiddenStock() {
        return aliquotQuantityHiddenStock;
    }

    public void setAliquotQuantityHiddenStock(int aliquotQuantityHiddenStock) {
        this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
    }

    public float getAliquotPrice() {
        return aliquotPrice;
    }

    public void setAliquotPrice(float aliquotPrice) {
        this.aliquotPrice = aliquotPrice;
    }

    public String getAliquoteProvider() {
        return aliquoteProvider;
    }

    public void setAliquoteProvider(String aliquoteProvider) {
        this.aliquoteProvider = aliquoteProvider;
    }

    public String getAliquoteproduct() {
        return aliquoteproduct;
    }

    public void setAliquoteproduct(String aliquoteproduct) {
        this.aliquoteproduct = aliquoteproduct;
    }
}
