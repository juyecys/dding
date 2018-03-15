package cn.com.dingduoduo.entity.common;

/**
 * Convert from a legacy mybatis type to maybe a hibernate or dto type.
 * //From L to T.
 * @author leon
 *
 * @param <L>
 * @param <T>
 */
public interface PageContentConverter<L, T> {
	public T convert(L legacy);
}
