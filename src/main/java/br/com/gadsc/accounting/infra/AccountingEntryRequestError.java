package br.com.gadsc.accounting.infra;

public class AccountingEntryRequestError {
	private final String errorMessage;

	public AccountingEntryRequestError(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMEssage() {
		return errorMessage;
	}
		
}
