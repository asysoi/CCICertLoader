package cci.repository.cert;

import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cci.model.OwnCertificate;

@Repository
public class JDBCOwnCertificateDAO  {
	
	private NamedParameterJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<OwnCertificate> getAllOwnCertificates() {
		String sql = "select * from OWN_CERT_VIEW ORDER BY id";

		return this.template.getJdbcOperations().query(sql,
				new BeanPropertyRowMapper<OwnCertificate>(OwnCertificate.class));
	}

}
