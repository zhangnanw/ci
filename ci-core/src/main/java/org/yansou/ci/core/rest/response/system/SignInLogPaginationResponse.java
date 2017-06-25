package org.yansou.ci.core.rest.response.system;

import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.system.SignInLog;
import org.yansou.ci.core.rest.response.RestResponse;

public class SignInLogPaginationResponse extends RestResponse<Pagination<SignInLog>> {

	private static final long serialVersionUID = 8890545053663742938L;
}
