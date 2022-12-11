package it.pierluigi.banking.model.request;

import it.pierluigi.banking.model.Creditor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Request body of money transfer service
 */
public class MoneyTransferRequest {

    @NotNull
    private Long accountId;
    @Valid
    @NotNull
    private Creditor creditor;

    @NotBlank
    private String description;

    @NotBlank
    private String currency;

    @NotNull
    private Double amount;

    @NotBlank
    private String executionDate;

    public Creditor getCreditor() {
        return creditor;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setCreditor(Creditor creditor) {
        this.creditor = creditor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }
}