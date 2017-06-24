package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.core.rest.response.CountResponse;
import org.yansou.ci.web.business.GeneralBusiness;

/**
 * @author liutiejun
 * @create 2017-05-13 22:52
 */
public interface ProjectInfoBusiness extends GeneralBusiness<ProjectInfo, Long> {

	ProjectInfo[] findByProjectIdentifie(String projectIdentifie);

	CountResponse updateChecked(Long[] ids, Integer checked);

}
