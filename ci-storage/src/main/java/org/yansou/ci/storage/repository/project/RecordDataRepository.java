package org.yansou.ci.storage.repository.project;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.storage.common.repository.GeneralRepository;

import java.util.List;

@Repository("recordDataRepository")
public interface RecordDataRepository extends GeneralRepository<RecordData, Long> {

	List<RecordData> findByProjectIdentifie(String projectIdentifie);

}
