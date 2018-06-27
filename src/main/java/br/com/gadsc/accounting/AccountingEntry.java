package br.com.gadsc.accounting;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountingEntry {
	@JsonProperty("contaContabil")
	private Long accountNumber;
	@JsonProperty("data")
	private Long date;
	@JsonProperty("valor")
	private Double value;

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AccountingEntryDTO [accountNumber=" + accountNumber + ", date=" + date + ", value=" + value + "]";
	}
	
	
}
