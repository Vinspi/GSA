package fr.uniamu.ibdm.gsa_server.requests.forms;

public class AddAliquoteForm {

    private int aliquotNLot;
    private String aliquotExpirationDate;
    private int aliquotQuantityVisibleStock;
    private int aliquotQuantityHiddenStock;
    private float aliquotPrice;
    private String provider;
    private String source;
    private  String target;


    public AddAliquoteForm(){}

    public AddAliquoteForm(int aliquotNLot, String aliquotExpirationDate, int aliquotQuantityVisibleStock, int aliquotQuantityHiddenStock, float aliquotPrice, String provider, String source, String target) {
        this.aliquotNLot = aliquotNLot;
        this.aliquotExpirationDate = aliquotExpirationDate;
        this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
        this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
        this.aliquotPrice = aliquotPrice;
        this.provider = provider;
        this.source = source;
        this.target = target;
    }

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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
