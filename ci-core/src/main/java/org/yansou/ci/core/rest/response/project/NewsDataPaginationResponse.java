package org.yansou.ci.core.rest.response.project;

import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.NewsData;
import org.yansou.ci.core.rest.response.RestResponse;

public class NewsDataPaginationResponse extends RestResponse<Pagination<NewsData>> {

	private static final long serialVersionUID = 2922512547664935068L;
}
