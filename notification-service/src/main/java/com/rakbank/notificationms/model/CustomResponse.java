package com.rakbank.notificationms.model;

public class CustomResponse<E> {

	private int status;

	private String description;

	private E data;
	
	private long date = System.currentTimeMillis();

	private String requestPath;

	public long getDate() {
		return date;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public CustomResponse() {
		// TODO Auto-generated constructor stub
	}

	public CustomResponse(int status, String description) {
		super();
		this.status = status;
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "CustomResponse [status=" + status + ", description=" + description + ", data=" + data + "]";
	}

	public CustomResponse(int status, String description, E data, String requestPath) {
		this.status = status;
		this.description = description;
		this.data = data;
		this.requestPath = requestPath;
	}
}
