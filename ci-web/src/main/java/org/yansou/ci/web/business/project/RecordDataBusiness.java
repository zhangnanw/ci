package org.yansou.ci.web.business.project;

import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.web.business.GeneralBusiness;

/**
 * @author liutiejun
 * @create 2017-05-13 22:52
 */
public interface RecordDataBusiness extends GeneralBusiness<RecordData, Long> {

	RecordData[] findByProjectIdentifie(String projectIdentifie);

}
