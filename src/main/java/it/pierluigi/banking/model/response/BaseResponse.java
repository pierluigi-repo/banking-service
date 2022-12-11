package it.pierluigi.banking.model.response;

import it.pierluigi.banking.model.Error;

import java.util.List;

/**
 * Base response returned from remote APIs
 *
 * @param <T> Payload type
 */
public class BaseResponse<T> {

    private String status;
    private T payload;

    private List<Error> errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
