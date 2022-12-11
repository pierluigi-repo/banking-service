package it.pierluigi.banking.service;

import it.pierluigi.banking.dto.BalanceDTO;
import it.pierluigi.banking.dto.TransactionDTO;
import it.pierluigi.banking.model.entity.TransactionRequestEntity;
import it.pierluigi.banking.model.response.BalanceResponse;
import it.pierluigi.banking.model.response.BaseResponse;
import it.pierluigi.banking.model.Transaction;
import it.pierluigi.banking.model.response.TransactionResponse;
import it.pierluigi.banking.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Provides business logic functionalities for account services
 */
@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    @Value("${balance.path}")
    private String balancePath;

    @Value("${transactions.path}")
    private String transactionsPath;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Retrieves the account balance
     *
     * @param accountId The id of the account
     *
     * @return Information about the balance
     */
    public BalanceDTO retrieveBalance(String accountId) {

        logger.info("begin retrieveBalance - accountId: " + accountId);

        // retrieve data from API
        Mono<BaseResponse<BalanceResponse>> webClientResponse = this.webClient.get()
                .uri(this.balancePath, accountId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<BalanceResponse>>() {});

        // encapsulate response from remote API
        BaseResponse<BalanceResponse> balanceResponse = Objects.requireNonNull(webClientResponse.block());

        // extract balance information
        BalanceDTO balanceDTO = this.modelMapper.map(balanceResponse.getPayload(), BalanceDTO.class);

        logger.info("end retrieveBalance - accountId: " + accountId);

        return balanceDTO;
    }

    /**
     * Retrieves the account transactions
     *
     * @param accountId The id of the account
     * @param fromAccountingDate The date from which data should be retrieved.
     * @param toAccountingDate The date to which data should be retrieved.
     *
     * @return The transactions list
     */
    public List<TransactionDTO> retrieveTransactions(String accountId,
                                                     String fromAccountingDate,
                                                     String toAccountingDate) {

        logger.info("begin retrieveTransactions - accountId: " + accountId);

        // encapsulate response from remote API
        // AtomicReference allows to use the response in lambda functions
        final AtomicReference<BaseResponse<TransactionResponse>> transactionResponse = new AtomicReference<>();

        try {

            // retrieve data from API
            Mono<BaseResponse<TransactionResponse>> webClientResponse = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(this.transactionsPath)
                            .queryParam("fromAccountingDate", fromAccountingDate)
                            .queryParam("toAccountingDate", toAccountingDate)
                            .build(accountId))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<TransactionResponse>>() {
                    });

            transactionResponse.set(Objects.requireNonNull(webClientResponse.block()));

            // extract transactions information
            List<TransactionDTO> transactionDTOList = transactionResponse.get().getPayload().getList().stream()
                    .map(t -> this.modelMapper.map(t, TransactionDTO.class))
                    .collect(Collectors.toList());

            logger.info("end retrieveTransactions - accountId: " + accountId);

            return transactionDTOList;

        } finally {

            // async non-blocking insert transactions into database
            new Thread(() -> {
                try {
                    TransactionRequestEntity transactionRequestEntity = new TransactionRequestEntity();
                    transactionRequestEntity.setAccountId(accountId);
                    transactionRequestEntity.setFromAccountingDate(LocalDate.parse(fromAccountingDate));
                    transactionRequestEntity.setToAccountingDate(LocalDate.parse(toAccountingDate));
                    if (transactionResponse.get() != null) {
                        transactionRequestEntity.setTransactionsId(transactionResponse.get().getPayload().getList().stream()
                                .map(Transaction::getTransactionId).collect(Collectors.toList()));
                    }
                    this.transactionRepository.save(transactionRequestEntity);

                } catch (Exception e) {
                    logger.error("Error while saving transactions in database: ", e);
                }
            }).start();
        }
    }

}
