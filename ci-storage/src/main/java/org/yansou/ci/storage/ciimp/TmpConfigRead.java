package org.yansou.ci.storage.ciimp;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.yansou.ci.common.utils.RegexUtils;

public class TmpConfigRead {
	private static String config_file = "application.yml";
	private static String cfgName;
	static {
		try {
			String appStr = IOUtils.toString(ClassLoader.getSystemResourceAsStream(config_file), "UTF-8");
			String tmpName = RegexUtils.regex("(?is)active: *([a-z]+)", appStr, 1);
			String fileName = "application-" + tmpName + ".yml";
			String cfgContext = IOUtils.toString(ClassLoader.getSystemResourceAsStream(fileName), "UTF-8");
			cfgName = RegexUtils.regex("url\\s*:\\s*jdbc:mysql://[^:/]+:3306/([0-9a-z-]+)", cfgContext, 1);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public static String getCfgName() {
		return cfgName;
	}
}
