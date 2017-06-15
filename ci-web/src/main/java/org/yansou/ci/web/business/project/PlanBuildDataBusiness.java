package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.web.business.GeneralBusiness;

/**
 * @author liutiejun
 * @create 2017-05-14 0:41
 */
public interface PlanBuildDataBusiness extends GeneralBusiness<PlanBuildData, Long> {

	PlanBuildData[] findByProjectIdentifie(String projectIdentifie);

}
