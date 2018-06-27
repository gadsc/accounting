package br.com.gadsc.accounting.statistics;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.gadsc.accounting.AccountingEntry;

public class AccountingEntryStatistics {
	@JsonProperty("soma")
	private final Double sum;
	@JsonProperty("min")
	private final Double min;
	@JsonProperty("max")
	private final Double max;
	@JsonProperty("media")
	private final Double avg;
	@JsonProperty("quantidade")
	private final Long quantity;
	
	private AccountingEntryStatistics(Double sum, Double min, Double max, Double avg, Long quantity) {
		this.sum = sum;
		this.min = min;
		this.max = max;
		this.avg = avg;
		this.quantity = quantity;
	}

	private AccountingEntryStatistics(StatisticsBuilder statistics) {
		this(statistics.sum, statistics.min, statistics.max, statistics.avg, statistics.quantity);
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

	public Long getQuantity() {
		return quantity;
	}
	
	public static AccountingEntryStatistics createDefault() {
		return new AccountingEntryStatistics(0d,0d,0d,0d,0l);
	}
	
	public static StatisticsBuilder statsOf(List<AccountingEntry> entries) {
		return new StatisticsBuilder(entries);
	}
	
	public static final class StatisticsBuilder {
		private final List<Double> entriesValue;
		private Double sum;
		private Double min;
		private Double max;
		private Double avg;
		private long quantity;

		private StatisticsBuilder(List<AccountingEntry> entries) {
			this.entriesValue = entries.stream().map(AccountingEntry::getValue).collect(Collectors.toList());
		}

		private StatisticsBuilder withSum() {
			this.sum = entriesValue.stream().reduce(Double::sum).orElse(null);
			return this;
		}

		private StatisticsBuilder withMin() {
			this.min = entriesValue.stream().reduce(Double::min).orElse(null);
			return this;
		}

		private StatisticsBuilder withMax() {
			this.max = entriesValue.stream().reduce(Double::max).orElse(null);
			return this;
		}

		private StatisticsBuilder withAvg() {
			this.avg = entriesValue.stream().mapToDouble(value -> value).average().getAsDouble();
			return this;
		}

		private StatisticsBuilder withQuantity() {
			this.quantity = entriesValue.size();
			return this;
		}
		
		public AccountingEntryStatistics generateStatistics() {
			return new AccountingEntryStatistics(this.withSum().withMin().withMax().withAvg().withQuantity());
		}
	}
}
