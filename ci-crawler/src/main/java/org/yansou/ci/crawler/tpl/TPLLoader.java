package org.yansou.ci.crawler.tpl;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * tpl文件加载器,会识别属性中的@parent属性自动加载父tpl文件.
 * 
 * @author zhangnan1
 *
 */
public abstract class TPLLoader {
	public Properties get(String path) throws IOException {
		LinkedHashSet<String> pathSet = new LinkedHashSet<String>();
		return get0(path, pathSet);
	}

	public abstract File getFile(String path);

	private Properties get0(String path, Set<String> pathSet)
			throws IOException {
		if (StringUtils.isBlank(path)) {
			throw new IOException("The path cannot be null");
		}
		if (pathSet.contains(path)) {
			throw new IOException("Prohibition of circular references:{" + path
					+ "} ->" + pathSet);
		}
		pathSet.add(path);
		TPLProp prop = new TPLProp();
		String str = readText(path);
		if (null == str) {
			throw new IOException("file error.");
		}
		prop.load(new StringReader(str));
		String parentPath = prop.getProperty("@parent");
		if (StringUtils.isNotBlank(parentPath)) {
			prop.setParent(get0(parentPath, pathSet));
		}
		return prop;
	}

	protected abstract String readText(String path) throws IOException;

	private static class TPLProp extends Properties {
		private static final long serialVersionUID = -6270627866821594848L;

		/**
		 * 设置父模版
		 * 
		 * @param properties
		 */
		public void setParent(Properties properties) {
			defaults = properties;
		}

		@Override
		public String getProperty(String key) {
			Object oval = super.get(key);
			String sval = (oval instanceof String) ? (String) oval : null;
			return ((StringUtils.isBlank(sval)) && (defaults != null)) ? defaults
					.getProperty(key) : sval;
		}

		@Override
		public String getProperty(String key, String defaultValue) {
			String val = getProperty(key);
			return (val == null) ? defaultValue : val;
		}

	}

	private final static TPLLoader classLoaderTPLLoader = new TPLLoader() {
		@Override
		protected String readText(String path) throws IOException {
			return IOUtils.toString(
					ClassLoader.getSystemResourceAsStream(path),
					Charset.forName("utf-8"));
		}

		@Override
		public File getFile(String path) {
			throw new UnsupportedOperationException();
		}
	};

	public static TPLLoader classLoaderTPLLoader() {
		return classLoaderTPLLoader;
	}

	public static TPLLoader filePathTPLLoader(final String rootPath)
			throws IOException {
		return filePathTPLLoader(new File(rootPath));
	}

	public static TPLLoader filePathTPLLoader(final File rootPath)
			throws IOException {
		String root = rootPath.getCanonicalPath();
		return new TPLLoader() {
			@Override
			protected String readText(String path) throws IOException {
				return FileUtils.readFileToString(getFile(path),
						Charset.forName("UTF-8"));
			}

			public File getFile(String path) {
				return new File(root + File.separator + path);
			}
		};
	}
}
