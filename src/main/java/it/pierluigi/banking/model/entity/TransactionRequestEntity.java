package it.pierluigi.banking.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity for transactions db tracking
 */
@Entity
@Table(name = "transactions_request")
public class TransactionRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "request_timestamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime requestTimestamp = LocalDateTime.now();

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "from_accounting_date")
    private LocalDate fromAccountingDate;

    @Column(name = "to_accounting_date")
    private LocalDate toAccountingDate;

    @ElementCollection
    @CollectionTable(name = "transactions_request_id", joinColumns = @JoinColumn(name = "transaction_request_id", referencedColumnName = "id"))
    @Column(name = "transaction_id")
    private List<String> transactionsId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public LocalDate getFromAccountingDate() {
        return fromAccountingDate;
    }

    public void setFromAccountingDate(LocalDate fromAccountingDate) {
        this.fromAccountingDate = fromAccountingDate;
    }

    public LocalDate getToAccountingDate() {
        return toAccountingDate;
    }

    public void setToAccountingDate(LocalDate toAccountingDate) {
        this.toAccountingDate = toAccountingDate;
    }

    public List<String> getTransactionsId() {
        return transactionsId;
    }

    public void setTransactionsId(List<String> transactionsId) {
        this.transactionsId = transactionsId;
    }
}
