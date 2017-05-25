package org.yansou.ci.storage.dao.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.storage.CiStorageApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CiStorageApplication.class)
public class TestPlanBuildDataDao {
	@Autowired
	private PlanBuildDataDao dao;

	@Test
	public void testGetByID() throws Exception {
		PlanBuildData pan = dao.findByProjectIdentifie(null);
		System.out.println(pan);
	}
}
