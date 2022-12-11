package it.pierluigi.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.pierluigi.banking.dto.BalanceDTO;
import it.pierluigi.banking.dto.response.MoneyTransferResponseDTO;
import it.pierluigi.banking.dto.TransactionDTO;
import it.pierluigi.banking.model.request.MoneyTransferRequest;
import it.pierluigi.banking.service.AccountService;
import it.pierluigi.banking.service.PaymentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * This class exposes the rest APIs
 */
@RestController
@RequestMapping(value = "/banking", produces = MediaType.APPLICATION_JSON_VALUE)
public class BankingController {

    private static final Logger logger = LoggerFactory.getLogger(BankingController.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentsService paymentsService;

    @Operation(summary = "${api.banking.balance.summary}",
            description = "${api.banking.balance.description}")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/balance")
    public BalanceDTO balance(@Parameter(description = "${api.banking.balance.account}", name = "accountId")
                                  @RequestParam String accountId) {
        return accountService.retrieveBalance(accountId);
    }

    @Operation(summary = "${api.banking.transactions.summary}",
            description = "${api.banking.transactions.description}")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping(value = "/transactions")
    public List<TransactionDTO> transactions(@Parameter(description = "${api.banking.transactions.account}", name = "accountId")
                                       @RequestParam String accountId,
                                             @Parameter(description = "${api.banking.transactions.date.from}", name = "fromAccountingDate")
                                       @RequestParam String fromAccountingDate,
                                             @Parameter(description = "${api.banking.transactions.date.to}", name = "toAccountingDate")
                                       @RequestParam String toAccountingDate) {
        return accountService.retrieveTransactions(accountId, fromAccountingDate, toAccountingDate);
    }

    @Operation(summary = "${api.banking.transfers.summary}",
            description = "${api.banking.transfers.description}")
    @ApiResponse(responseCode = "200", description = "OK")
    @PostMapping(value = "/money-transfer")
    public MoneyTransferResponseDTO moneyTransfer(@RequestBody(required = true,
                                                  content = @Content(
                                                          schema=@Schema(implementation = MoneyTransferRequest.class)))
                                          @Valid @org.springframework.web.bind.annotation.RequestBody MoneyTransferRequest moneyTransferRequest) {
        return paymentsService.executeMoneyTransfer(moneyTransferRequest);
    }
}
