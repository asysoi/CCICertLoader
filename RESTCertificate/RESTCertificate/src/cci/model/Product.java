package cci.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Product {
	@JsonIgnore
	private int id;
	
	private int number;
	private String name;
	private String code;

	public void init(int id, int number, String name, String code) {
		this.id = id;
		this.number = number;
		this.name = name;
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", number=" + number + ", name=" + name
				+ ", code=" + code + "]";
	}
    
	
}
