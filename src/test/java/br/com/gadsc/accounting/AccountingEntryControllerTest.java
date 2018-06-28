package br.com.gadsc.accounting;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.gadsc.accounting.infra.TestUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountingEntryController.class)
public class AccountingEntryControllerTest {
	
	private static final double DEFAULT_VALUE = 25000.15;
	private static final long DEFAULT_DATE = 20170130l;
	private static final long DEFAULT_ACCOUNT_NUMBER = 1111001l;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AccountingEntries service;

	@Test
	public void shouldInsertWhenAccountingEntryIsValid() throws Exception {
		AccountingEntry entry = new AccountingEntry();
		UUID uuid = UUID.randomUUID();

		entry.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry.setDate(DEFAULT_DATE);
		entry.setValue(DEFAULT_VALUE);

		Mockito.when(service.insert(entry)).thenReturn(uuid);

		mvc.perform(post("/lancamentos-contabeis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(entry))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(uuid.toString())));
		
		Mockito.verify(service, Mockito.times(1)).insert(entry);
	    Mockito.verifyNoMoreInteractions(service);
	}
	
	@Test
	public void shouldNotInsertWhenAccountNumberIsNull() throws Exception {
		AccountingEntry entry = new AccountingEntry();
		UUID uuid = UUID.randomUUID();

		entry.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry.setValue(DEFAULT_VALUE);

		Mockito.when(service.insert(entry)).thenReturn(uuid);

		mvc.perform(post("/lancamentos-contabeis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(entry))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)));
		
		Mockito.verify(service, Mockito.never()).insert(entry);
	}
	
	@Test
	public void shouldNotInsertWhenValueIsNull() throws Exception {
		AccountingEntry entry = new AccountingEntry();
		UUID uuid = UUID.randomUUID();

		entry.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry.setDate(DEFAULT_DATE);

		Mockito.when(service.insert(entry)).thenReturn(uuid);

		mvc.perform(post("/lancamentos-contabeis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(entry))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)));
		
		Mockito.verify(service, Mockito.never()).insert(entry);
	}
	
	@Test
	public void shouldNotInsertWhenDateIsNull() throws Exception {
		AccountingEntry entry = new AccountingEntry();
		UUID uuid = UUID.randomUUID();

		entry.setDate(DEFAULT_DATE);
		entry.setValue(DEFAULT_VALUE);

		Mockito.when(service.insert(entry)).thenReturn(uuid);

		mvc.perform(post("/lancamentos-contabeis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(entry))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)));
		
		Mockito.verify(service, Mockito.never()).insert(entry);
	}
	
	@Test
	public void shouldNotInsertWhenAccountingEntryIsNotValid() throws Exception {
		AccountingEntry entry = new AccountingEntry();
		UUID uuid = UUID.randomUUID();

		Mockito.when(service.insert(entry)).thenReturn(uuid);

		mvc.perform(post("/lancamentos-contabeis")
				.contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.asJsonString(entry))
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(3)));
		
		Mockito.verify(service, Mockito.never()).insert(entry);
	}
	
	@Test
	public void shouldReturnAccountingEntryByIdWhenExists() throws Exception {
		AccountingEntry entry = new AccountingEntry();
		UUID uuid = UUID.randomUUID();

		entry.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry.setDate(DEFAULT_DATE);
		entry.setValue(DEFAULT_VALUE);

		Mockito.when(service.getByUUID(uuid)).thenReturn(Optional.of(entry));

		mvc.perform(get("/lancamentos-contabeis/{uuid}", uuid).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.contaContabil", is(entry.getAccountNumber().intValue())))
				.andExpect(jsonPath("$.data", is(entry.getDate().intValue())))
				.andExpect(jsonPath("$.valor", is(entry.getValue().doubleValue())));
	}

	@Test
	public void shouldReturnNotFoundAccountingEntryWhenNotExists() throws Exception {
		UUID uuid = UUID.randomUUID();

		Mockito.when(service.getByUUID(uuid)).thenReturn(Optional.empty());

		mvc.perform(get("/lancamentos-contabeis/{uuid}", uuid)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void shouldReturnAccountingEntriesByAccountNumberWhenExists() throws Exception {
		AccountingEntry entry1 = new AccountingEntry();
		AccountingEntry entry2 = new AccountingEntry();

		entry1.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry1.setDate(DEFAULT_DATE);
		entry1.setValue(DEFAULT_VALUE);

		entry2.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry2.setDate(DEFAULT_DATE);
		entry2.setValue(30000.15);
		
		Mockito.when(service.getByAccountNumber(DEFAULT_ACCOUNT_NUMBER)).thenReturn(Arrays.asList(entry1, entry2));

		mvc.perform(get("/lancamentos-contabeis")
				.param("contaContabil", String.valueOf(DEFAULT_ACCOUNT_NUMBER))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].contaContabil", is(entry1.getAccountNumber().intValue())))
				.andExpect(jsonPath("$[0].data", is(entry1.getDate().intValue())))
				.andExpect(jsonPath("$[0].valor", is(entry1.getValue().doubleValue())))
				.andExpect(jsonPath("$[1].contaContabil", is(entry2.getAccountNumber().intValue())))
				.andExpect(jsonPath("$[1].data", is(entry2.getDate().intValue())))
				.andExpect(jsonPath("$[1].valor", is(entry2.getValue().doubleValue())));
	}
	
	@Test
	public void shouldReturnNotFoundAccountingEntriesWhenNotExists() throws Exception {
		Mockito.when(service.getByAccountNumber(DEFAULT_ACCOUNT_NUMBER)).thenReturn(Collections.emptyList());

		mvc.perform(get("/lancamentos-contabeis")
				.param("contaContabil", String.valueOf(DEFAULT_ACCOUNT_NUMBER))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void shouldReturnForbiddenWhenNotExistsAccountEntriesForStatsWithAccountNumberFilter() throws Exception {
		Mockito.when(service.getByAccountNumber(DEFAULT_ACCOUNT_NUMBER)).thenReturn(Collections.emptyList());

		mvc.perform(get("/lancamentos-contabeis/_stats")
				.param("contaContabil", String.valueOf(DEFAULT_ACCOUNT_NUMBER))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void shouldReturnForbiddenWhenNotExistsAccountEntriesForStats() throws Exception {
		Mockito.when(service.getAll()).thenReturn(Collections.emptyList());

		mvc.perform(get("/lancamentos-contabeis/_stats")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void shouldReturnAccountEntriesStatsWithAccountNumberFilter() throws Exception {
		AccountingEntry entry1 = new AccountingEntry();
		AccountingEntry entry2 = new AccountingEntry();

		entry1.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry1.setDate(DEFAULT_DATE);
		entry1.setValue(DEFAULT_VALUE);

		entry2.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry2.setDate(DEFAULT_DATE);
		entry2.setValue(30000.15);
		
		Mockito.when(service.getByAccountNumber(DEFAULT_ACCOUNT_NUMBER)).thenReturn(Arrays.asList(entry1, entry2));

		mvc.perform(get("/lancamentos-contabeis/_stats")
				.param("contaContabil", String.valueOf(DEFAULT_ACCOUNT_NUMBER))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.soma", is(entry1.getValue().doubleValue() + entry2.getValue().doubleValue())))
				.andExpect(jsonPath("$.min", is(entry1.getValue().doubleValue())))
				.andExpect(jsonPath("$.max", is(entry2.getValue().doubleValue())))
				.andExpect(jsonPath("$.media", is((entry1.getValue().doubleValue() + entry2.getValue().doubleValue()) / 2)))
				.andExpect(jsonPath("$.qtde", is(2)));
	}
	
	@Test
	public void shouldReturnAccountEntriesStats() throws Exception {
		AccountingEntry entry1 = new AccountingEntry();
		AccountingEntry entry2 = new AccountingEntry();

		entry1.setAccountNumber(DEFAULT_ACCOUNT_NUMBER);
		entry1.setDate(DEFAULT_DATE);
		entry1.setValue(DEFAULT_VALUE);

		entry2.setAccountNumber(1111002l);
		entry2.setDate(DEFAULT_DATE);
		entry2.setValue(30000.15);
		
		Mockito.when(service.getAll()).thenReturn(Arrays.asList(entry1, entry2));

		mvc.perform(get("/lancamentos-contabeis/_stats")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.soma", is(entry1.getValue().doubleValue() + entry2.getValue().doubleValue())))
				.andExpect(jsonPath("$.min", is(entry1.getValue().doubleValue())))
				.andExpect(jsonPath("$.max", is(entry2.getValue().doubleValue())))
				.andExpect(jsonPath("$.media", is((entry1.getValue().doubleValue() + entry2.getValue().doubleValue()) / 2)))
				.andExpect(jsonPath("$.qtde", is(2)));
	}
}
