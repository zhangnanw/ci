package org.yansou.ci.storage.ciimp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yansou.ci.storage.CiStorageApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CiStorageApplication.class)
public class TestCorvToPlanBuild {
	@Autowired
	private CorvToPlanBuild corv;

	@Test
	public void testRun() throws Exception {
		corv.run();
	}
}
