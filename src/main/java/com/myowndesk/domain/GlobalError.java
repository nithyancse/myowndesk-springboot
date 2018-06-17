package com.myowndesk.domain;

import org.springframework.http.HttpStatus;

public class GlobalError {

	private HttpStatus status;
	private String message;
	private String error;

	public GlobalError(HttpStatus status, String message, String error) {
		this.status = status;
		this.message = message;
		this.error = error;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "ApiErrorResponse [status=" + status + ", message=" + message + ", error=" + error + "]";
	}

}