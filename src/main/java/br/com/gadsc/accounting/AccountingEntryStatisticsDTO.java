package br.com.gadsc.accounting;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountingEntryStatisticsDTO {
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

//	AccountingEntryStatisticsDTO(Double sum, Double min, Double max, Double avg, Long quantity) {
//		this.sum = sum;
//		this.min = min;
//		this.max = max;
//		this.avg = avg;
//		this.quantity = quantity;
//	}
	
	AccountingEntryStatisticsDTO(AccountingEntryStatistics statistics) {
		this.sum = statistics.getSum();
		this.min = statistics.getMin();
		this.max = statistics.getMax();
		this.avg = statistics.getAvg();
		this.quantity = statistics.getQuantity();
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
	
}
