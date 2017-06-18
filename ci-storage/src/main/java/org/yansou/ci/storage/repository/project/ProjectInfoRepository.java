package org.yansou.ci.storage.repository.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.ProjectInfo;
import org.yansou.ci.storage.common.repository.GeneralRepository;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:15
 */
@Repository("projectInfoRepository")
public interface ProjectInfoRepository extends GeneralRepository<ProjectInfo, Long> {

	List<ProjectInfo> findByProjectIdentifie(String projectIdentifie);

	@Modifying
	@Query("update ProjectInfo bean set bean.checked = ?2 where bean.id in (?1)")
	int updateChecked(Long[] ids, Integer checked);

}
