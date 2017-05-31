package org.yansou.ci.storage.ciimp;

import org.yansou.ci.core.model.project.BiddingData;
import org.yansou.ci.core.model.project.BiddingSnapshot;
import org.yansou.ci.core.model.project.PlanBuildData;
import org.yansou.ci.core.model.project.PlanBuildSnapshot;

/**
 * 导入过滤器
 * 
 * @author Administrator
 *
 */
public class LTFilter {

	private static final String[] KEYWORDS = { "光伏组件", "光伏面板", "太阳能电池板", "光伏电池组件", "单晶组件", "多晶组件", "晶硅组件" };

	final static public boolean isSave(PlanBuildData data, PlanBuildSnapshot snapshot) {
		String text = data.getProjectName() + snapshot.getContext();
		return _isSave(text);
	}

	final static public boolean isSave(BiddingData data, BiddingSnapshot snapshot) {
		String text = data.getProjectName() + snapshot.getContext();
		return _isSave(text);
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
