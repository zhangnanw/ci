package org.yansou.ci.core.rest.response.project;

import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.rest.response.RestResponse;

public class RecordDataPaginationResponse extends RestResponse<Pagination<RecordData>> {

	private static final long serialVersionUID = 2325759849848268804L;
}
