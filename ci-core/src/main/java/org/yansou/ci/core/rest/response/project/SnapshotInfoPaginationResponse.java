package org.yansou.ci.core.rest.response.project;

import org.yansou.ci.common.page.Pagination;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.core.rest.response.RestResponse;

public class SnapshotInfoPaginationResponse extends RestResponse<Pagination<SnapshotInfo>> {

	private static final long serialVersionUID = 1757382739752004964L;
}
