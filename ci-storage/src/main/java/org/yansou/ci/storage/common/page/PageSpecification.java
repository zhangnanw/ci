package org.yansou.ci.storage.common.page;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jpa.criteria.CriteriaBuilderImpl;
import org.springframework.data.jpa.domain.Specification;
import org.yansou.ci.common.page.ColumnInfo;
import org.yansou.ci.common.page.PageCriteria;
import org.yansou.ci.common.page.SearchInfo;
import org.yansou.ci.common.utils.ClassUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liutiejun
 * @create 2017-05-26 0:37
 */
public class PageSpecification<T> implements Specification<T> {

	private final static String OR_SEPARATOR = "+";
	private final static String ESCAPED_OR_SEPARATOR = "\\+";
	private final static String ATTRIBUTE_SEPARATOR = ".";
	private final static String ESCAPED_ATTRIBUTE_SEPARATOR = "\\.";
	private final static char ESCAPE_CHAR = '\\';
	private final static String NULL = "NULL";
	private final static String ESCAPED_NULL = "\\NULL";

	private PageCriteria pageCriteria;

	public PageSpecification() {
	}

	public PageSpecification(PageCriteria pageCriteria) {
		this.pageCriteria = pageCriteria;
	}

	public PageCriteria getPageCriteria() {
		return pageCriteria;
	}

	public void setPageCriteria(PageCriteria pageCriteria) {
		this.pageCriteria = pageCriteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicateList = new ArrayList<>();

		List<ColumnInfo> columnInfoList = pageCriteria.getColumnInfoList();
		if (CollectionUtils.isNotEmpty(columnInfoList)) {
			columnInfoList.stream().forEach(columnInfo -> {
				Predicate predicate = toPredicate(columnInfo, root, cb);

				if (predicate != null) {
					predicateList.add(predicate);
				}

			});

		}

		return cb.and(predicateList.toArray(new Predicate[0]));
	}

	private Predicate toPredicate(ColumnInfo columnInfo, Root<?> root, CriteriaBuilder cb) {
		if (!columnInfo.getSearchable()) {
			return null;
		}

		SearchInfo searchInfo = columnInfo.getSearchInfo();

		if (searchInfo == null) {
			return null;
		}

		// 查询的字段
		String propertyName = searchInfo.getPropertyName();
		// 查询的字段值
		String[] values = searchInfo.getValues();
		// 字段类型
		String valueType = searchInfo.getValueType();
		// 字段的查询方式：EQ、NE、LIKE、GT、GE、LT、LE、BETWEEN、IN、IS_NULL、IS_NOT_NULL
		SearchInfo.SearchOp searchOp = searchInfo.getSearchOp();

		if (StringUtils.isBlank(propertyName) || ArrayUtils.isEmpty(values) || searchOp == null) {
			return null;
		}

		Class<?> clazz = ClassUtils.getClass(valueType);

		Expression<?> expression = getExpression(root, propertyName, clazz);

		if (searchOp == SearchInfo.SearchOp.EQ) {
			Object realValue = ClassUtils.getValue(values[0], valueType);

			return cb.equal(expression, realValue);
		}

		if (searchOp == SearchInfo.SearchOp.NE) {
			Object realValue = ClassUtils.getValue(values[0], valueType);

			return cb.notEqual(expression, realValue);
		}

		if (searchOp == SearchInfo.SearchOp.LIKE) {
			String realValue = values[0];

			return cb.like((Expression<String>) expression, "%" + realValue + "%");
		}

		if (searchOp == SearchInfo.SearchOp.GT) {
			Comparable realValue = (Comparable) ClassUtils.getValue(values[0], valueType);

			return cb.greaterThan((Expression<? extends Comparable>) expression, realValue);
		}

		if (searchOp == SearchInfo.SearchOp.GE) {
			Comparable realValue = (Comparable) ClassUtils.getValue(values[0], valueType);

			return cb.greaterThanOrEqualTo((Expression<? extends Comparable>) expression, realValue);
		}

		if (searchOp == SearchInfo.SearchOp.LT) {
			Comparable realValue = (Comparable) ClassUtils.getValue(values[0], valueType);

			return cb.lessThan((Expression<? extends Comparable>) expression, realValue);
		}

		if (searchOp == SearchInfo.SearchOp.LE) {
			Comparable realValue = (Comparable) ClassUtils.getValue(values[0], valueType);

			return cb.lessThanOrEqualTo((Expression<? extends Comparable>) expression, realValue);
		}

		if (searchOp == SearchInfo.SearchOp.BETWEEN) {
			Comparable minValue = (Comparable) ClassUtils.getValue(values[0], valueType);
			Comparable maxValue = (Comparable) ClassUtils.getValue(values[1], valueType);

			return cb.between((Expression<? extends Comparable>) expression, minValue, maxValue);
		}

		if (searchOp == SearchInfo.SearchOp.IN) {
			Object[] realValues = ClassUtils.getValueList(values, valueType);

			return ((CriteriaBuilderImpl) cb).in(expression, realValues);
		}

		if (searchOp == SearchInfo.SearchOp.IS_NULL) {
			return cb.isNull(expression);
		}

		if (searchOp == SearchInfo.SearchOp.IS_NOT_NULL) {
			return cb.isNotNull(expression);
		}

		return null;
	}

	private static <S> Expression<S> getExpression(Root<?> root, String propertyName, Class<S> clazz) {
		if (!propertyName.contains(ATTRIBUTE_SEPARATOR)) {
			// propertyName is like "attribute" so nothing particular to do
			return root.get(propertyName).as(clazz);
		}

		// propertyName is like "joinedEntity.attribute" so add a join clause
		String[] values = propertyName.split(ESCAPED_ATTRIBUTE_SEPARATOR);
		if (root.getModel().getAttribute(values[0]).getPersistentAttributeType() == Attribute.PersistentAttributeType
				.EMBEDDED) {
			// with @Embedded attribute
			return root.get(values[0]).get(values[1]).as(clazz);
		}

		From<?, ?> from = root;
		for (int i = 0; i < values.length - 1; i++) {
			from = from.join(values[i], JoinType.LEFT);
		}

		return from.get(values[values.length - 1]).as(clazz);
	}

}
