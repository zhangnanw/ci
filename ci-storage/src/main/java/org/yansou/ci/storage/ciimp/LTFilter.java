package org.yansou.ci.storage.ciimp;

import org.apache.commons.lang3.StringUtils;
import org.yansou.ci.core.db.model.project.BiddingData;
import org.yansou.ci.core.db.model.project.PlanBuildData;
import org.yansou.ci.core.db.model.project.SnapshotInfo;

/**
 * 导入过滤器
 * 
 * @author Administrator
 *
 */
public class LTFilter {

	private static final String[] KEYWORDS = { "光伏组件", "光伏面板", "太阳能电池板", "光伏电池组件", "单晶组件", "多晶组件", "晶硅组件" };

	final static public boolean isSave(PlanBuildData data, SnapshotInfo snapshot) {
		return true;
	}

	final static public boolean isSave(BiddingData data, SnapshotInfo snapshot) {
		String title = data.getProjectName();
		if (StringUtils.isNotBlank(title)) {
			return _isSave(title);
		} else {
			String context = snapshot.getContext();
			return _isSave(context);
		}
	}

	final static private boolean _isSave(String text) {
		if (null == text) {
			return false;
		}
		for (String keyword : KEYWORDS) {
			if (text.contains(keyword)) {
				return true;
			}
		}
		return false;
	}
}
