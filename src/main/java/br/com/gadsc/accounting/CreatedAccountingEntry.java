package br.com.gadsc.accounting;

import java.util.UUID;

public class CreatedAccountingEntry {
	private final UUID id;

	public CreatedAccountingEntry(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}
}
