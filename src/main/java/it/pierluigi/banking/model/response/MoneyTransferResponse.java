package it.pierluigi.banking.model.response;

import it.pierluigi.banking.model.Amount;
import it.pierluigi.banking.model.Creditor;
import it.pierluigi.banking.model.Debtor;
import it.pierluigi.banking.model.Fee;

import java.util.Date;
import java.util.List;

/**
 * Response payload returned from remote money transfer API
 */
public class MoneyTransferResponse {

    public String moneyTransferId;
    public String cro;
    public String trn;
    public String status;
    public String uri;
    public String direction;
    public Debtor debtor;
    public Creditor creditor;
    public String feeAccountId;
    public String description;
    public Date createdDatetime;
    public Date accountedDatetime;
    public String debtorValueDate;
    public String creditorValueDate;
    public Amount amount;
    public Boolean isUrgent;
    public Boolean isInstant;
    public String feeType;
    public List<Fee> fees;
    public Boolean hasTaxRelief;

    public String getMoneyTransferId() {
        return moneyTransferId;
    }

    public void setMoneyTransferId(String moneyTransferId) {
        this.moneyTransferId = moneyTransferId;
    }

    public String getCro() {
        return cro;
    }

    public void setCro(String cro) {
        this.cro = cro;
    }

    public String getTrn() {
        return trn;
    }

    public void setTrn(String trn) {
        this.trn = trn;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Debtor getDebtor() {
        return debtor;
    }

    public void setDebtor(Debtor debtor) {
        this.debtor = debtor;
    }

    public Creditor getCreditor() {
        return creditor;
    }

    public void setCreditor(Creditor creditor) {
        this.creditor = creditor;
    }

    public String getFeeAccountId() {
        return feeAccountId;
    }

    public void setFeeAccountId(String feeAccountId) {
        this.feeAccountId = feeAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getAccountedDatetime() {
        return accountedDatetime;
    }

    public void setAccountedDatetime(Date accountedDatetime) {
        this.accountedDatetime = accountedDatetime;
    }

    public String getDebtorValueDate() {
        return debtorValueDate;
    }

    public void setDebtorValueDate(String debtorValueDate) {
        this.debtorValueDate = debtorValueDate;
    }

    public String getCreditorValueDate() {
        return creditorValueDate;
    }

    public void setCreditorValueDate(String creditorValueDate) {
        this.creditorValueDate = creditorValueDate;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Boolean getUrgent() {
        return isUrgent;
    }

    public void setUrgent(Boolean urgent) {
        isUrgent = urgent;
    }

    public Boolean getInstant() {
        return isInstant;
    }

    public void setInstant(Boolean instant) {
        isInstant = instant;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }

    public Boolean getHasTaxRelief() {
        return hasTaxRelief;
    }

    public void setHasTaxRelief(Boolean hasTaxRelief) {
        this.hasTaxRelief = hasTaxRelief;
    }
}

