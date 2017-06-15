package org.yansou.ci.storage.service.project;

import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.storage.common.service.GeneralService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-21 12:26
 */
public interface ProjectInfoService extends GeneralService<ProjectInfo, Long> {

	List<ProjectInfo> findByProjectIdentifie(String projectIdentifie) throws DaoException;

}
