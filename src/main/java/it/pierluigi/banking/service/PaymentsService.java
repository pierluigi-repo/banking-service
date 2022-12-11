package it.pierluigi.banking.service;

import it.pierluigi.banking.dto.request.MoneyTransferRequestDTO;
import it.pierluigi.banking.dto.response.MoneyTransferResponseDTO;
import it.pierluigi.banking.model.request.MoneyTransferRequest;
import it.pierluigi.banking.model.response.BaseResponse;
import it.pierluigi.banking.model.response.MoneyTransferResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Provides business logic functionalities for payment services
 */
@Service
public class PaymentsService {
    private static final Logger logger = LoggerFactory.getLogger(PaymentsService.class);

    @Value("${money.transfer.path}")
    private String moneyTransferPath;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Executes a money transfer to another account
     *
     * @param moneyTransferRequest Contains information to perform the money transfer
     *
     * @return The result of the money transfer
     */
    public MoneyTransferResponseDTO executeMoneyTransfer(MoneyTransferRequest moneyTransferRequest) {

        logger.info("begin executeMoneyTransfer - accountId: " + moneyTransferRequest.getAccountId());

        MoneyTransferRequestDTO moneyTransferRequestDTO =
                this.modelMapper.map(moneyTransferRequest, MoneyTransferRequestDTO.class);

        // money transfer submit
        Mono<BaseResponse<MoneyTransferResponse>> webClientResponse = this.webClient.post()
                .uri(this.moneyTransferPath, moneyTransferRequest.getAccountId())
                .header("X-Time-Zone", "Europe/Rome")
                .body(Mono.just(moneyTransferRequestDTO), MoneyTransferRequest.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<BaseResponse<MoneyTransferResponse>>() {});

        // encapsulate response from remote API
        BaseResponse<MoneyTransferResponse> moneyTransferResponse = Objects.requireNonNull(webClientResponse.block());

        // extract result
        MoneyTransferResponseDTO moneyTransferResponseDTO =
                this.modelMapper.map(moneyTransferResponse.getPayload(), MoneyTransferResponseDTO.class);

        logger.info("end executeMoneyTransfer - accountId: " + moneyTransferRequest.getAccountId());

        return moneyTransferResponseDTO;
    }
}
