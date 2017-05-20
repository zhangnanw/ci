package org.yansou.ci.common.page;

import java.io.Serializable;

import org.yansou.ci.common.utils.GsonUtils;

/**
 * 用于分页显示数据
 *
 * @param <T>
 *
 * @author liutiejun
 * @create 2017-05-10 18:14
 */
public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = -5261756891229264182L;

	/**
	 * 每一页默认显示最多10条数据
	 */
	private static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 最多显示10个分页链接
	 */
	private static final int MAX_PAGE_LEN = 10;

	/**
	 * 总记录数
	 */
	private Long totalCount;

	/**
	 * 每页显示的最多记录数
	 */
	private Integer pageSize;

	/**
	 * 总页数
	 */
	private Integer totalPage;

	/**
	 * 最多显示多少页, 用来限制要显示的页数
	 */
	private Integer maxPage;

	/**
	 * 当前页号
	 */
	private Integer currentPageNo;

	/**
	 * 上一页
	 */
	private Integer prePageNo;

	/**
	 * 下一页
	 */
	private Integer nextPageNo;

	/**
	 * 是否第一页
	 */
	private Boolean firstPage;

	/**
	 * 是否最后一页
	 */
	private Boolean lastPage;

	/**
	 * 分页链接对应的pageNo
	 */
	private Integer[] pageNoArray;

	/**
	 * 当前页的数据
	 */
	private T[] dataArray;

	public Pagination() {
		super();
	}

	public Pagination(Long totalCount, Integer pageSize, Integer currentPageNo, T[] dataArray) {
		this(totalCount, pageSize, -1, currentPageNo, dataArray);
	}

	public Pagination(Long totalCount, Integer pageSize, Integer maxPage, Integer currentPageNo, T[] dataArray) {
		super();

		adjust(totalCount, pageSize, maxPage, currentPageNo);

		this.dataArray = dataArray;
	}

	/**
	 * 调整分页参数
	 *
	 * @param totalCount
	 * @param pageSize
	 * @param maxPage
	 * @param currentPageNo
	 */
	private void adjust(Long totalCount, Integer pageSize, Integer maxPage, Integer currentPageNo) {
		// set totalCount
		if (totalCount == null || totalCount <= 0) {
			totalCount = 0L;
		}
		this.totalCount = totalCount;

		// set pageSize
		if (pageSize == null || pageSize <= 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		this.pageSize = pageSize;

		// set maxPage and totalPage
		this.totalPage = calTotalPage(this.totalCount, this.pageSize);

		if (maxPage == null || maxPage <= 0 || maxPage > this.totalPage) {
			maxPage = this.totalPage;
		}
		this.maxPage = maxPage;
		this.totalPage = maxPage;

		// set currentPageNo
		if (currentPageNo == null || currentPageNo <= 0) {
			currentPageNo = 1;
		} else if (currentPageNo > this.totalPage) {
			currentPageNo = this.totalPage;
		}
		this.currentPageNo = currentPageNo;

		// set prePageNo
		this.prePageNo = this.currentPageNo - 1;
		if (this.prePageNo <= 0) {
			this.prePageNo = 1;
		}

		// set nextPageNo
		this.nextPageNo = this.currentPageNo + 1;
		if (this.nextPageNo > this.totalPage) {
			this.nextPageNo = this.totalPage;
		}

		// set firstPage and lastPage
		this.firstPage = this.currentPageNo <= 1;
		this.lastPage = this.currentPageNo >= this.totalPage;

		// set pageNoArray
		int startPageNo = this.currentPageNo - MAX_PAGE_LEN / 2;
		if (startPageNo <= 0) {
			startPageNo = 1;
		}
		if (this.totalPage - startPageNo < MAX_PAGE_LEN - 1) {
			startPageNo = this.totalPage - MAX_PAGE_LEN + 1;
		}
		if (startPageNo <= 0) {
			startPageNo = 1;
		}

		int endPageNo = startPageNo + MAX_PAGE_LEN - 1;
		if (endPageNo > this.totalPage) {
			endPageNo = this.totalPage;
		}

		this.pageNoArray = new Integer[endPageNo - startPageNo + 1];
		for (int i = 0; i < this.pageNoArray.length; i++) {
			this.pageNoArray[i] = startPageNo + i;
		}
	}

	private Integer calTotalPage(Long totalCount, Integer pageSize) {
		if (totalCount == 0L || pageSize == 0) {
			return 1;
		}

		return (int) Math.ceil(totalCount.doubleValue() / pageSize.doubleValue());
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(Integer maxPage) {
		this.maxPage = maxPage;
	}

	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getPrePageNo() {
		return prePageNo;
	}

	public void setPrePageNo(Integer prePageNo) {
		this.prePageNo = prePageNo;
	}

	public Integer getNextPageNo() {
		return nextPageNo;
	}

	public void setNextPageNo(Integer nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public Boolean getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Boolean firstPage) {
		this.firstPage = firstPage;
	}

	public Boolean getLastPage() {
		return lastPage;
	}

	public void setLastPage(Boolean lastPage) {
		this.lastPage = lastPage;
	}

	public Integer[] getPageNoArray() {
		return pageNoArray;
	}

	public void setPageNoArray(Integer[] pageNoArray) {
		this.pageNoArray = pageNoArray;
	}

	public T[] getDataArray() {
		return dataArray;
	}

	public void setDataArray(T[] dataArray) {
		this.dataArray = dataArray;
	}

	@Override
	public String toString() {
		return GsonUtils._gson.toJson(this);
	}

}
