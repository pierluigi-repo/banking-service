package it.pierluigi.banking.model;

import javax.validation.constraints.NotBlank;

public class Account {

    @NotBlank
    private String accountCode;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }
}
