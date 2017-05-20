package org.yansou.ci.core.rest.response;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liutiejun
 * @create 2017-05-11 15:45
 */
public class SimpleRestResponse extends RestResponse<Object> {

	private static final long serialVersionUID = -3787227737679585867L;

	public static SimpleRestResponse ok() {
		return ok(RestStatus.OK.name());
	}

	public static SimpleRestResponse ok(Object result) {
		SimpleRestResponse restResponse = new SimpleRestResponse();

		restResponse.setStatus(RestStatus.OK.getValue());
		restResponse.setResult(result);

		return restResponse;
	}

	public static SimpleRestResponse ok(String key, Object value) {
		SimpleRestResponse restResponse = new SimpleRestResponse();

		restResponse.setStatus(RestStatus.OK.getValue());

		Map<String, Object> result = new HashMap<>();
		result.put(key, value);

		restResponse.setResult(result);

		return restResponse;
	}

	public static SimpleRestResponse exception() {
		return exception(RestStatus.INTERNAL_SERVER_ERROR.name());
	}

	public static SimpleRestResponse exception(Exception e) {
		String errors = null;
		if (e != null) {
			errors = e.getMessage();

			if (errors == null) {
				errors = e.getClass().getSimpleName();
			}
		}

		return exception(errors);
	}

	public static SimpleRestResponse exception(String errors) {
		SimpleRestResponse restResponse = new SimpleRestResponse();

		restResponse.setStatus(RestStatus.INTERNAL_SERVER_ERROR.getValue());
		restResponse.setErrors(errors);
		restResponse.setResult(errors);

		return restResponse;
	}

	public static SimpleRestResponse id(Long id) {
		if (id > 0) {
			return ok("id", id);
		}

		return exception();
	}

}
