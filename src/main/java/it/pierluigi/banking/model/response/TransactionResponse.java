package it.pierluigi.banking.model.response;

import it.pierluigi.banking.model.Transaction;

import java.util.List;

/**
 * Response payload returned from remote transactions API
 */
public class TransactionResponse {

    private List<Transaction> list;

    public List<Transaction> getList() {
        return list;
    }

    public void setList(List<Transaction> list) {
        this.list = list;
    }
}
