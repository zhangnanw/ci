package wang.biaoshu.analyzer.util;

import java.math.BigDecimal;
import java.util.Arrays;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Chars;

/**
 * 数字转换为汉语中人民币的大写<br>
 * 
 * @author hongten
 * @contact hongtenzone@foxmail.com
 * @create 2013-08-13
 */
public class NumberToCN {
	/**
	 * 汉语中数字大写
	 */
	private static final char[] CN_UPPER_NUMBER = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };

	private enum MoneyUnit {
		分 {
			@Override
			long toFen(long num) {
				return num;
			}
		},
		角 {
			@Override
			long toFen(long num) {
				return num * 10;
			}
		},
		元 {
			@Override
			long toFen(long num) {
				return num * 100;
			}
		},
		拾 {
			@Override
			long toFen(long num) {
				return num * 1000;
			}
		},
		佰 {
			@Override
			long toFen(long num) {

				return num * 10000;
			}
		},
		仟 {
			@Override
			long toFen(long num) {
				return num * 100000;
			}
		},
		万 {
			@Override
			long toFen(long num) {
				return num * 1000000;
			}
		},
		亿 {
			@Override
			long toFen(long num) {
				return num * 10000000000L;
			}
		}

		;
		abstract long toFen(long num);
	}

	/**
	 * 汉语中货币单位大写，这样的设计类似于占位符
	 */
	private static final char[] CN_UPPER_MONETRAY_UNIT = { '分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾',
			'佰', '仟', '兆', '拾', '佰', '仟' };

	/**
	 * 特殊字符：整
	 */
	private static final char CN_FULL = '整';
	/**
	 * 特殊字符：负
	 */
	private static final char CN_NEGATIVE = '负';
	/**
	 * 金额的精度，默认值为2
	 */
	private static final int MONEY_PRECISION = 2;
	/**
	 * 特殊字符：零元整
	 */
	private static final String CN_ZEOR_FULL = "零元" + CN_FULL;
	final static public String CN_UPPER_NUMBERS = Chars.asList(CN_UPPER_NUMBER).stream().map(a -> a + "")
			.reduce((a, b) -> a + b).get()
			+ Arrays.asList(MoneyUnit.values()).stream().map(a -> a.name()).reduce((a, b) -> a + b).get() + CN_FULL;

	/**
	 * 把输入的金额转换为汉语中人民币的大写
	 * 
	 * @param numberOfMoney
	 *            输入的金额
	 * @return 对应的汉语大写
	 */
	public static String number2CNMontrayUnit(BigDecimal numberOfMoney) {
		StringBuffer sb = new StringBuffer();
		// -1, 0, or 1 as the value of this BigDecimal is negative, zero, or
		// positive.
		int signum = numberOfMoney.signum();
		// 零元整的情况
		if (signum == 0) {
			return CN_ZEOR_FULL;
		}
		// 这里会进行金额的四舍五入
		long number = numberOfMoney.movePointRight(MONEY_PRECISION).setScale(0, 4).abs().longValue();
		// 得到小数点后两位值
		long scale = number % 100;
		int numUnit = 0;
		int numIndex = 0;
		boolean getZero = false;
		// 判断最后两位数，一共有四中情况：00 = 0, 01 = 1, 10, 11
		if (!(scale > 0)) {
			numIndex = 2;
			number = number / 100;
			getZero = true;
		}
		if ((scale > 0) && (!(scale % 10 > 0))) {
			numIndex = 1;
			number = number / 10;
			getZero = true;
		}
		int zeroSize = 0;
		while (true) {
			if (number <= 0) {
				break;
			}
			// 每次获取到最后一个数
			numUnit = (int) (number % 10);
			if (numUnit > 0) {
				if ((numIndex == 9) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[6]);
				}
				if ((numIndex == 13) && (zeroSize >= 3)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[10]);
				}
				sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				getZero = false;
				zeroSize = 0;
			} else {
				++zeroSize;
				if (!(getZero)) {
					sb.insert(0, CN_UPPER_NUMBER[numUnit]);
				}
				if (numIndex == 2) {
					if (number > 0) {
						sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
					}
				} else if (((numIndex - 2) % 4 == 0) && (number % 1000 > 0)) {
					sb.insert(0, CN_UPPER_MONETRAY_UNIT[numIndex]);
				}
				getZero = true;
			}
			// 让number每次都去掉最后一个数
			number = number / 10;
			++numIndex;
		}
		// 如果signum == -1，则说明输入的数字为负数，就在最前面追加特殊字符：负
		if (signum == -1) {
			sb.insert(0, CN_NEGATIVE);
		}
		// 输入的数字小数点后两位为"00"的情况，则要在最后追加特殊字符：整
		if (!(scale > 0)) {
			sb.append(CN_FULL);
		}
		return sb.toString();
	}

	/**
	 * 千万别用，还没写好呢。
	 * 
	 * @param str
	 * @return
	 */
	@Deprecated
	public static BigDecimal chToNum(String str) {
		long price = 0;
		long tmpPrice = 0;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			int num = Arrays.binarySearch(CN_UPPER_NUMBER, c);
			if (num >= 0) {
				tmpPrice = num;
			} else {
				Preconditions.checkArgument(tmpPrice > 0);
				MoneyUnit unit = MoneyUnit.valueOf(c + "");
				if (unit != null) {
					tmpPrice = unit.toFen(tmpPrice);
				}
				if (c == '零') {
					price += tmpPrice;
				}
			}
		}
		return new BigDecimal(price);
	}

	public static void main(String[] args) {
		System.out.println(CN_UPPER_NUMBERS);
	}
}
