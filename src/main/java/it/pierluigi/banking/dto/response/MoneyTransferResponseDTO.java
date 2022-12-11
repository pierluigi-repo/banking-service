package it.pierluigi.banking.dto.response;

import it.pierluigi.banking.model.Creditor;

/**
 * Response body of the application money transfer service
 */
public class MoneyTransferResponseDTO {

    private String status;
    private String direction;
    private String description;
    private Creditor creditor;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Creditor getCreditor() {
        return creditor;
    }

    public void setCreditor(Creditor creditor) {
        this.creditor = creditor;
    }

}
