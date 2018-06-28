package br.com.gadsc.accounting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class AccountingEntries {
	private final Logger LOGGER = LoggerFactory.getLogger(AccountingEntries.class);
	private final Map<UUID, AccountingEntry> entries = new ConcurrentHashMap<>();
	private final Map<Long, List<UUID>> keys = new ConcurrentHashMap<>();
	
	public UUID insert(AccountingEntry accountingEntry) {
		UUID uuid = UUID.randomUUID();
		
		entries.put(uuid, accountingEntry);
		LOGGER.info("Accounting entry with account number " + accountingEntry.getAccountNumber() + " successfully inserted with id: " + uuid);
		
		if (keys.containsKey(accountingEntry.getAccountNumber())) {
			keys.get(accountingEntry.getAccountNumber()).add(uuid);
			LOGGER.debug("Key of accounting number " + accountingEntry.getAccountNumber() + " updated successfully.");
		} else {
			keys.put(accountingEntry.getAccountNumber(), new ArrayList<>(Arrays.asList(uuid)));
			LOGGER.debug("Key of accounting number " + accountingEntry.getAccountNumber() + " inserted successfully.");
		}
		
		return uuid;
	}
	
	public Optional<AccountingEntry> getByUUID(UUID uuid) {
		return Optional.ofNullable(entries.get(uuid));
	}
	
	public List<AccountingEntry> getAll() {
		return new ArrayList<>(entries.values());
	}
	
	public List<AccountingEntry> getByAccountNumber(Long accountNumber) {
		if (keys.containsKey(accountNumber)) {
			return Collections.unmodifiableList(keys.get(accountNumber).stream()
					.map(uuid -> entries.get(uuid))
					.collect(Collectors.toList()));
		}
		
		return Collections.emptyList();
	}
	
	public Map<Long, List<UUID>> getKeys() {
		return Collections.unmodifiableMap(keys);
	}
}
