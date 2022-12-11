package it.pierluigi.banking.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pierluigi.banking.dto.ErrorDTO;
import it.pierluigi.banking.model.response.BaseResponse;
import it.pierluigi.banking.model.Error;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static it.pierluigi.banking.configuration.MessagesConfiguration.ERROR_BAD_REQUEST;
import static it.pierluigi.banking.configuration.MessagesConfiguration.ERROR_GENERIC_INTERNAL;

/**
 * This interceptor manages all unhandled exceptions in the application
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MessageSource messageSource;

    // remote APIs errors
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorDTO> webClientError(WebRequest request, WebClientResponseException ex) {
        HttpStatus code = ex.getStatusCode();
        ErrorDTO errorDTO = createErrorInfo(code, request, ex);
        return new ResponseEntity<>(errorDTO, code);
    }

    // invalid inputs
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorDTO missingParamsError(WebRequest request, MissingServletRequestParameterException ex) {
        return createErrorInfo(HttpStatus.BAD_REQUEST, request, ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO invalidRequestBodyError(WebRequest request, MethodArgumentNotValidException ex) {
        return createErrorInfo(HttpStatus.BAD_REQUEST, request, ex);
    }

    // unhandled internal exceptions
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorDTO genericError(WebRequest request, Exception ex) {
        return createErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, request, ex);
    }

    /**
     * Creates the error response
     *
     * @param httpStatus Error status code
     * @param request Web request
     * @param ex Exception thrown by application or WebClient
     *
     * @return An error response body
     */
    private ErrorDTO createErrorInfo(HttpStatus httpStatus, WebRequest request, Exception ex) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        logger.error("Returning status code: {}, for path: {}", httpStatus, path, ex);

        // custom error message key
        String errorKey = "";
        // loads requested locale, if present
        String acceptLanguage = request.getHeader("Accept-Language");
        Locale locale = null;
        if (acceptLanguage != null) {
            locale = Locale.forLanguageTag(acceptLanguage);
        }

        try {
            // parse remote APIs errors
            if (ex instanceof WebClientResponseException) {
                ObjectMapper objectMapper = new ObjectMapper();
                BaseResponse<?> res = objectMapper
                        .readValue(((WebClientResponseException) ex)
                                .getResponseBodyAsByteArray(), BaseResponse.class);
                List<Error> errors = res.getErrors();
                Optional<Error> error = errors.stream().findFirst();
                if (error.isPresent()) {
                    return this.modelMapper.map(error.get(), ErrorDTO.class);
                } else {
                    errorKey = ERROR_GENERIC_INTERNAL;
                }
            // manage unhandled internal exceptions
            } else {
                switch (httpStatus) {
                    case BAD_REQUEST:
                        errorKey = ERROR_BAD_REQUEST;
                        break;
                    // here can customize other status code descriptions
                    default:
                        errorKey = ERROR_GENERIC_INTERNAL;
                }
            }
        } catch (Exception e) {
            logger.error("Error: ", e);
            errorKey = ERROR_GENERIC_INTERNAL;
        }

        String errorDescription = this.messageSource.getMessage(errorKey, null, locale);

        return new ErrorDTO(errorDescription);

    }

}