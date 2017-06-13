package org.yansou.ci.storage.merge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yansou.ci.storage.CiStorageApplication;

/**
 * Created by Administrator on 2017/6/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CiStorageApplication.class)
public class TestProjectMergeProcess {
    @Autowired
    private ProjectMergeProcess projectMergeProcess;

    @Test
    public void testProjectMergeProcess() {
        projectMergeProcess.run();
    }


}
