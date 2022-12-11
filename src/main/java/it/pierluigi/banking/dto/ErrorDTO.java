package it.pierluigi.banking.dto;

/**
 * Contains information returned by the application when an error occurs
 */
public class ErrorDTO {

    private String code;
    private String description;

    public ErrorDTO(String description, String code) {
        this.code = code;
        this.description = description;
    }

    public ErrorDTO(String description) {
        this.description = description;
        this.code = "ERRBNK";
    }

    public ErrorDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}