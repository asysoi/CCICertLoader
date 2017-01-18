package cci.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.XmlMappingException;

import cci.model.OwnCertificate;

public class xmlParseTest {
	public static void main(String[] args) throws IOException {
		FileInputStream is = null;

		try {
			String filename = "C:\\apps\\curl\\xml\\16.3!94-1_U.xml";
			is = new FileInputStream(filename);
			JAXBContext jaxbContext = JAXBContext
					.newInstance(OwnCertificate.class);

			Unmarshaller jaxb2Unmarshaller = (Unmarshaller) jaxbContext
					.createUnmarshaller();
			OwnCertificate customer = (OwnCertificate) jaxb2Unmarshaller
					.unmarshal(new StreamSource(is));
			System.out.println(customer);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XmlMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

}
