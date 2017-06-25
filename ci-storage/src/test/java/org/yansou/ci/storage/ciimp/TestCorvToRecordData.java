package org.yansou.ci.storage.ciimp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yansou.ci.storage.CiStorageApplication;

/**
 * Created by zhang on 2017/6/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CiStorageApplication.class)
public class TestCorvToRecordData {
    @Autowired
    private CorvToRecordData corvToRecordData;

    @Test
    public void testRun() {
        corvToRecordData.run();
    }
}
