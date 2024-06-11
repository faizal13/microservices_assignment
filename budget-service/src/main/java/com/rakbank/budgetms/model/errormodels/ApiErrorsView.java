package com.rakbank.budgetms.model.errormodels;

import java.util.List;

public class ApiErrorsView {
	private List<ApiFieldError> fieldErrors;
	private List<ApiGlobalError> globalErrors;

	public ApiErrorsView(List<ApiFieldError> fieldErrors, List<ApiGlobalError> globalErrors) {
		this.fieldErrors = fieldErrors;
		this.globalErrors = globalErrors;
	}

	public List<ApiFieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<ApiFieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public List<ApiGlobalError> getGlobalErrors() {
		return globalErrors;
	}

	public void setGlobalErrors(List<ApiGlobalError> globalErrors) {
		this.globalErrors = globalErrors;
	}

	@Override
	public String toString() {
		return "ApiErrorsView [fieldErrors=" + fieldErrors + ", globalErrors=" + globalErrors + "]";
	}

}