package cci.cert.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import cci.cert.model.Certificate;
import cci.cert.model.Product;

@Repository
public class JDBCCertificateDAO implements CertificateDAO {
	private static final Logger LOG = Logger.getLogger(JDBCCertificateDAO.class);
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	// ---------------------------------------------------------------
	// ����� ����������� �� id
	// ---------------------------------------------------------------
	public Certificate findByID(Long id) {
		Certificate cert = null;

		try {
			String sql = "select * from CERT_VIEW WHERE cert_id = ?";
			cert = template.getJdbcOperations().queryForObject(sql,
					new Object[] { id },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			sql = "select * from c_PRODUCT WHERE cert_id = ? ORDER BY product_id";
			cert.setProducts(template.getJdbcOperations().query(sql,
					new Object[] { cert.getCert_id() },
					new BeanPropertyRowMapper<Product>(Product.class)));
		} catch (Exception ex) {
			LOG.error("������ ������ ����������� �� ID " + id + ": " + ex.getMessage());
			//ex.printStackTrace();
		}
		return cert;
	}

	// ---------------------------------------------------------------
	// ����� ����������� �� id, nomercert & datacert
	// ---------------------------------------------------------------	
	public Certificate check(Certificate cert) {
		Certificate rcert = null;

		try {
			String sql = "select * from CERT_VIEW WHERE NOMERCERT = ? AND NBLANKA = ? AND (DATACERT=? or ISSUEDATE=TO_DATE(?,'DD.MM.YY'))";
			rcert = template.getJdbcOperations().queryForObject(
					sql,
					new Object[] { cert.getNomercert(), cert.getNblanka(),
							cert.getDatacert(), cert.getDatacert() },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));
			
			if (rcert != null) {
				sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				rcert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { rcert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (EmptyResultDataAccessException emt) {
			LOG.error("���������� � �������  "
					+ cert.getNomercert() + " �� ������. ��������� ������ ��������.");
		} catch (Exception ex) {
			LOG.error("�������� ������� � ���� ����������� "
					+ cert.getNomercert() + " ������: " + ex.getMessage());
			// ex.printStackTrace();
		}
		return rcert;
	}

	
	// ---------------------------------------------------------------	
	// ����� ����������� �� ������ ������
	// �������� ��������� ������������
	// ---------------------------------------------------------------	
	public List<Certificate> findByNBlanka(String number) {
		List<Certificate> certs = null;

		try {
			String sql = "select * from CERT_VIEW WHERE nblanka = ?";
			certs = template.getJdbcOperations().query(sql,
					new Object[] { number },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			for (Certificate cert : certs) {
				sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			LOG.error("������ ������ ����������� �� ������ ������ " + number  + ": " + ex.getMessage());
			//ex.printStackTrace();
		}
		return certs;
	}

	// ---------------------------------------------------------------
	// ����� ����������� �� ������ �����������
	// ---------------------------------------------------------------
	public List<Certificate> findByNumberCert(String number) {
		List<Certificate> certs = null;

		try {
			String sql = "select * from CERT_VIEW WHERE nomercert = ?";
			certs = template.getJdbcOperations().query(sql,
					new Object[] { number },
					new BeanPropertyRowMapper<Certificate>(Certificate.class));

			for (Certificate cert : certs) {
				sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>(Product.class)));
			}
		} catch (Exception ex) {
			LOG.error("������ ������ ����������� �� ������ ����������� "
					+ number + ": " + ex.getMessage());
			//ex.printStackTrace();
		}
		return certs;
	}

	
	// ---------------------------------------------------------------
	// ����� ��� �����������
	// ---------------------------------------------------------------
	public List<Certificate> findAll() {
		String sql = "select * from c_CERT ORDER BY cert_id";

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<Certificate>(Certificate.class));
	}

	
	// ---------------------------------------------------------------
	// ��������� ����������
	// ---------------------------------------------------------------
	public long save(Certificate cert) {
		String sql_cert = "insert into c_cert "
				+ "(cert_id, "
				+ "forms, unn, kontrp, kontrs, adress, poluchat, adresspol, datacert, "
				+ "nomercert, expert, nblanka, rukovod, transport, marshrut, otmetka, "
				+ "stranav, stranapr, status, koldoplist, flexp, unnexp, expp, "
				+ "exps, expadress, flimp, importer, adressimp, flsez, sez, "
				+ "flsezrez, stranap, otd_id, parentnumber, parentstatus, issuedate,  "
				+ "codestranav, codestranapr, codestranap,  category) "
				//+ "values (beltpp.cert_id_seq.nextval, "
				+ "values (:cert_id, "
				+ "TRIM(:forms), :unn, :kontrp, :kontrs, :adress, :poluchat, :adresspol, :datacert, "
				+ ":nomercert, :expert, :nblanka, :rukovod, :transport, :marshrut, :otmetka, "
				+ ":stranav, :stranapr, :status, :koldoplist, :flexp, :unnexp, :expp, "
				+ ":exps, :expadress, :flimp, :importer, :adressimp, :flsez, :sez, "
				+ ":flsezrez, :stranap, :otd_id, :parentnumber, :parentstatus, TO_DATE(:datacert,'DD.MM.YY'),  "
				+ ":codestranav, :codestranapr, :codestranap,  :category )";

		long cert_id = 0;
		
		try {
			cert.setCert_id(SequenceGenerator.getNextValue("cert_id", this));
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);
			// auto generation return -> GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			
			// auto generation return -> int row = template.update(sql_cert, parameters, keyHolder,
			// auto generation return -> 		new String[] { "CERT_ID" });
			// auto generation return -> cert_id = keyHolder.getKey().longValue();
			
			int row = template.update(sql_cert, parameters);
			
			if (row > 0) {
				cert_id = cert.getCert_id();
				//// sql_cert = "select beltpp.cert_id_seq.currval from dual";
				//// cert_id = template.getJdbcOperations().queryForInt(sql_cert);	
				
				if (cert_id > 0) { 
					String sql_product = "insert into c_PRODUCT values ("
							+ " beltpp.product_id_seq.nextval, " + cert_id
							+ ", "
							+ " :numerator, :tovar, :vidup, :kriter, :ves, :schet, :fobvalue)";

					if (cert.getProducts() != null && cert.getProducts().size() > 0) {
						SqlParameterSource[] batch = SqlParameterSourceUtils
								.createBatch(cert.getProducts().toArray());
						int[] updateCounts = template.batchUpdate(sql_product, batch);
				
						// 	create product  denorm record
						String tovar = "";
						for (Product product: cert.getProducts()) {
							tovar += product.getTovar() +  ", " + product.getKriter() + ", " + product.getVes() + "; "; 
						}
				
						sql_product = "insert into C_PRODUCT_DENORM values (:cert_id, :tovar)";
						parameters = new MapSqlParameterSource().addValue("cert_id", cert_id).addValue("tovar",tovar);
						template.update(sql_product, parameters);
					}
				}
			}
		} catch (Exception ex) {
			LOG.error("������ ���������� ����������� " + cert.getNomercert() + ": " + ex.getMessage());
			ex.printStackTrace();
		}
		return cert_id;
	}

	
	// ---------------------------------------------------------------
	// �������� ����������
	// ---------------------------------------------------------------
	public void update(Certificate cert) throws Exception {

		String sql_cert = "update c_cert SET "
				
				+ "forms = TRIM(:forms), unn = :unn, kontrp = :kontrp, kontrs = :kontrs, adress = :adress, poluchat = :poluchat, adresspol = :adresspol, datacert = :datacert,"
				+ "nomercert = :nomercert, expert = :expert, nblanka = :nblanka, rukovod = :rukovod, transport = :transport, marshrut = :marshrut, otmetka = :otmetka,"
				+ "stranav = :stranav, stranapr = :stranapr, status = :status, koldoplist = :koldoplist, flexp = :flexp, unnexp = :unnexp, expp = :expp, "
				+ "exps = :exps, expadress = :expadress, flimp = :flimp, importer = :importer, adressimp = :adressimp, flsez = :flsez, sez = :sez,"
				+ "flsezrez = :flsezrez, stranap = :stranap, otd_id = :otd_id, parentnumber = :parentnumber, parentstatus = :parentstatus, issuedate = TO_DATE(:datacert,'DD.MM.YY'), "
				+ "codestranav = :codestranav, codestranapr = :codestranapr, codestranap = :codestranap, category = :category "
				+ "WHERE cert_id = :cert_id";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(cert);

		//try {

			int row = template.update(sql_cert, parameters);

			template.getJdbcOperations().update(
					"delete from c_PRODUCT where cert_id = ?",
					Long.valueOf(cert.getCert_id()));
			
			template.getJdbcOperations().update(
					"delete from c_PRODUCT_DENORM where cert_id = ?",
					Long.valueOf(cert.getCert_id()));


			String sql_product = "insert into c_PRODUCT values ("
					+ " beltpp.product_id_seq.nextval, "
					+ cert.getCert_id() + ", "
					+ " :numerator, :tovar, :vidup, :kriter, :ves, :schet, :fobvalue)";

			if (cert.getProducts() != null && cert.getProducts().size() > 0) {
				SqlParameterSource[] batch = SqlParameterSourceUtils
						.createBatch(cert.getProducts().toArray());
				int[] updateCounts = template.batchUpdate(sql_product, batch);
			}
			
			// create product  denorm record
			String tovar = "";
			for (Product product: cert.getProducts()) {
				tovar += product.getTovar() +  ", " + product.getKriter() + ", " + product.getVes() + "; "; 
			}
			
			sql_product = "insert into C_PRODUCT_DENORM values (:cert_id, :tovar)";
			parameters = new MapSqlParameterSource().addValue("cert_id", Long.valueOf(cert.getCert_id())).addValue("tovar",tovar);
			template.update(sql_product, parameters);
					

		//} catch (Exception ex) {
		//	LOG.error("������ ���������� ����������� " + cert.getNomercert() + ": " + ex.getMessage());
			//ex.printStackTrace();
		//}

	}

	
	// ---------------------------------------------------------------
	// ����� ������ ������������ �� �������
	// ---------------------------------------------------------------
	public List<Certificate> findByCertificate(Certificate qcert) {
		List<Certificate> certs = null;

		String sql_cert = "SELECT * from CERT_VIEW WHERE "
				+ "datacert = :datacert AND nomercert = :nomercert AND nblanka = :nblanka";

		SqlParameterSource parameters = new BeanPropertySqlParameterSource(
				qcert);

		try {

			certs = template.query(sql_cert, parameters,
					new BeanPropertyRowMapper<Certificate>());

			for (Certificate cert : certs) {
				String sql = "select * from c_PRODUCT WHERE cert_id = ?  ORDER BY product_id";
				cert.setProducts(template.getJdbcOperations().query(sql,
						new Object[] { cert.getCert_id() },
						new BeanPropertyRowMapper<Product>()));
			}

		} catch (Exception ex) {
			LOG.error("������ ������ ������������ �� ������� �����������: "
					+ qcert + ": " + ex.getMessage());
			//ex.printStackTrace();
		}

		return certs;
	}

	// ---------------------------------------------------------------
	// �������� ID ��������� �� ��� ��������� ����� (minsk, vitebsk ...)
	// ---------------------------------------------------------------
	public long getOtdIdBySynonimName(String directory) {
		long id = 0;

		try {
			String sql = "select id from C_OTD WHERE NAME_SYN = ?";
			id = template.getJdbcOperations().queryForObject(sql,
					new Object[] { directory }, Long.class);
		} catch (Exception ex) {
			LOG.error("������ ������ ID ��������� �� ��� ��������: "
					+ directory+ ": " + ex.getMessage());
			//ex.printStackTrace();
		}

		return id;
	}

	
	// ---------------------------------------------------------------
	// ��������� ��� ����� � ������������ � ���� ������
	// ---------------------------------------------------------------
	public int saveFile(long cert_id, String lfile) {
		String sql = "insert into c_files_in(file_in_id, file_in_name, cert_id, date_load) values "
				     + "(beltpp.file_id_seq.nextval, :file_in_name, :cert_id, SYSDATE)";
		int row = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("file_in_name", lfile);
		parameters.put("cert_id", cert_id);
	
		try {
			row = template.update(sql, parameters);
		} catch (Exception ex) {
			LOG.error("������ ���������� ������ � ����� " +lfile  + " ����������� " + cert_id + " � ������� file_in");
			//ex.printStackTrace();
		}
		return row;
	}

	@Override
	public List<Certificate> findNextPage(int pageindex, int pagesize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// --------------------------------------------------- 
	// autogeneration returning test
	// ---------------------------------------------------
	private void testkey() {
		String QUERY = "INSERT INTO test_students "
				+ "  VALUES (student_seq.NEXTVAL,"
				+ "         'Harry', 'harry@hogwarts.edu', '31-July-1980')";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection connection = DriverManager
					.getConnection("jdbc:oracle:thin:@//localhost:1521/pdborcl.cci.by",
							"beltpp", "123456");

			PreparedStatement ps = connection.prepareStatement(QUERY,
					new String[] { "student_id" });

			Long studentId = null;

			if (ps.executeUpdate() > 0) {

				ResultSet generatedKeys = ps.getGeneratedKeys();
				
				if (generatedKeys != null && generatedKeys.next()) {
					
					studentId = generatedKeys.getLong(1);
					LOG.info("Student ID: " + studentId );
				}

			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	// ---------------------------------------------------------------
	// get pool. return start pooling
	// ---------------------------------------------------------------
	public long getNextValuePool(String seq_name, int poolsize) throws Exception {

		String sql = "select value from c_sequence WHERE name = '" + seq_name + "'";
		long vl = template.getJdbcOperations().queryForInt(sql);
		
		sql = "update c_sequence SET "
				+ " value = value + :poolsize"
				+ " WHERE name = :seq_name";
		
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("poolsize", Integer.valueOf(poolsize)).addValue("seq_name",seq_name);
	    template.update(sql, parameters);
	
	    return vl;
	}

}
