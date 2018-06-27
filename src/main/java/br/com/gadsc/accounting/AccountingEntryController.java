package br.com.gadsc.accounting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.gadsc.accounting.statistics.AccountingEntryStatistics;

@RestController()
public class AccountingEntryController {

	private final AccountingEntries entries;

	@Autowired
	AccountingEntryController(AccountingEntries entries) {
		this.entries = entries;
	}

	@PostMapping(path = "/lancamentos-contabeis", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public UUID create(@Valid @RequestBody AccountingEntry accountingEntry) {
		return entries.insert(accountingEntry);
	}

	@GetMapping(path = "/lancamentos-contabeis/{uuid}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> getById(@PathVariable UUID uuid) {
		Optional<AccountingEntry> accountingEntry = entries.getByUUID(uuid);
		
		if (accountingEntry.isPresent()) {
			return new ResponseEntity<>(accountingEntry, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Conta contábil não encontrada pelo id informado.", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/lancamentos-contabeis")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public List<AccountingEntry> getByAccountNumber(
			@RequestParam(name = "contaContabil", required = false) Long accountNumber) {
		return entries.getByAccountNumber(accountNumber);
	}

	@GetMapping(path = "/lancamentos-contabeis/_stats")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public AccountingEntryStatistics getStats(
			@RequestParam(name = "contaContabil", required = false) Long accountNumber) {
		List<AccountingEntry> accountingEntries;

		if (accountNumber != null) {
			accountingEntries = entries.getByAccountNumber(accountNumber);
		} else {
			accountingEntries = entries.getAll();
		}

		if (accountingEntries.isEmpty()) {
			return AccountingEntryStatistics.createDefault();
		}
		
		return AccountingEntryStatistics.statsOf(accountingEntries).generateStatistics();
	}
 
}
