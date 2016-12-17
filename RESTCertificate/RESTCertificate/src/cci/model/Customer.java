package cci.model;

import java.util.List;

public class Customer extends Company {

	
	private List<Company> branches;
	
	public List<Company> getBranches() {
		return branches;
	}

	public void setBranches(List<Company> branches) {
		this.branches = branches;
	}

    
}
