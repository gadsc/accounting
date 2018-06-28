package br.com.gadsc.accounting;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountingEntriesTest {
	private static final double VALUE = 25000.15;
	private static final long DATE = 20170130l;
	private static final long ACCOUNT_NUMBER = 1111001l;
	
	private AccountingEntries subject;
	private AccountingEntry accountingEntry;
	
	@Before
	public void init() {
		subject = new AccountingEntries();
		accountingEntry = new AccountingEntry();
		
		accountingEntry.setAccountNumber(ACCOUNT_NUMBER);
		accountingEntry.setDate(DATE);
		accountingEntry.setValue(VALUE);		
	}
	
	@Test
	public void shouldCreateUUIDWhenInserted() {
		UUID uuid = subject.insert(accountingEntry);
		
		Assert.assertNotNull(uuid);
		Assert.assertEquals(1, subject.getKeys().get(accountingEntry.getAccountNumber()).size());
		Assert.assertEquals(uuid, subject.getKeys().get(accountingEntry.getAccountNumber()).get(0));
	}
	
	@Test
	public void shouldUpdateKeysWhenAccountingEntryWithSameAccountNumberInserted() {
		AccountingEntry accountingEntryWithNewValue = new AccountingEntry();

		accountingEntryWithNewValue.setAccountNumber(ACCOUNT_NUMBER);
		accountingEntryWithNewValue.setDate(DATE);
		accountingEntryWithNewValue.setValue(30000.15);
		
		subject.insert(accountingEntry);
		subject.insert(accountingEntryWithNewValue);
		
		Assert.assertEquals(2, subject.getKeys().get(accountingEntry.getAccountNumber()).size());
	}
	
	@Test
	public void shouldReturnAccountingEntryWhenInserted() {
		UUID uuid = subject.insert(accountingEntry);
		Optional<AccountingEntry> entry = subject.getByUUID(uuid);
		
		Assert.assertTrue(entry.isPresent());
		Assert.assertEquals(ACCOUNT_NUMBER, entry.get().getAccountNumber(), 0);
	}
	
	@Test
	public void shouldReturnEmptyWhenNotInserted() {
		UUID uuid = UUID.randomUUID();
		
		Optional<AccountingEntry> entry = subject.getByUUID(uuid);
		
		Assert.assertFalse(entry.isPresent());
	}
	
	@Test
	public void shouldReturnAccountingEntriesWhenInserted() {
		subject.insert(accountingEntry);
		List<AccountingEntry> entries = subject.getByAccountNumber(ACCOUNT_NUMBER);
		
		Assert.assertFalse(entries.isEmpty());
		Assert.assertEquals(1, entries.size());
		Assert.assertEquals(ACCOUNT_NUMBER, entries.get(0).getAccountNumber(), 0);
	}
	
	@Test
	public void shouldReturnAccountingEntriesWhenInsertedWithTheSameAccountNumber() {
		AccountingEntry accountingEntryWithNewValue = new AccountingEntry();
		
		accountingEntryWithNewValue.setAccountNumber(ACCOUNT_NUMBER);
		accountingEntryWithNewValue.setDate(DATE);
		accountingEntryWithNewValue.setValue(30000.15);
		
		subject.insert(accountingEntry);
		subject.insert(accountingEntryWithNewValue);
		List<AccountingEntry> entries = subject.getByAccountNumber(ACCOUNT_NUMBER);
		
		Assert.assertFalse(entries.isEmpty());
		Assert.assertEquals(2, entries.size());
		Assert.assertEquals(ACCOUNT_NUMBER, entries.get(0).getAccountNumber(), 0);
		Assert.assertEquals(ACCOUNT_NUMBER, entries.get(1).getAccountNumber(), 0);
	}
	
	@Test
	public void shouldReturnEmptyListWhenNotInserted() {
		List<AccountingEntry> entries = subject.getByAccountNumber(ACCOUNT_NUMBER);
		
		Assert.assertTrue(entries.isEmpty());
		Assert.assertEquals(0, entries.size());
	}
}
