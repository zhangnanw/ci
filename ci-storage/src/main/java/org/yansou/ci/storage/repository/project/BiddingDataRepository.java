package org.yansou.ci.storage.repository.project;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.storage.common.repository.GeneralRepository;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:15
 */
@Repository("biddingDataRepository")
public interface BiddingDataRepository extends GeneralRepository<BiddingData, Long> {

	List<BiddingData> findByProjectIdentifie(String projectIdentifie);

	@Modifying
	@Query("update BiddingData bean set bean.checked = ?2 where bean.id in (?1)")
	int updateChecked(Long[] ids, Integer checked);

}
