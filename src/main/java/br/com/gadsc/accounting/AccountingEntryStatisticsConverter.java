package br.com.gadsc.accounting;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AccountingEntryStatisticsConverter {
	public AccountingEntryStatisticsDTO convert(List<AccountingEntry> entries) {
		AccountingEntryStatistics statistics = new AccountingEntryStatistics(entries);
		
		return new AccountingEntryStatisticsDTO(statistics.withSum().withMin().withMax().withAvg().withQuantity());
	}
}
