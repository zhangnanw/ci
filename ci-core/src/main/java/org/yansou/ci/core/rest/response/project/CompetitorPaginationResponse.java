package org.yansou.ci.core.rest.response.project;

import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.Competitor;
import org.yansou.ci.core.rest.response.RestResponse;

public class CompetitorPaginationResponse extends RestResponse<Pagination<Competitor>> {

	private static final long serialVersionUID = 788139308403259917L;
}
