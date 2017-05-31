package org.yansou.ci.web.business.project.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.yansou.ci.web.business.project.SnapshotBusiness;

/**
 * @author liutiejun
 * @create 2017-05-31 21:52
 */
public class SnapshotBusinessImpl implements SnapshotBusiness {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String detail(String dataType, String dataId) {

		return null;
	}

}
