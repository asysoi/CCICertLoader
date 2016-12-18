package cci.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cci.model.Company;
import cci.model.Customer;
import cci.model.OwnCertificate;
import cci.model.Product;
import cci.repository.cert.JDBCOwnCertificateDAO;

@Service
public class OwnCertificateService {

	@Autowired
	private JDBCOwnCertificateDAO certificateDAO;

	public List<OwnCertificate> getAllCertificates() {

		return testCertificates();
		// return certificateDAO.getAllOwnCertificates();
	}

	
	private List<OwnCertificate> testCertificates() {
		List<OwnCertificate> certs = new ArrayList<OwnCertificate>();
		
		for (int i = 0; i < 5; i++) {
			OwnCertificate cert = new OwnCertificate();
			cert.setNumber(i + " - 367.1/3860-1");
			cert.setBlanknumber("0037426");
			Customer customer = new Customer();
			customer.init(1, "ООО \"Электрощит-Сервис\"",
					"220070, г.Минск, ул.Солтыса, д.187, корпус 7", "190903999");
			List<Company> branches = new ArrayList<Company>();
			Company branch = new Company();
			branch.init(2, "Первый филиал",
					"220070, г.Минск, ул.Солтыса, д.157, корпус 1", "");
			branches.add(branch);
			branch = new Company();
			branch.init(3, "Второй филиал",
					"220070, г.Минск, ул.Солтыса, д.157, корпус 1", "");
			branches.add(branch);
			customer.setBranches(branches);
			cert.setCustomer(customer);
			Company enterprise = new Company();
			enterprise.init(4, "Минское отделение БелТПП",
					"220113, г.Минск, ул.Я.Коласа, д.65, т. 280-04-73",
					"2345617890");
			cert.setBeltpp(enterprise);
			cert.setFactoryaddress("220070, г.Минск, ул.Солтыса, д.187, корпус 7, Республика Беларусь");
			cert.setDatecert("21 июля 2014г.");
			cert.setDateexpire("21 июля 2015 г.");
			cert.setExpertname("Петров В.А.");
			cert.setSigner("М.В.Гуринович");
			cert.setSignerjob("Начальник ОЭиС №1");
			cert.setSignerdate("28.07.2014");
			List<Product> products = new ArrayList<Product>();
			Product product = new Product(); 
			product.init(1, 1, "Подстанция", "8537 20 910 0");
			products.add(product);
			product = new Product();
			product.init(2, 2, "Камеры", "8537 20 910 0");
			products.add(product);
			product = new Product();
			product.init(3, 3, "Распределеители", "8537 20 910 0");
			products.add(product);
			cert.setProducts(products);
			certs.add(cert);
		}
		return certs;
	}

	public OwnCertificate getOwnCertificateById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public OwnCertificate updateCertificate(OwnCertificate certificate) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object deleteOwnCertificate(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addOwnSertificate(OwnCertificate certificate) {
		// TODO Auto-generated method stub
	}

}
