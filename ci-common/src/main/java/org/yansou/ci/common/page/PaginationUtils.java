package org.yansou.ci.common.page;

/**
 * 分页计算工具类
 *
 * @author liutiejun
 * @create 2017-05-16 9:49
 */
public class PaginationUtils {

	/**
	 * 求出总页数
	 *
	 * @param totalCount ：总记录数
	 * @param pageSize ：每页显示的最多记录数
	 *
	 * @return
	 */
	public static int calTotalPage(int totalCount, int pageSize) {
		int totalPage = totalCount / pageSize;
		if (totalCount % pageSize != 0) {
			totalPage++;
		}

		return totalPage;
	}

	/**
	 * 计算当前页的开始下标
	 *
	 * @param currentPageNo ：当前页号
	 * @param pageSize ：每页显示的最多记录数
	 *
	 * @return
	 */
	public static int calFirstResult(int currentPageNo, int pageSize) {
		return calFirstResult(currentPageNo, pageSize, 0);
	}

	/**
	 * 计算当前页的开始下标
	 *
	 * @param currentPageNo ：当前页号
	 * @param pageSize ：每页显示的最多记录数
	 * @param minFirstResult ：第一页的开始下标，有可能是0，也有可能是1
	 *
	 * @return
	 */
	public static int calFirstResult(int currentPageNo, int pageSize, int minFirstResult) {
		return (currentPageNo - 1 < 0 ? 0 : currentPageNo - 1) * pageSize + minFirstResult;
	}

	/**
	 * 计算当前是第几页
	 *
	 * @param firstResult
	 * @param pageSize
	 *
	 * @return
	 */
	public static int calCurrentPageNo(int firstResult, int pageSize) {
		return calCurrentPageNo(firstResult, pageSize, 0);
	}

	/**
	 * 计算当前是第几页
	 *
	 * @param firstResult
	 * @param pageSize
	 * @param minFirstResult
	 *
	 * @return
	 */
	public static int calCurrentPageNo(int firstResult, int pageSize, int minFirstResult) {
		return (firstResult - minFirstResult) / pageSize + 1;
	}

}
