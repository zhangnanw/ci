package wang.biaoshu.analyzer;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AResult implements Iterable<ATerm> {
	private List<ATerm> terms;

	public List<ATerm> getTerms() {
		return terms;
	}

	public AResult(List<ATerm> terms) {
		this.terms = terms;
	}

	@Override
	public Iterator<ATerm> iterator() {
		return terms.iterator();
	}

	public Stream<ATerm> stream() {
		return terms.stream();
	}

	/**
	 * 过滤词库
	 * 
	 * @param predicate
	 */
	public AResult filter(java.util.function.Predicate<ATerm> predicate) {
		terms = terms.stream().filter(predicate).collect(Collectors.toList());
		return this;
	}

}
