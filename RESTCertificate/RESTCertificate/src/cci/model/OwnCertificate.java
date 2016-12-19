package cci.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnCertificate {
    
	private int id;
	@JsonIgnore
	private int id_beltpp;
	
	private String number;
	private String blanknumber;
	private String customername;
	private String customeraddress;
	private String customerunp;
	private String factoryaddress;
	private String branches;
	private Company beltpp;
	private String datecert;
	private String dateexpire;
	private String expert;
	private String signer;
	private String signerjob;
	private String datesign;
	private List<Product> products;
	private String dateload;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_beltpp() {
		return id_beltpp;
	}
	public void setId_beltpp(int id_beltpp) {
		this.id_beltpp = id_beltpp;
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
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getCustomeraddress() {
		return customeraddress;
	}
	public void setCustomeraddress(String customeraddress) {
		this.customeraddress = customeraddress;
	}
	public String getCustomerunp() {
		return customerunp;
	}
	public void setCustomerunp(String customerunp) {
		this.customerunp = customerunp;
	}
	public String getFactoryaddress() {
		return factoryaddress;
	}
	public void setFactoryaddress(String factoryaddress) {
		this.factoryaddress = factoryaddress;
	}
	public String getBranches() {
		return branches;
	}
	public void setBranches(String branches) {
		this.branches = branches;
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
	public String getExpert() {
		return expert;
	}
	public void setExpert(String expert) {
		this.expert = expert;
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
	public String getDatesign() {
		return datesign;
	}
	public void setDatesign(String datesign) {
		this.datesign = datesign;
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
				+ ", blanknumber=" + blanknumber + ", customername="
				+ customername + ", customeraddress=" + customeraddress
				+ ", customerunp=" + customerunp + ", factoryaddress="
				+ factoryaddress + ", branches=" + branches + ", beltpp="
				+ beltpp + ", datecert=" + datecert + ", dateexpire="
				+ dateexpire + ", expertname=" + expert + ", signer="
				+ signer + ", signerjob=" + signerjob + ", signerdate="
				+ datesign + ", products=" + products + ", dateload="
				+ dateload + "]";
	}

}
