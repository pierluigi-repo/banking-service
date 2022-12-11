package it.pierluigi.banking.dto;

/**
 * Response body of the application balance service
 */
public class BalanceDTO {

    private Double balance;

    private String currency;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
