package com.rakbank.expensems.model.errormodels;

public class ApiGlobalError {
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ApiGlobalError(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ApiGlobalError [code=" + code + "]";
	}

}