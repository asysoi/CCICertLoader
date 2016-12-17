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

	/* -----------------------------
	 * Get list of all certificates
	 * ----------------------------- */
	@GetMapping(value = "owncerts", headers = "Accept=application/json")
	@ResponseStatus (HttpStatus.OK)
	public List<OwnCertificate> getCertificates(
			@RequestParam(value = "from", required = false) String datefrom,
			@RequestParam(value = "to", required = false) String dateto) {
		List<OwnCertificate> certificates;
		certificates = service.getAllCertificates();
		return certificates;
	}

	/* -----------------------------
	 * Add new certificate
	 * ----------------------------- */
	@PostMapping(value = "owncerts", headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public OwnCertificate addCertificate(@RequestBody OwnCertificate certificate) {
		
		service.addOwnSertificate(certificate);
		
		return certificate;

	}

	@PostMapping(value = "owncerts/debug",  headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)	
	public OwnCertificate addDebugCertificate(@RequestBody String jsonstr) {
		OwnCertificate certificate = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			certificate = mapper.readValue(jsonstr, OwnCertificate.class);
		} catch (JsonGenerationException e) {
			throw(new AddCertificateException(e.toString()));
		} catch (JsonMappingException e) {
			throw(new AddCertificateException(e.toString()));
		} catch (IOException e) {
			throw(new AddCertificateException(e.toString()));
		}
       return certificate;
	}
	
	@GetMapping(value = "owncert/{id}", headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<String> getOwnCertificateById(@PathVariable int id) {
		//return service.getOwnCertificateById(id);
		System.out.println("id="+id);
		service.deleteOwnCertificate(id);
		URI location = null;
		try {
			location = new URI("http://localhost/");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(location);
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.OK);
	}

	@PutMapping(value = "owncerts",  consumes  = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public OwnCertificate updateCountry(@RequestBody OwnCertificate certificate) {
		return service.updateCertificate(certificate);

	}
	
	@DeleteMapping(value = "owncert/{id}",  consumes  = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<String> deleteCountry(@PathVariable("id") int id) {
		System.out.println("id="+id);
		service.deleteOwnCertificate(id);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("MyResponseHeader", "MyValue");
		return new ResponseEntity<String>("Certificate " + id + " deleted.", responseHeaders, HttpStatus.OK);
	}
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleIOException(Exception ex) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Error code", "12345");
		return new ResponseEntity<String>(ex.toString(), null,  HttpStatus.BAD_REQUEST);
    }

}
