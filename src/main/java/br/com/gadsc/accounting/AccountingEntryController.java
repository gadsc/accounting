package br.com.gadsc.accounting;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AccountingEntryController {

	private final AccountingEntries entries;
	private final AccountingEntryStatisticsConverter statisticsConverter;

	@Autowired
	AccountingEntryController(AccountingEntries entries, AccountingEntryStatisticsConverter statisticsConverter) {
		this.entries = entries;
		this.statisticsConverter = statisticsConverter;
	}

	@PostMapping(path = "/lancamentos-contabeis", produces = MediaType.APPLICATION_JSON_VALUE, consumes =  MediaType.APPLICATION_JSON_VALUE)
	public UUID create(@RequestBody AccountingEntry accountingEntry) {
		return entries.insert(accountingEntry);
	}

	@GetMapping(path = "/lancamentos-contabeis/{uuid}")
	public AccountingEntry getById(@PathVariable UUID uuid) {
		return entries.getByUUID(uuid).get();
	}

	@GetMapping(path = "/lancamentos-contabeis")
	public List<AccountingEntry> getByAccountNumber(
			@RequestParam(name = "contaContabil", required = false) Long accountNumber) {
		return entries.getByAccountNumber(accountNumber);
	}
	
	@GetMapping(path = "/lancamentos-contabeis/_stats")
	public AccountingEntryStatisticsDTO getStats(
			@RequestParam(name = "contaContabil", required = false) Long accountNumber) {
		List<AccountingEntry> accountingEntries;
		
		if (accountNumber != null) {
			accountingEntries = entries.getByAccountNumber(accountNumber);
		} else {
			accountingEntries = entries.getAll();
		}
		
		return statisticsConverter.convert(accountingEntries);
	}
}
