package cn.com.dingduoduo.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Page<E> {

	/**
	 * 
	 */
	@JsonProperty("pageSize")
	private Integer pageSize;
	@JsonProperty("totalPage")
	private Integer totalPage;
	@JsonProperty("totalCount")
	private Integer totalCount;
	@JsonProperty("start")
	private Integer start;
	@JsonProperty("nowPage")
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