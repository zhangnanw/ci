package org.yansou.ci.core.rest.response.project;

import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.core.rest.response.RestResponse;

public class ProjectInfoPaginationResponse extends RestResponse<Pagination<ProjectInfo>> {

	private static final long serialVersionUID = 5836783424033851971L;
}
