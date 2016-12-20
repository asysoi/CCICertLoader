package cci.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cci.model.Company;
import cci.model.OwnCertificate;
import cci.service.OwnCertificateService;

@RestController
public class CertificateController {

	@Autowired
	private OwnCertificateService service;

	/* -----------------------------------------
	 * Get list of all certificates by filter
	 * ----------------------------------------- */
	@GetMapping(value = "owncerts", headers = "Accept=application/json")
	@ResponseStatus (HttpStatus.OK)
	public List<OwnCertificate> getCertificates(
			@RequestParam(value = "number", required = false) String number,
			@RequestParam(value = "blanknumber", required = false) String blanknumber,
			@RequestParam(value = "from", required = false) String from,
			@RequestParam(value = "to", required = false) String to ) {
		List<OwnCertificate> certificates;
		Filter filter = new Filter(number, blanknumber, from, to);
		certificates = service.getOwnCertificates(filter);
		
		if (certificates.size() == 0 ) {
			throw (new NotFoundCertificateException("Не найдено сертификатов, удовлетворяющих условиям поиска: " + filter.toString()));
		}
		return certificates;
	}

	/* -----------------------------
	 * Add new certificate
	 * ----------------------------- */
	@PostMapping(value = "owncerts", headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public OwnCertificate addCertificate(@RequestBody OwnCertificate certificate) {
		try {
			service.addOwnSertificate(certificate);
		} catch (Exception ex) {
			throw(new AddCertificateException(ex.toString()));
		}
		return certificate;
	}
	
	/* -----------------------------
	 * Add new certificate in debug mode
	 * ----------------------------- */
	@PostMapping(value = "owncerts/debug",  headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)	
	public OwnCertificate addDebugCertificate(@RequestBody String jsonstr) {
		OwnCertificate certificate = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			certificate = mapper.readValue(jsonstr, OwnCertificate.class);
			service.addOwnSertificate(certificate);
		} catch (JsonGenerationException e) {
			throw(new AddCertificateException(e.toString()));
		} catch (JsonMappingException e) {
			throw(new AddCertificateException(e.toString()));
		} catch (Exception e) {
			throw(new AddCertificateException(e.toString()));
		}
       return certificate;
	}
	
	/* -----------------------------
	 * Get certificate by ID
	 * ----------------------------- */
	@GetMapping(value = "owncert/{id}", headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.OK)
	public OwnCertificate getOwnCertificateById(@PathVariable int id)  {
		try {
		    return service.getOwnCertificateById(id);
		} catch (Exception ex) {
			throw(new NotFoundCertificateException("Серитификат id = " + id + " не найден:  " + ex.toString()));			
		}
	}

	/* -----------------------------
	 * Update certificate
	 * ----------------------------- */
	@PutMapping(value = "owncerts",  consumes  = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public OwnCertificate updateCountry(@RequestBody OwnCertificate certificate) {
		return service.updateOwnCertificate(certificate);
	}
	
	/* -----------------------------
	 * Delete certificate
	 * ----------------------------- */
	@DeleteMapping(value = "owncert/{id}",  consumes  = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<String> deleteCountry(@PathVariable("id") int id) {
		System.out.println("id="+id);
		service.deleteOwnCertificate(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<String>("Certificate " + id + " deleted.", responseHeaders, HttpStatus.OK);
	}
	
	/* -----------------------------
	 * Exception handling 
	 * ----------------------------- */
	@ExceptionHandler(Exception.class)
	@ResponseBody
    public ResponseEntity<String> handleIOException(Exception ex) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Error-Code", "12345");
		responseHeaders.set("Content-Type", "application/json;charset=utf-8");
		return new ResponseEntity<String>(ex.toString(), responseHeaders,  HttpStatus.BAD_REQUEST);
    }

}
