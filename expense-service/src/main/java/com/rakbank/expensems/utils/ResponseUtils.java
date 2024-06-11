package com.rakbank.expensems.utils;


import com.rakbank.expensems.model.CustomResponse;
import jakarta.servlet.http.HttpServletRequest;


public class ResponseUtils {

	public static CustomResponse<Object> getCustomResponse(int statusCode, Object data, String description,
														   HttpServletRequest request) {
		CustomResponse<Object> customResponse = new CustomResponse<Object>();
		customResponse.setData(data);
		customResponse.setDescription(description);
		customResponse.setStatus(statusCode);
		customResponse.setRequestPath(null == request.getServletPath() ? "" : request.getServletPath());
		return customResponse;
	}

	private ResponseUtils() {
	}

}
