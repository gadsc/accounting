package br.com.gadsc.accounting.statistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.gadsc.accounting.AccountingEntry;

public class AccountingEntryStatisticsTest {

	private AccountingEntryStatistics subject;
	private List<AccountingEntry> entries;

	@Before
	public void init() {
		AccountingEntry accountingEntry1 = new AccountingEntry();
		AccountingEntry accountingEntry2 = new AccountingEntry();
		AccountingEntry accountingEntry3 = new AccountingEntry();

		accountingEntry1.setAccountNumber(1111001l);
		accountingEntry1.setDate(20170130l);
		accountingEntry1.setValue(25000.15);

		accountingEntry2.setAccountNumber(1111001l);
		accountingEntry2.setDate(20170130l);
		accountingEntry2.setValue(20000.15);

		accountingEntry3.setAccountNumber(1111001l);
		accountingEntry3.setDate(20170130l);
		accountingEntry3.setValue(30000.15);

		entries = Arrays.asList(accountingEntry1, accountingEntry2, accountingEntry3);
		subject = AccountingEntryStatistics.statsOf(entries).generateStatistics();
	}

	@Test
	public void shouldReturnSumStatistics() {
		Assert.assertEquals(25000.15 + 20000.15 + 30000.15, subject.getSum(), 0);
	}

	@Test
	public void shouldReturnMinStatistics() {
		Assert.assertEquals(20000.15, subject.getMin(), 0);
	}

	@Test
	public void shouldReturnMaxStatistics() {
		Assert.assertEquals(30000.15, subject.getMax(), 0);
	}

	@Test
	public void shouldReturnAvgStatistics() {
		Assert.assertEquals((25000.15 + 20000.15 + 30000.15)/3, subject.getAvg(), 0);
	}

	@Test
	public void shouldReturnQuantityStatistics() {
		Assert.assertEquals(entries.size(), subject.getQuantity(), 0);
	}
	
	@Test
	public void shouldCreateDefaultIfEntriesIsEmpty() {
		subject = AccountingEntryStatistics.statsOf(new ArrayList<>()).generateStatistics();
		Assert.assertEquals(0, subject.getSum(), 0);
		Assert.assertEquals(0, subject.getMin(), 0);
		Assert.assertEquals(0, subject.getMax(), 0);
		Assert.assertEquals(0, subject.getAvg(), 0);
		Assert.assertEquals(0, subject.getQuantity(), 0);
	}
	
	@Test
	public void shouldCreateDefaultIfEntriesIsNull() {
		subject = AccountingEntryStatistics.statsOf(new ArrayList<>()).generateStatistics();
		Assert.assertEquals(0, subject.getSum(), 0);
		Assert.assertEquals(0, subject.getMin(), 0);
		Assert.assertEquals(0, subject.getMax(), 0);
		Assert.assertEquals(0, subject.getAvg(), 0);
		Assert.assertEquals(0, subject.getQuantity(), 0);
	}
}
