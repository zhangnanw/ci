package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-21 12:27
 */
public interface RecordDataService extends GeneralService<RecordData, Long> {

	List<RecordData> findByProjectIdentifie(String projectIdentifie) throws DaoException;

}
