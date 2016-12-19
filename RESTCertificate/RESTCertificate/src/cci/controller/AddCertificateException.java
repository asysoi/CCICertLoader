package cci.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (HttpStatus.CONFLICT)
public class AddCertificateException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public AddCertificateException(String err) {
		   super("Ошибка добавления сертификата в базу: " + err);
       }
}
