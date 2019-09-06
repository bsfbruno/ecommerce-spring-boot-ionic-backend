package br.com.bruno.ecommerce.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError(Integer status, String msg, long timeStamp) {
		super(status, msg, timeStamp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	//Editar o setList para não carregar todos os erros de uma vez
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}