package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-21 12:26
 */
public interface MergeDataService extends GeneralService<MergeData, Long> {

	List<MergeData> findByProjectIdentifie(String projectIdentifie) throws DaoException;

	int updateChecked(Long[] ids, Integer checked) throws DaoException;

}
