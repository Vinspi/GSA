package fr.uniamu.ibdm.gsa_server.requests.forms;

import java.util.regex.Pattern;

public class AddAliquoteForm implements Form{

    private long aliquotNLot;
    private int aliquotQuantityVisibleStock;
    private int aliquotQuantityHiddenStock;
    private float aliquotPrice;
    private String aliquotProvider;
    private String aliquotProduct;

    public AddAliquoteForm(long aliquotNLot,
                           int aliquotQuantityVisibleStock,
                           int aliquotQuantityHiddenStock,
                           float aliquotPrice,
                           String aliquotProvider,
                           String aliquotProduct) {

        this.aliquotNLot = aliquotNLot;
        this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
        this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
        this.aliquotPrice = aliquotPrice;
        this.aliquotProvider = aliquotProvider;
        this.aliquotProduct = aliquotProduct;
    }

    public AddAliquoteForm() {
    }

    @Override
    public boolean validate() {

        Pattern patternProductName = Pattern.compile("^[A-Z]*_ANTI_[A-Z]*");

        if(
            aliquotNLot >= 0
            && (aliquotQuantityVisibleStock + aliquotQuantityHiddenStock) > 0
            && aliquotQuantityHiddenStock >= 0
            && aliquotQuantityVisibleStock >= 0
            && aliquotPrice > 0
            && aliquotProvider.length() > 0
            && patternProductName.matcher(aliquotProduct).matches()
        ) {
            return true;
        }

        return false;
    }

    public long getAliquotNLot() {
        return aliquotNLot;
    }

    public void setAliquotNLot(long aliquotNLot) {
        this.aliquotNLot = aliquotNLot;
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

    public String getAliquotProvider() {
        return aliquotProvider;
    }

    public void setAliquotProvider(String aliquotProvider) {
        this.aliquotProvider = aliquotProvider;
    }

    public String getAliquotProduct() {
        return aliquotProduct;
    }

    public void setAliquotProduct(String aliquotProduct) {
        this.aliquotProduct = aliquotProduct;
    }
}
