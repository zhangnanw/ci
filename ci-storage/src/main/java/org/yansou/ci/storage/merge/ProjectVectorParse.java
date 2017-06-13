package org.yansou.ci.storage.merge;

import org.yansou.ci.common.utils.PojoUtils;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.PlanBuildData;

/**
 * /**x项目向量引用 Created by Administrator on 2017/6/12.
 */
public class ProjectVectorParse {

	public ProjectVector parse(Object object) {
		if (object instanceof PlanBuildData) {
			PlanBuildData data = (PlanBuildData) object;
			ProjectVector projectVector = new ProjectVector();
			projectVector.setA1(data.getProjectProvince() + "");
			projectVector.setA2(data.getProjectCity());
			projectVector.setA3(data.getProjectDistrict());
			projectVector.setMw1(data.getProjectScale() + "");
			projectVector.setParty_a(data.getProjcetOwner());
			projectVector.setQuote(data);
			PojoUtils.nullStringToEmpty(projectVector);
			return projectVector;
		}
		if (object instanceof BiddingData) {
			BiddingData data = (BiddingData) object;
			ProjectVector projectVector = new ProjectVector();
			projectVector.setA1(data.getProjectProvince() + "");
			projectVector.setA2(data.getProjectCity());
			projectVector.setA3(data.getProjectDistrict());
			projectVector.setMw1(data.getProjectScale() + "");
			projectVector.setParty_a(data.getProjcetOwner());
			projectVector.setQuote(data);
			PojoUtils.nullStringToEmpty(projectVector);
			return projectVector;
		}
		return null;
	}
}
