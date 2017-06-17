package org.yansou.ci.storage.repository.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.storage.common.repository.GeneralRepository;

import java.util.List;

@Repository("recordDataRepository")
public interface RecordDataRepository extends GeneralRepository<RecordData, Long> {

	List<RecordData> findByProjectIdentifie(String projectIdentifie);

	@Modifying
	@Query("update RecordData bean set bean.checked = ?2 where bean.id in (?1)")
	int updateChecked(Long[] ids, Integer checked);

}
