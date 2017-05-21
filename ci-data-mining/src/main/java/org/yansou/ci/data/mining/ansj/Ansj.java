package org.yansou.ci.data.mining.ansj;

import java.util.concurrent.atomic.AtomicBoolean;

import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;
import org.yansou.ci.data.mining.iio.OSSIIOAdapter;

/**
 * ANSJ逻辑。
 *
 * @author zhang
 *
 */
public class Ansj {
	public static final String DEFAULT_NATURE = "userDefine";

	public static final Integer DEFAULT_FREQ = 1000;

	public static final String DEFAULT_FREQ_STR = "1000";
	public static final Forest default_forest = new Forest();

	public Ansj() {

	}

	private static Ansj ansj = new Ansj();

	private static AtomicBoolean isInit = new AtomicBoolean(false);

	public static Ansj getInstance() {
		try {
			insertWord();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ansj;
	}

	/**
	 * ANSJ导入机构名词库
	 *
	 * @author shi
	 *
	 */
	public static void insertWord() throws Exception {
		synchronized (isInit) {
			if (isInit.get()) {
				return;
			}
		}
		OSSIIOAdapter iio = new OSSIIOAdapter("http://yansoudic.oss-cn-qingdao.aliyuncs.com/ansj/");
		String path = "library/userLibrary/CompanyName1.dic";
		int limitSize = 10000;
		UserDefineLibrary.insertWord("国家图书馆");
		UserDefineLibrary.insertWord("中央编译局");
		IOUtils.readLines(iio.open(path), "UTF-8").stream().skip(1).limit(limitSize).forEach(line -> {
			if (StringUtils.isNotBlank(line)) {
				UserDefineLibrary.insertWord(line.trim());
			}
		});
		System.out.println("ansj party_a loaded.");
		isInit.set(true);
	}

	static void addWord(String word) {
		Value value = new Value(word.trim(), DEFAULT_NATURE, DEFAULT_FREQ_STR);
		Library.insertWord(default_forest, value);
	}

	public String raragraphProcess(String text) {
		return ToAnalysis.parse(text).toString(" ");
	}

}
