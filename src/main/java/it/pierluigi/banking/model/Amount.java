package it.pierluigi.banking.model;

public class Amount {
    public int debtorAmount;
    public String debtorCurrency;
    public int creditorAmount;
    public String creditorCurrency;
    public String creditorCurrencyDate;
    public int currencyRatio;

    public int getDebtorAmount() {
        return debtorAmount;
    }

    public void setDebtorAmount(int debtorAmount) {
        this.debtorAmount = debtorAmount;
    }

    public String getDebtorCurrency() {
        return debtorCurrency;
    }

    public void setDebtorCurrency(String debtorCurrency) {
        this.debtorCurrency = debtorCurrency;
    }

    public int getCreditorAmount() {
        return creditorAmount;
    }

    public void setCreditorAmount(int creditorAmount) {
        this.creditorAmount = creditorAmount;
    }

    public String getCreditorCurrency() {
        return creditorCurrency;
    }

    public void setCreditorCurrency(String creditorCurrency) {
        this.creditorCurrency = creditorCurrency;
    }

    public String getCreditorCurrencyDate() {
        return creditorCurrencyDate;
    }

    public void setCreditorCurrencyDate(String creditorCurrencyDate) {
        this.creditorCurrencyDate = creditorCurrencyDate;
    }

    public int getCurrencyRatio() {
        return currencyRatio;
    }

    public void setCurrencyRatio(int currencyRatio) {
        this.currencyRatio = currencyRatio;
    }
}
