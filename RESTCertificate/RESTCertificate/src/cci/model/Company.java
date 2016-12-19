package cci.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Company {
    @JsonIgnore
	private int id; 
	private String name;
	private String address;
	private String unp;
	
   
	public void init(int id, String name, String address, String unp) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.unp = unp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUnp() {
		return unp;
	}

	public void setUnp(String unp) {
		this.unp = unp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Company [name=" + name + ", address=" + address + ", unp="
				+ unp + "]";
	}

}
