package org.yansou.ci.common.exception;

/**
 * @author liutiejun
 * @create 2017-04-14 11:52
 */
public class DaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6406931883057312740L;

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
