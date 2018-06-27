package br.com.gadsc.accounting;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountingEntry {
	@JsonProperty("contaContabil")
	@NotNull(message = "Identificador da conta contábil não pode ser nulo.")
	private Long accountNumber;
	@JsonProperty("data")
	@NotNull(message = "Data da conta contábil não pode ser nula.")
	private Long date;
	@JsonProperty("valor")
	@NotNull(message = "Valor da conta contábil não pode ser nulo.")
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
