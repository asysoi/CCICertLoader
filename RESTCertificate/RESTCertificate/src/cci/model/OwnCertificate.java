package cci.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnCertificate {
    
	private int id;
	private String number;
	private String blanknumber;
	private Customer customer;
	private String factoryaddress;
	private Company beltpp;
	private String datecert;
	private String dateexpire;
	private String expertname;
	private String signer;
	private String signerjob;
	private String signerdate;
	private List<Product> products;
	private String dateload;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getBlanknumber() {
		return blanknumber;
	}

	public void setBlanknumber(String blanknumber) {
		this.blanknumber = blanknumber;
	}

	public String getFactoryaddress() {
		return factoryaddress;
	}

	public void setFactoryaddress(String factoryaddress) {
		this.factoryaddress = factoryaddress;
	}

	public Company getBeltpp() {
		return beltpp;
	}

	public void setBeltpp(Company beltpp) {
		this.beltpp = beltpp;
	}

	public String getDatecert() {
		return datecert;
	}

	public void setDatecert(String datecert) {
		this.datecert = datecert;
	}

	public String getDateexpire() {
		return dateexpire;
	}

	public void setDateexpire(String dateexpire) {
		this.dateexpire = dateexpire;
	}

	public String getExpertname() {
		return expertname;
	}

	public void setExpertname(String expertname) {
		this.expertname = expertname;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getSignerjob() {
		return signerjob;
	}

	public void setSignerjob(String signerjob) {
		this.signerjob = signerjob;
	}

	public String getSignerdate() {
		return signerdate;
	}

	public void setSignerdate(String signerdate) {
		this.signerdate = signerdate;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getDateload() {
		return dateload;
	}

	public void setDateload(String dateload) {
		this.dateload = dateload;
	}

	@Override
	public String toString() {
		return "OwnCertificate [id=" + id + ", number=" + number
				+ ", blanknumber=" + blanknumber + ", customer=" + customer
				+ ", factoryaddress=" + factoryaddress + ", beltpp="
				+ beltpp + ", datecert=" + datecert + ", dateexpire="
				+ dateexpire + ", expertname=" + expertname + ", signer="
				+ signer + ", signerjob=" + signerjob + ", signerdate="
				+ signerdate + ", products=" + products + ", dateload="
				+ dateload + "]";
	}

	
}
