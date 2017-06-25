package org.yansou.ci.storage.service.project.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yansou.ci.common.exception.DaoException;
import org.yansou.ci.core.db.model.project.RecordData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.storage.common.repository.GeneralRepository;
import org.yansou.ci.storage.common.service.GeneralServiceImpl;
import org.yansou.ci.storage.repository.project.RecordDataRepository;
import org.yansou.ci.storage.service.project.RecordDataService;
import org.yansou.ci.storage.service.project.SnapshotInfoService;

import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-14 0:23
 */
@Service("recordDataService")
@Transactional
public class RecordDataServiceImpl extends GeneralServiceImpl<RecordData, Long> implements RecordDataService {
    private RecordDataRepository recordDataRepository;
    @Autowired
    private SnapshotInfoService snapshotInfoService;

    @Autowired
    @Qualifier("recordDataRepository")
    @Override
    public void setGeneralRepository(GeneralRepository<RecordData, Long> generalRepository) {
        this.generalRepository = generalRepository;
        this.recordDataRepository = (RecordDataRepository) generalRepository;
    }

    @Override
    public List<RecordData> findByProjectIdentifie(String projectIdentifie) throws DaoException {
        return recordDataRepository.findByProjectIdentifie(projectIdentifie);
    }

    @Override
    public int updateChecked(Long[] ids, Integer checked) throws DaoException {
        if (ArrayUtils.isEmpty(ids) || checked == null) {
            return -1;
        }

        return recordDataRepository.updateChecked(ids, checked);
    }

    public void saveDataAndSnapshotInfo(RecordData data, SnapshotInfo snap) throws DaoException {
        this.save(data);
        snapshotInfoService.save(snap);
    }
}
