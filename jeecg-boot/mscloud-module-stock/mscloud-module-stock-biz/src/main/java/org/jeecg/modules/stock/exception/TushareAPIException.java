package org.jeecg.modules.stock.exception;

public class TushareAPIException extends Exception {
	private static final long serialVersionUID = 1L;

	public TushareAPIException(String message){
		super(message);
	}

	public TushareAPIException(Throwable cause)
	{
		super(cause);
	}

	public TushareAPIException(String message, Throwable cause)
	{
		super(message,cause);
	}
}
