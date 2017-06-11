package org.yansou.ci.storage.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSOUPUtils {
	/**
	 * 
	 * @param element
	 * @param cssQuery
	 * @param consumers
	 * @return 返回一个流
	 */
	@SafeVarargs
	private static Optional<Element> find0(Object element, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		Elements es = null;
		if (element instanceof Element) {
			es = ((Element) element).select(cssQuery);
		} else if (element instanceof Elements) {
			es = ((Elements) element).select(cssQuery);
		} else if (element instanceof CharSequence) {
			es = Jsoup.parse(element.toString()).select(cssQuery);
		} else {
			throw new IllegalArgumentException("element type err:" + element.getClass());
		}
		if (Objects.nonNull(es) && !es.isEmpty()) {
			Element e = es.get(0);
			for (Consumer<Element> act : consumers) {
				act.accept(es.get(0));
			}
			return Optional.ofNullable(e);
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param element
	 * @param cssQuery
	 * @param consumers
	 * @return 返回一个流
	 */
	@SafeVarargs
	private static Stream<Element> finds0(Object element, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		Elements es = null;
		if (element instanceof Element) {
			es = ((Element) element).select(cssQuery);
		} else if (element instanceof Elements) {
			es = ((Elements) element).select(cssQuery);
		} else if (element instanceof CharSequence) {
			es = Jsoup.parse(element.toString()).select(cssQuery);
		} else {
			throw new IllegalArgumentException("element type err:" + element.getClass());
		}
		if (!es.isEmpty()) {
			Builder<Element> builder = Stream.builder();
			es.forEach(e -> {
				builder.add(e);
				Arrays.asList(consumers).forEach(act -> {
					act.accept(e);
				});
			});

			return builder.build();
		}

		return Stream.empty();
	}

	@SafeVarargs
	public static Optional<Element> find(Element element, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		return find0(element, cssQuery, consumers);
	}

	@SafeVarargs
	public static Optional<Element> find(Elements elements, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		return find0(elements, cssQuery, consumers);
	}

	@SafeVarargs
	public static Optional<Element> find(CharSequence html, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		return find0(html, cssQuery, consumers);
	}

	@SafeVarargs
	public static Stream<Element> finds(Element elements, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		return finds0(elements, cssQuery, consumers);
	}

	@SafeVarargs
	public static Stream<Element> finds(Elements elements, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		return finds0(elements, cssQuery, consumers);
	}

	@SafeVarargs
	public static Stream<Element> finds(CharSequence html, String cssQuery,
			java.util.function.Consumer<Element>... consumers) {
		return finds0(html, cssQuery, consumers);
	}
}
