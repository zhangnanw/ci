package org.yansou.ci.data.mining;

public class ATerm {

	public ATerm() {

	}

	public ATerm(String word, String nature, int offset) {
		this.word = word;
		this.nature = nature;
		this.offset = offset;
	}

	/**
	 * 词语
	 */
	private String word;

	/**
	 * 词性
	 */
	private String nature;

	/**
	 * 在文本中的起始位置（需开启分词器的offset选项）
	 */
	private int offset;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@Override
	public String toString() {
		return word + '/' + nature;
	}

	/**
	 * toString，并带上偏移量一起。
	 * 
	 * @return
	 */
	public String toStringBandOffset() {
		return word + '/' + nature + ":" + offset;
	}
}
