package com.rakbank.budgetms.model.errormodels;


public class FieldErrorResponse {

	private ApiErrorsView error;

	public FieldErrorResponse(ApiErrorsView error) {
		this.error = error;
	}

	public ApiErrorsView getError() {
		return error;
	}

	public void setError(ApiErrorsView error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "FieldErrorResponse [error=" + error + "]";
	}

}
