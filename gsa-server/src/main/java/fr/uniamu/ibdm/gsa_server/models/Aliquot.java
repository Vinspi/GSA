package fr.uniamu.ibdm.gsa_server.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class Aliquot implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) /* changer pour la prod */
	private long aliquotNLot;

	private LocalDate aliquotExpirationDate;
	private long aliquotQuantityVisibleStock;
	private long aliquotQuantityHiddenStock;
	private float aliquotPrice;
	private String provider;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "source", nullable = false), @JoinColumn(name = "target", nullable = false) })
	private Product product;

	public Aliquot() {
	}

	public Aliquot(long aliquotNLot, LocalDate aliquotExpirationDate, long aliquotQuantityVisibleStock,
			long aliquotQuantityHiddenStock, String nameAliquot) {
		this.aliquotNLot = aliquotNLot;
		this.aliquotExpirationDate = aliquotExpirationDate;
		this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
		this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
	}

	public Aliquot(long aliquotQuantityVisibleStock, long aliquotQuantityHiddenStock) {
		this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
		this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
	}

	/**
	 * The method withdraw a quantity of the visible stock, if the quantity is
	 * higher than the actual, set the quantity of the aliquot to 0.
	 *
	 * @param q quantity to withdraw
	 */
	public int withdrawFromVisibleStock(int q) {

		if (q > this.getAliquotQuantityVisibleStock()) {
			int returnValue = (int) this.getAliquotQuantityVisibleStock();
			this.setAliquotQuantityVisibleStock(0);
			return returnValue;
		} else {
			this.setAliquotQuantityVisibleStock(this.getAliquotQuantityVisibleStock() - q);
			return q;
		}

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

	public long getAliquotQuantityVisibleStock() {
		return aliquotQuantityVisibleStock;
	}

	public void setAliquotQuantityVisibleStock(long aliquotQuantityVisibleStock) {
		this.aliquotQuantityVisibleStock = aliquotQuantityVisibleStock;
	}

	public long getAliquotQuantityHiddenStock() {
		return aliquotQuantityHiddenStock;
	}

	public void setAliquotQuantityHiddenStock(long aliquotQuantityHiddenStock) {
		this.aliquotQuantityHiddenStock = aliquotQuantityHiddenStock;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

}
