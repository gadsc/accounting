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

import org.springframework.stereotype.Service;

@Service
class AccountingEntries {
	
	private final Map<UUID, AccountingEntry> entries = new ConcurrentHashMap<>();
	private final Map<Long, List<UUID>> keys = new ConcurrentHashMap<>();
	
	public UUID insert(AccountingEntry accountingEntry) {
		UUID uuid = UUID.randomUUID();
		
		entries.put(uuid, accountingEntry);
		
		if (keys.containsKey(accountingEntry.getAccountNumber())) {
			keys.get(accountingEntry.getAccountNumber()).add(uuid);
		} else {
			keys.put(accountingEntry.getAccountNumber(), new ArrayList<>(Arrays.asList(uuid)));			
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
			return keys.get(accountNumber).stream()
					.map(uuid -> entries.get(uuid))
					.collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}
}
