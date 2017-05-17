package wang.biaoshu.analyzer.ansj;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.ansj.domain.Result;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;

import wang.biaoshu.analyzer.AResult;
import wang.biaoshu.analyzer.ATerm;
import wang.biaoshu.analyzer.Analysis;

public class AnsjAnalysis implements Analysis {

	// 白名单

	private ToAnalysis analy;
	/**
	 * 自定义词性解析器
	 * @param words
	 * @param cx
	 */
	public AnsjAnalysis(Set<String> words,String cx) {
		Forest forest = new Forest();
		AtomicLong score = new AtomicLong(40000);
		words.forEach(w -> {
			String[] paramers = new String[2];
			paramers[0] = cx;// 设置词性
			paramers[1] = score.get() + "";// 设置分数
			Value value = new Value(w, paramers);
			Library.insertWord(forest, value);
		});
		analy = new ToAnalysis(forest);
	}

	@Override
	public AResult recognition(String text) {
		Result result = analy.parseStr(text);
		List<ATerm> terms = new ArrayList<>();
		result.getTerms().stream().map(t -> new ATerm(t.getRealName(), t.getNatureStr(), t.getOffe()))
				.forEach(terms::add);
		return new AResult(terms);
	}

}
