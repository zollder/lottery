package org.app.model;

import java.io.Serializable;


public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean success;
	private String message;
	private String errorCode;

	public Response(Boolean success, String message) {
		this.message = message;
		this.success = success;
		this.errorCode = null;
	}

	public Response(Boolean success, String message, String code)
	{
		this.message = message;
		this.success = success;
		this.errorCode = code;
	}

	public Boolean getSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
