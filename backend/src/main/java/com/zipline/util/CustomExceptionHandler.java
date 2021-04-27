package com.zipline.util;

import com.zipline.exception.*;
import com.zipline.model.Status;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

/**
 * The type Custom exception handler.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private static Status getStatus(final Integer code, final String message) {
        return new Status(code, message);
    }

    /**
     * Handle all exceptions response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(400, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle like already exists exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(LikeAlreadyExistsException.class)
    public final ResponseEntity<?> handleLikeAlreadyExistsException(LikeAlreadyExistsException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(406, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Handle no such like exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchLikeException.class)
    public final ResponseEntity<?> handleNoSuchLikeException(NoSuchLikeException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such post type exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchPostTypeException.class)
    public final ResponseEntity<?> handleNoSuchPostTypeException(NoSuchPostTypeException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such post for post type exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchPostForPostTypeException.class)
    public final ResponseEntity<?> handleNoSuchPostForPostTypeException(NoSuchPostForPostTypeException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no permission exception exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoPermissionException.class)
    public final ResponseEntity<?> handleNoPermissionExceptionException(NoPermissionException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(403, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle no such comment exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchCommentException.class)
    public final ResponseEntity<?> handleNoSuchCommentException(NoSuchCommentException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such user exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchUserException.class)
    public final ResponseEntity<?> handleNoSuchUserException(NoSuchUserException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such publication exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchPublicationException.class)
    public final ResponseEntity<?> handleSuchPublicationException(NoSuchPublicationException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such ticker exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchTickerException.class)
    public final ResponseEntity<?> handleNoSuchTickerException(NoSuchTickerException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such news exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchNewsException.class)
    public final ResponseEntity<?> handleNoSuchNewsException(NoSuchNewsException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such role exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchRoleException.class)
    public final ResponseEntity<?> handleNoSuchRoleException(NoSuchRoleException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle username not found exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle email already exists exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public final ResponseEntity<?> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(406, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Handle username already exists exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public final ResponseEntity<?> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(406, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Handle no such currency exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchCurrencyException.class)
    public final ResponseEntity<?> handleNoSuchCurrencyException(NoSuchCurrencyException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle no such token exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchTokenException.class)
    public final ResponseEntity<?> handleNoSuchTokenException(NoSuchTokenException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle authentication exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<?> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(400, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle io exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(IOException.class)
    public final ResponseEntity<?> handleIOException(IOException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(400, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle access denied exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(401, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle no such opened trade exception response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler(NoSuchOpenedTradeException.class)
    public final ResponseEntity<?> handleNoSuchOpenedTradeException(NoSuchOpenedTradeException ex, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ErrorResponse error = new ErrorResponse(getStatus(404, ex.getLocalizedMessage()));
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
