package cn.com.dingduoduo.entity.common;

import java.util.Collections;
import java.util.List;

public class Page<E> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5874717906598880778L;
	private Integer pageSize;
	private Integer totalPage;
	private Integer totalCount;
	private Integer start;
	private Integer nowPage;
	private List<E> result = Collections.emptyList();

	public Page() {
	}

	public Page(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Page(Integer pageSize, Integer nowPage) {
		this.pageSize = pageSize;
		this.nowPage = nowPage;
	}

	public int getStart() {
		start = (getNowPage() - 1) * getPageSize();
		if (start < 0) {
			start = 0;
		}
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public void setNowPage(Integer nowPage) {
		this.nowPage = nowPage;
	}

	public Integer getTotalPage() {
		return  (int)Math.ceil(totalCount * 1.0 / pageSize);
	}

	public Integer getNowPage() {
		if (nowPage == null || nowPage <= 0)
			nowPage = 1;
		return nowPage;
	}

	@Override
	public String toString() {
		return "Page{" +
				"pageSize=" + pageSize +
				", totalPage=" + totalPage +
				", totalCount=" + totalCount +
				", start=" + start +
				", nowPage=" + nowPage +
				", result=" + result +
				'}';
	}

}