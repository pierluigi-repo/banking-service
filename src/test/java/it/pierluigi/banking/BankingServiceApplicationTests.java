package it.pierluigi.banking;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import it.pierluigi.banking.model.Account;
import it.pierluigi.banking.model.Creditor;
import it.pierluigi.banking.model.request.MoneyTransferRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=RANDOM_PORT)
@PropertySource("classpath:test.properties")
@EnableEncryptableProperties
public class BankingServiceApplicationTests {

	private static final String BALANCE_PATH = "/banking/balance";
	private static final String TRANSACTIONS_PATH = "/banking/transactions";
	private static final String MONEY_TRANSFER_PATH = "/banking/money-transfer";

	@Value("${test.account.id}")
	private String testAccountId;

	@Value("${test.account.code}")
	private String testAccountCode;

	@Test
	public void getBalanceOk() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("accountId", this.testAccountId);

		testGetCall(BALANCE_PATH, queryParams)
				.isOk()
				.expectBody().consumeWith(System.out::println);
	}

	@Test
	public void getBalanceInvalidId() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("accountId", "abc");

		testGetCall(BALANCE_PATH, queryParams)
				.isForbidden()
				.expectBody().consumeWith(System.out::println);
	}

	@Test
	public void getTransactionsOk() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("accountId", this.testAccountId);
		queryParams.add("fromAccountingDate", "2019-01-01");
		queryParams.add("toAccountingDate", "2019-12-01");

		testGetCall(TRANSACTIONS_PATH, queryParams)
				.isOk()
				.expectBody().consumeWith(System.out::println);
	}

	@Test
	public void getTransactionsMissingParam() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("accountId", this.testAccountId);
		queryParams.add("fromAccountingDate", "2019-01-01");

		testGetCall(TRANSACTIONS_PATH, queryParams)
				.isBadRequest()
				.expectBody().consumeWith(System.out::println);
	}

	@Test
	public void getTransactionsWrongInterval() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.add("accountId", this.testAccountId);
		queryParams.add("fromAccountingDate", "2019-01-01");
		queryParams.add("toAccountingDate", "2018-12-01");

		testGetCall(TRANSACTIONS_PATH, queryParams)
				.isBadRequest()
				.expectBody().consumeWith(System.out::println);
	}

	@Test
	public void executeMoneyTransferOk() {
		Account account = new Account();
		account.setAccountCode(this.testAccountCode);

		Creditor creditor = new Creditor();
		creditor.setAccount(account);
		creditor.setName("John Doe");

		MoneyTransferRequest mtr = new MoneyTransferRequest();
		mtr.setCreditor(creditor);
		mtr.setAccountId(Long.parseLong(this.testAccountId));
		mtr.setAmount(1D);
		mtr.setCurrency("EUR");
		mtr.setDescription("Test money transfer");
		mtr.setExecutionDate(getValidExecutionDate().toString());

		testPostCall(MONEY_TRANSFER_PATH, mtr)
				.isOk()
				.expectBody().consumeWith(System.out::println);
	}

	/**
	 * Performs the GET test call to the service
	 *
	 * @param path Service path
	 * @param queryParams Service query params map
	 *
	 * @return An object to test the result
	 */
	private StatusAssertions testGetCall(String path,
									  MultiValueMap<String, String> queryParams) {
		WebTestClient testClient = getWebTestClient();

		return testClient.get().uri(
						uriBuilder -> uriBuilder
								.path(path)
								.queryParams(queryParams)
								.build())
				.exchange()
				.expectStatus();

	}

	/**
	 * Performs the POST test call to the service
	 *
	 * @param path Service path
	 * @param requestBody Request body data
	 *
	 * @return An object to test the result
	 */
	private StatusAssertions testPostCall(String path,
										 Object requestBody) {
		WebTestClient testClient = getWebTestClient();

		return testClient.post().uri(
						uriBuilder -> uriBuilder
								.path(path)
								.build())
				.bodyValue(requestBody)
				.exchange()
				.expectStatus();
	}

	private WebTestClient getWebTestClient() {
		return WebTestClient.bindToServer()
				.baseUrl("http://localhost:8080")
				.responseTimeout(Duration.ofSeconds(30))
				.build();
	}

	private static final Set<String> HOLIDAYS = new HashSet<>();

	static {
		HOLIDAYS.add("01-01");
		HOLIDAYS.add("01-06");
		HOLIDAYS.add("04-25");
		HOLIDAYS.add("05-01");
		HOLIDAYS.add("06-02");
		HOLIDAYS.add("08-15");
		HOLIDAYS.add("11-01");
		HOLIDAYS.add("12-08");
		HOLIDAYS.add("12-25");
		HOLIDAYS.add("12-26");
	}

	/**
	 * Gets a valid execution date to test money transfer
	 *
	 * @return A valid execution date
	 */
	private LocalDate getValidExecutionDate() {
		LocalDate executionDate = LocalDate.now().plusDays(1);
		while (executionDate.getDayOfWeek() == SATURDAY ||
				executionDate.getDayOfWeek() == SUNDAY ||
				HOLIDAYS.contains(executionDate.toString().substring(5))) {
			executionDate = executionDate.plusDays(1);
		}

		return executionDate;
	}
}
