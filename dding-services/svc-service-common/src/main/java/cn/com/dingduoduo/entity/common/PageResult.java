package cn.com.dingduoduo.entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;

@JsonInclude(Include.NON_NULL)
public class PageResult<T> {
	public Collection<T> content;
	@JsonProperty("has_previous")
	public boolean hasPrevious;
	@JsonProperty("has_next")
	public boolean hasNext;
	@JsonProperty("total_pages")
	public int totalPages;
	@JsonProperty("total_records")
	public long totalRecords;
	@JsonProperty("records_in_page")
	public int numberOfRecords;

	public static <S> PageResult<S> buildFromSpringDataPage(Page<S> page) {
		PageResult<S> result = new PageResult<S>();
		result.content = page.getContent();
		result.hasNext = page.hasNext();
		result.hasPrevious = page.hasPrevious();
		result.numberOfRecords = page.getNumberOfElements();
		result.totalPages = page.getTotalPages();
		result.totalRecords = page.getTotalElements();
		return result;
	}

	public static <S,T> PageResult<T> buildFromSpringDataPage(Page<S> page,PageContentConverter<S, T> converter) {
		PageResult<T> result = new PageResult<T>();
		result.hasNext = page.hasNext();
		result.hasPrevious = page.hasPrevious();
		result.numberOfRecords = page.getNumberOfElements();
		result.totalPages = page.getTotalPages();
		result.totalRecords = page.getTotalElements();
		result.content = new ArrayList<T>();
		for (S model : page.getContent()) {
			result.content.add(converter.convert(model));
		}
		return result;
	}
	
	public static <S> PageResult<S> buildFromNonPage(Collection<S> collection) {
		PageResult<S> result = new PageResult<S>();
		result.content = collection;
		result.hasNext = false;
		result.hasPrevious = false;
		result.numberOfRecords = collection.size();
		result.totalRecords = collection.size();
		if (result.totalRecords > 0) {
			result.totalPages = 1;
		} else {
			result.totalPages = 0;
		}
		return result;
	}
	
	public static <L, T> PageResult<T> buildFromLegayPage(cn.com.dingduoduo.entity.common.Page<L> legacyPage,
                                                          PageContentConverter<L, T> converter) {
		PageResult<T> newPageResult = new PageResult<T>();
		newPageResult.totalPages = legacyPage.getTotalPage();
		newPageResult.totalRecords = legacyPage.getTotalCount();
		newPageResult.numberOfRecords = legacyPage.getResult().size();
		if (legacyPage.getStart() + legacyPage.getResult().size() >= legacyPage
				.getTotalCount()) {
			newPageResult.hasNext = false;
		} else {
			newPageResult.hasNext = true;
		}
		if (legacyPage.getTotalCount() > 0 && legacyPage.getStart() > 0) {
			newPageResult.hasPrevious = true;
		} else {
			newPageResult.hasPrevious = false;
		}

		newPageResult.content = new ArrayList<T>();
		for (L model : legacyPage.getResult()) {
			newPageResult.content.add(converter.convert(model));
		}
		return newPageResult;
	}
	
	public static <L, T> PageResult<T> buildFromLegayPage(PageResult<L> pageResult,
			PageContentConverter<L, T> converter) {
		PageResult<T> newPageResult = new PageResult<T>();
		newPageResult.totalPages = pageResult.totalPages;
		newPageResult.totalRecords = pageResult.totalRecords;
		newPageResult.numberOfRecords = pageResult.numberOfRecords;
		newPageResult.hasNext = pageResult.hasNext;
		newPageResult.hasPrevious = pageResult.hasPrevious;

		newPageResult.content = new ArrayList<T>();
		for (L model : pageResult.content) {
			newPageResult.content.add(converter.convert(model));
		}
		return newPageResult;
	}
	
	public static <T> PageResult<T> buildFromLegayPage(
			cn.com.dingduoduo.entity.common.Page<T> legacyPage) {
		PageResult<T> newPageResult = new PageResult<T>();
		newPageResult.totalPages = legacyPage.getTotalPage();
		newPageResult.totalRecords = legacyPage.getTotalCount();
		newPageResult.numberOfRecords = legacyPage.getResult().size();
		if (legacyPage.getStart() + legacyPage.getResult().size() >= legacyPage
				.getTotalCount()) {
			newPageResult.hasNext = false;
		} else {
			newPageResult.hasNext = true;
		}
		if (legacyPage.getTotalCount() > 0 && legacyPage.getStart() > 0) {
			newPageResult.hasPrevious = true;
		} else {
			newPageResult.hasPrevious = false;
		}

		newPageResult.content = legacyPage.getResult();
		return newPageResult;
	}
	

	@Override
	public String toString() {
		return "PageResult [content="
				+ (content == null ? null : content.size()) + ", hasPrevious="
				+ hasPrevious + ", hasNext=" + hasNext + ", totalPages="
				+ totalPages + ", totalRecords=" + totalRecords
				+ ", numberOfRecords=" + numberOfRecords + "]";
	}

}
