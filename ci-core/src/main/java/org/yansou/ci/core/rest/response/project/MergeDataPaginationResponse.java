package org.yansou.ci.core.rest.response.project;

import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.core.rest.response.RestResponse;

public class MergeDataPaginationResponse extends RestResponse<Pagination<MergeData>> {

	private static final long serialVersionUID = -1918633692014643440L;
}
