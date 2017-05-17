package com.yansou.ci.storage.ciimp;

import org.springframework.stereotype.Repository;

import com.yansou.ci.core.model.project.BiddingData;
import com.yansou.ci.storage.common.dao.GeneralDao;
@Repository
public interface BiddingDataDao extends GeneralDao<BiddingData, Long> {

}
