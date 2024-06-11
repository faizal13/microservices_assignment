package com.rakbank.budgetms.exceptionhandler;

import com.rakbank.budgetms.exception.BudgetServiceException;
import com.rakbank.budgetms.exception.RecordNotFoundException;
import com.rakbank.budgetms.model.CustomResponse;
import com.rakbank.budgetms.model.ErrorMessage;
import com.rakbank.budgetms.model.errormodels.ApiErrorsView;
import com.rakbank.budgetms.model.errormodels.ApiFieldError;
import com.rakbank.budgetms.model.errormodels.ApiGlobalError;
import com.rakbank.budgetms.model.errormodels.FieldErrorResponse;
import com.rakbank.budgetms.utils.Constant;
import com.rakbank.budgetms.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.script.ScriptException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	private static final Logger adviceLogger = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {

		BindingResult bindingResult = ex.getBindingResult();

		List<ApiFieldError> apiFieldErrors = bindingResult.getFieldErrors().stream()
				.map(fieldError -> new ApiFieldError(fieldError.getField(), fieldError.getCode(),
						fieldError.getRejectedValue()))
				.collect(Collectors.toList());

		List<ApiGlobalError> apiGlobalErrors = bindingResult.getGlobalErrors().stream()
				.map(globalError -> new ApiGlobalError(globalError.getCode())).collect(Collectors.toList());

		ApiErrorsView apiErrorsView = new ApiErrorsView(apiFieldErrors, apiGlobalErrors);
		FieldErrorResponse errorResponse = new FieldErrorResponse(apiErrorsView);

		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();

		CustomResponse<Object> response = ResponseUtils.getCustomResponse(400, errorResponse,
				Constant.FIELD_VALIDATION_ERROR, request);
		adviceLogger.error("For request path: {}", request.getServletPath());
		adviceLogger.error("Field errors are: {}", apiErrorsView);

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { NonTransientDataAccessException.class, RecoverableDataAccessException.class,
			ScriptException.class, TransientDataAccessException.class })
	protected ResponseEntity<CustomResponse<Object>> handleCrudRepositoryExceptions(DataAccessException ex,
			WebRequest webRequest) {
		adviceLogger.error("Database error occurred!", ex);
		int statusCode = 501;
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(statusCode, new ErrorMessage(ex.getMessage(), "Database error occurred!"),
				Constant.STATUS_CODE_501_DESC, request);
		return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<CustomResponse<Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {

		adviceLogger.error("Bad Request recieved!", ex);
		int statusCode = 400;
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(statusCode, new ErrorMessage(ex.getMessage(), "message not readable"),
				Constant.STATUS_CODE_400_DESC, request);
		return new ResponseEntity<>(customResponse, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<CustomResponse<Object>> handleException(Throwable ex, WebRequest webRequest) {
		adviceLogger.error("Exception {}",ex.getMessage());
		int statusCode = 500;
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(statusCode,
				new ErrorMessage(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
				Constant.STATUS_CODE_500_DESC, request);
		return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BudgetServiceException.class)
	public ResponseEntity<CustomResponse<Object>> handleBudgetServiceException(BudgetServiceException e, WebRequest webRequest) {
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(510,
				new ErrorMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
				e.getMessage(), request);
		return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<CustomResponse<Object>> handleRecordNotFoundException(RecordNotFoundException e, WebRequest webRequest) {
		HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
		CustomResponse<Object> customResponse = ResponseUtils.getCustomResponse(512, new ErrorMessage(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
				e.getMessage(), request);
		return new ResponseEntity<>(customResponse, HttpStatus.NOT_FOUND);
	}


}
