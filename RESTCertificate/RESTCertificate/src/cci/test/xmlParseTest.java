package cci.test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.castor.CastorMarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.XMLReader;

import cci.model.OwnCertificate;
import cci.model.Product;
import cci.model.Products;

public class xmlParseTest {
	public static void main(String[] args) throws IOException {
		//String filename = "C:\\apps\\curl\\xml\\55.1!741-1.xml";
		String filename = "C:\\apps\\curl\\xml\\16.3!94-1_U.xml";
		String xmlsrting = "";
		
		try {
			xmlsrting = readFile(filename);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		

		try {
			OwnCertificate cert;
			
			Jaxb2Marshaller unmarshaler = new Jaxb2Marshaller();
			
			unmarshaler.setContextPath(OwnCertificate.class.getPackage().getName());
			unmarshaler.setClassesToBeBound(OwnCertificate.class);
			
			//org.xml.sax.InputSource input = new org.xml.sax.InputSource(xmlsrting);  
			//SAXSource source = new SAXSource(input);
			
			
			//InputStream is = new FileInputStream(filename);
			//StreamSource source = new StreamSource(is);
			
			StringReader reader = new StringReader(xmlsrting);
			StreamSource source = new StreamSource(reader);

			
			cert = (OwnCertificate) unmarshaler.unmarshal(source);
			System.out.println(cert);
			
			StringWriter writer = new StringWriter();
		    StreamResult result = new StreamResult(writer);

		    unmarshaler.marshal(cert, result);			
			System.out.println(result.getWriter().toString());
			
		} catch (XmlMappingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//if (is != null) {
			//	is.close();
			//}
		}

	}

	static String readFile(String fileName) throws IOException {
		
		// BufferedReader br = new BufferedReader(new FileReader(fileName));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}

}
