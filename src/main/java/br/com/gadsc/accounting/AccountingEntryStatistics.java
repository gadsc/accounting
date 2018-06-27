package br.com.gadsc.accounting;

import java.util.List;
import java.util.stream.Collectors;

public class AccountingEntryStatistics {
	private final List<Double> entriesValue;
	private Double sum;
	private Double min;
	private Double max;
	private Double avg;
	private long quantity;

	AccountingEntryStatistics(List<AccountingEntry> entries) {
		this.entriesValue = entries.stream().map(AccountingEntry::getValue).collect(Collectors.toList());
	}

	public AccountingEntryStatistics withSum() {
		this.sum = entriesValue.stream().reduce(Double::sum).orElse(null);
		return this;
	}

	public AccountingEntryStatistics withMin() {
		this.min = entriesValue.stream().reduce(Double::min).orElse(null);
		return this;
	}

	public AccountingEntryStatistics withMax() {
		this.max = entriesValue.stream().reduce(Double::max).orElse(null);
		return this;
	}

	public AccountingEntryStatistics withAvg() {
		this.avg = entriesValue.stream().mapToDouble(value -> value).average().getAsDouble();
		return this;
	}

	public AccountingEntryStatistics withQuantity() {
		this.quantity = entriesValue.size();
		return this;
	}

	public Double getSum() {
		return sum;
	}

	public Double getMin() {
		return min;
	}

	public Double getMax() {
		return max;
	}

	public Double getAvg() {
		return avg;
	}

	public long getQuantity() {
		return quantity;
	}

}
