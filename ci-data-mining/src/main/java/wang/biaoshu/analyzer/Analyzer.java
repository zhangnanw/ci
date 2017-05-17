package wang.biaoshu.analyzer;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhang
 *
 */
public interface Analyzer {
	/**
	 * 
	 * @param obj
	 * @return
	 */
	JSONObject analy(JSONObject obj);

	/**
	 * 
	 * @param obj
	 * @return
	 */
	default boolean match(JSONObject obj) {
		return true;
	}

}
