package cci.repository.cert;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.model.OwnCertificate;
import cci.model.Product;
import cci.model.Company;

@Repository
public class JDBCOwnCertificateDAO {

	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<OwnCertificate> getAllOwnCertificates() {
		String sql = "select * from OWN_CERT_VIEW ORDER BY id";

		return this.template.getJdbcOperations()
				.query(sql,
						new BeanPropertyRowMapper<OwnCertificate>(
								OwnCertificate.class));
	}

	// ---------------------------------------------------------------
	// поиск сертификата по id -> PS
	// ---------------------------------------------------------------
	public OwnCertificate findCertificateByID(int id) throws Exception {
		OwnCertificate cert = null;

		String sql = "select * from owncertificate WHERE id = ?";
		cert = template.getJdbcOperations()
				.queryForObject(
						sql,
						new Object[] { id },
						new BeanPropertyRowMapper<OwnCertificate>(
								OwnCertificate.class));

		sql = "select * from beltpp WHERE id = ? ";
		cert.setBeltpp(template.getJdbcOperations().queryForObject(sql,
				new Object[] { cert.getId_beltpp() },
				new BeanPropertyRowMapper<Company>(Company.class)));

		sql = "select * from ownproduct WHERE id_certificate = ? ORDER BY id";
		cert.setProducts(template.getJdbcOperations().query(sql,
				new Object[] { cert.getId() },
				new BeanPropertyRowMapper<Product>(Product.class)));
		return cert;
	}

	// ---------------------------------------------------------------
	// save certificate -> PS
	// ---------------------------------------------------------------
	public OwnCertificate save(OwnCertificate cert) throws Exception {

		String sql = "SELECT id FROM beltpp WHERE name = '"  
				+  cert.getBeltpp().getName() + "'";
		int id = 0;
        try {		
		  id = this.template.getJdbcOperations().queryForObject(sql,
				Integer.class); 
        } catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("Query: " + sql);
		}

		System.out.println("id = " + id);
		if (id == 0) {
			sql = "insert into beltpp(name, address) values(:name, :address)";
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(
					cert.getBeltpp());
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			id = 0;

			int row = template.update(sql, parameters, keyHolder,
					new String[] { "id" });
			id = keyHolder.getKey().intValue();
		}
		cert.setId_beltpp(id);

		String sql_cert = "insert into owncertificate(id_beltpp, number, blanknumber, customername, customeraddress, "
				+ " customerunp, factoryaddress, branches, datecert, dateexpire, expert, signer, signerjob, datesign ) "
				+ " values ("
				+ " :id_beltpp, :number, :blanknumber, :customername, :customeraddress, :customerunp, :factoryaddress, :branches,"
				+ " TO_DATE(:datecert,'DD.MM.YY'), "
				+ " TO_DATE(:dateexpire,'DD.MM.YY'), "
				+ " :expert, :signer, :signerjob, "
				+ " TO_DATE(:datesign,'DD.MM.YY')" + " )";

		System.out.println("Step 2");
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		id = 0;
		System.out.println("Step 3");
		int row = template.update(sql_cert, parameters, keyHolder,
				new String[] { "id" });
		System.out.println("Step 4");
		id = keyHolder.getKey().intValue();
		System.out.println("Step 5");
		String sql_product = "insert into ownproduct(id_certificate, number, name, code) values ("
				+ id + ", :number, :name, :code)";
		System.out.println("Step 6");
		if (cert.getProducts() != null && cert.getProducts().size() > 0) {
			SqlParameterSource[] batch = SqlParameterSourceUtils
					.createBatch(cert.getProducts().toArray());
			int[] updateCounts = template.batchUpdate(sql_product, batch);
		}
		System.out.println("Step 7");
		cert.setId(id);
		
		
		return cert;
	}

	// ---------------------------------------------------------------
	// update certificate XXXX
	// ---------------------------------------------------------------
	public void update(OwnCertificate cert) {

		String sql_cert = "update c_cert SET "
				+ "forms = :forms, unn = :unn, kontrp = :kontrp, kontrs = :kontrs, adress = :adress, poluchat = :poluchat, adresspol = :adresspol, datacert = :datacert,"
				+ "nomercert = :nomercert, expert = :expert, nblanka = :nblanka, rukovod = :rukovod, transport = :transport, marshrut = :marshrut, otmetka = :otmetka,"
				+ "stranav = :stranav, stranapr = :stranapr, status = :status, koldoplist = :koldoplist, flexp = :flexp, unnexp = :unnexp, expp = :expp, "
				+ "exps = :exps, expadress = :expadress, flimp = :flimp, importer = :importer, adressimp = :adressimp, flsez = :flsez, sez = :sez,"
				+ "flsezrez = :flsezrez, stranap = :stranap, parentnumber = :parentnumber, parentstatus = : parentstatus, issuedate=TO_DATE(:datacert,'DD.MM.YY')"
				+ "WHERE cert_id = :cert_id";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);

		try {

			int row = template.update(sql_cert, parameters);

			template.getJdbcOperations().update(
					"delete from C_PRODUCT where cert_id = ?",
					Long.valueOf(cert.getId()));

			String sql_product = "insert into C_PRODUCT values ("
					+ " beltpp.product_id_seq.nextval, " + cert.getId() + ", "
					+ " :numerator, :tovar, :vidup, :kriter, :ves, :schet)";

			if (cert.getProducts() != null && cert.getProducts().size() > 0) {
				SqlParameterSource[] batch = SqlParameterSourceUtils
						.createBatch(cert.getProducts().toArray());
				int[] updateCounts = template.batchUpdate(sql_product, batch);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
