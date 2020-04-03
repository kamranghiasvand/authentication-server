package com.bluebox.planner.auth.rest.aspect;

import com.bluebox.planner.auth.common.Constants;
import com.bluebox.planner.auth.common.exception.DuplicateResourceException;
import com.bluebox.planner.auth.common.exception.GlobalException;
import com.bluebox.planner.auth.common.exception.ResourceNotFoundException;
import com.bluebox.planner.auth.common.util.ExceptionUtil;
import com.bluebox.planner.auth.rest.RestErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.bluebox.planner.auth.common.Constants.*;
import static java.text.MessageFormat.format;


/**
 * @author kamran ghiasvand
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<RestErrorResponse> handleException(Exception exception) {
        ResponseEntity<RestErrorResponse> result = checkDuplicateException(exception);
        if (result != null)
            return result;
        ResponseEntity<RestErrorResponse> fkError = checkSaveOrUpdateException(exception);
        if (fkError != null)
            return fkError;
        return unhandledError(exception);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RestErrorResponse> handleException(MissingServletRequestParameterException exception) {
        return new ResponseEntity<>(
                new RestErrorResponse(Constants.ERROR_VALIDATION,
                        Collections.singletonList(format(VALIDATION_IS_NULL_MSG, exception.getParameterName())))
                , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<RestErrorResponse> handleException(MissingServletRequestPartException exception) {
        return new ResponseEntity<>(
                new RestErrorResponse(Constants.ERROR_VALIDATION,
                        Collections.singletonList(format(VALIDATION_IS_NULL_MSG, exception.getRequestPartName())))
                , HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<RestErrorResponse> handleException(JpaObjectRetrievalFailureException exception) {
        String message = ExceptionUtil.fkManyToManyException(exception);
        if (!"".equals(message))
            return handleException(new ResourceNotFoundException(message));
        return unhandledError(exception);
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<RestErrorResponse> handleException(GlobalException ex) {
        if (ex.getMessages() != null && !ex.getMessages().isEmpty()) {
            Object o = ex.getMessages().get(0);
            LOGGER.error(o.toString(), ex);
        } else
            LOGGER.error("", ex);
        return new ResponseEntity<>(new RestErrorResponse(ex.getKey(), ex.getMessages()), ex.getStatus());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<RestErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex) {
        return new ResponseEntity<>(new RestErrorResponse(ERROR_NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LOGGER.error(ex.getMessage(), ex);
        List<String> errorList = new ArrayList<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        if (!allErrors.isEmpty()) {
            allErrors.forEach(err -> errorList.add(err.getDefaultMessage()));
        }
        RestErrorResponse restErrorResponse = new RestErrorResponse(Constants.ERROR_VALIDATION, errorList);
        return new ResponseEntity<>(restErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestErrorResponse> illegalArgumentException(IllegalArgumentException ex) {
        return clientError(ex);
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<RestErrorResponse> illegalArgumentException(HttpMessageConversionException ex) {
        return clientError(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorResponse> handleException(HttpMessageNotReadableException exception) {
        return clientError(new Exception("not readable body"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RestErrorResponse> handleException(MethodArgumentTypeMismatchException exception) {
        LOGGER.error(exception.getMessage(), exception);
        ArrayList<String> messages = new ArrayList<>();
        messages.add(MessageFormat.format(VALIDATION_ARGUMENT_MSG, exception.getValue(), exception.getName()));
        return new ResponseEntity<>(new RestErrorResponse(Constants.ERROR_VALIDATION, messages), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<RestErrorResponse> handleException(MissingPathVariableException exception) {
        return clientError(exception);
    }


    private ResponseEntity<RestErrorResponse> clientError(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        ArrayList<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        return new ResponseEntity<>(new RestErrorResponse(Constants.ERROR_CLIENT, messages), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<RestErrorResponse> unhandledError(Exception exception) {
        LOGGER.error(exception.getMessage(), exception);
        ArrayList<String> messages = new ArrayList<>();
        messages.add(exception.getMessage());
        return new ResponseEntity<>(new RestErrorResponse(Constants.ERROR_UNHANDLED, messages), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<RestErrorResponse> checkDuplicateException(Exception ex) {
        boolean b = ExceptionUtil.isConstraintViolationExp(ex);
        boolean b1 = ExceptionUtil.isDuplicateExp(ex);
        if (b && b1) {
            DuplicateResourceException exception = new DuplicateResourceException(ExceptionUtil.findMessage(ex));
            return handleException(exception);
        }
        return null;
    }

    private ResponseEntity<RestErrorResponse> checkSaveOrUpdateException(Exception ex) {
        String s = ExceptionUtil.fkException(ex);
        if (s != null && !"".equals(s))
            return handleException(new ResourceNotFoundException(s));
        return null;
    }


}
