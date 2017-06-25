package org.yansou.ci.storage.repository.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.MergeData;
import org.yansou.ci.storage.common.repository.GeneralRepository;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-21 12:19
 */
@Repository("mergeDataRepository")
public interface MergeDataRepository extends GeneralRepository<MergeData, Long> {

	List<MergeData> findByProjectIdentifie(String projectIdentifie);

	@Modifying
	@Query("update MergeData bean set bean.checked = ?2 where bean.id in (?1)")
	int updateChecked(Long[] ids, Integer checked);

}
