package org.yansou.ci.storage.repository.project;

import org.springframework.stereotype.Repository;
import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.storage.common.repository.GeneralRepository;

/**
 * @author liutiejun
 * @create 2017-05-14 0:15
 */
@Repository("biddingDataRepository")
public interface BiddingDataRepository extends GeneralRepository<BiddingData, Long> {

}
