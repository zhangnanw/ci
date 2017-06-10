package org.yansou.ci.crawler.core;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.yansou.ci.crawler.tpl.TPLLoader;
import org.yansou.ci.crawler.tpl.TplUtil;
import org.yansou.ci.crawler.tpl.URLParser;

import com.google.common.collect.Lists;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;

public class EntityProcessorInProperties<T> implements PageProcessor, Closeable {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EntityProcessorInProperties.class);
	public static final String TPLPATH = "crawl.tplpath";
	private Downloader downloader = new HttpClientDownloader();
	private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
	private ScriptEngine scriptEngine;
	private Invocable invocable;
	private List<Field> fieldList;
	private Properties props;
	// 模版中需要的工具方法存放对象
	private TplUtil util = TplUtil.getTplUtil();
	// 中间页编号记录。
	private final List<Integer> listNums = new ArrayList<>();
	// 参数编号记录。
	private final List<Integer> pNums = new ArrayList<>();
	// 中间页面的编号上限，大于等于此数字编号的中间页将不被处理
	// 最大参数个数
	private int maxArgNum = 10;
	private Site site = Site.me();
	private boolean debug = false;

	private AtomicInteger linkCounter = new AtomicInteger(0);
	private AtomicInteger outCounter = new AtomicInteger(0);
	private AtomicInteger insCount = new AtomicInteger(0);
	// 经典名称，无须解释。供生成JS使用.
	private StringBuffer sb = new StringBuffer();
	private File scriptFile;
	private RangeStat rangeStat = RangeStat.ALLRANGE;
	private Class<T> clazz;

	private EntityProcessorInProperties(File scriptFile) {
		this.scriptFile = scriptFile;
		openEngine();
	}

	/**
	 * 
	 * @param propserties
	 *            模版配置集合
	 * @param isIncrement
	 *            是否是增量抓取
	 */
	private EntityProcessorInProperties(Properties propserties) {
		this.props = propserties;
		// 初始化环境
		openEngine();
		// 创建JS文件
		generateJavaScriptFile();

	}

	private void initAll() {
		loadJavaScript();
		initSiteUUID();
		runSiteConf();
	}

	/**
	 * 初始化UUID，使用随机UUID
	 */
	private void initSiteUUID() {
		site = new MySite(UUID.randomUUID().toString());
	}

	private enum RangeStat {
		/**
		 * 全部调用
		 */
		ALLRANGE {
			@Override
			void rangeforEach(IntConsumer action) {
				IntStream.range(0, 99).forEach(action);
			}
		},
		/**
		 * 增量抓取
		 */
		INCREMENTAL_CRAWL {
			@Override
			void rangeforEach(IntConsumer action) {
				IntStream.range(0, 9).forEach(action);
			}
		},
		/**
		 * 全量抓取
		 */
		FULL_CRAWL {
			@Override
			void rangeforEach(IntConsumer action) {
				IntStream.range(0, 19).forEach(action);
			}
		},
		/**
		 * 搜索抓取
		 */
		SEARCH_CRAWL {
			@Override
			void rangeforEach(IntConsumer action) {
				// IntStream.range(0, 9).forEach(action);
				IntStream.range(20, 29).forEach(action);
			}
		};

		abstract void rangeforEach(IntConsumer action);
	}

	private void setIncrement(boolean isIncrement) {
		if (isIncrement) {
			rangeStat = RangeStat.INCREMENTAL_CRAWL;
		} else {
			rangeStat = RangeStat.FULL_CRAWL;
		}

	}

	private void runSiteConf() {
		site.setCycleRetryTimes(3);
		if (null != scriptEngine.get("siteConf")) {
			try {
				invocable.invokeFunction("siteConf", site);
			} catch (NoSuchMethodException | ScriptException e) {
				throw new IllegalArgumentException(e);
			}
		}

	}

	private static TPLLoader tplLoader;

	public final static void setTPLLoader(TPLLoader tplLoader) {
		EntityProcessorInProperties.tplLoader = tplLoader;
	}

	private static TPLLoader tplLoader() {
		if (null == tplLoader) {
			synchronized (TPLLoader.class) {
				if (null == tplLoader) {
					try {
						tplLoader = TPLLoader.filePathTPLLoader(System.getProperty(TPLPATH, "./alltpl"));
					} catch (IOException e) {
						throw new IllegalArgumentException(e);
					}
				}
			}
		}
		return tplLoader;
	}

	public final static <T> EntityProcessorInProperties<T> create(Seed<T> seed) throws IOException {
		EntityProcessorInProperties<T> paperProc;
		String path = seed.tplPath;
		path = path.replaceAll(".tpl$", "");
		File jsFile = tplLoader().getFile(path + ".js");
		if (jsFile.isFile()) {
			paperProc = new EntityProcessorInProperties<T>(jsFile);
		} else {
			paperProc = new EntityProcessorInProperties<T>(tplLoader.get(path + ".tpl"));
		}
		// 设置增量或全量抓取
		paperProc.setIncrement(seed.isFetch != 0);
		// 设置种子如果种子是搜索，则变换状态为搜索抓取
		paperProc.setSeed(seed);
		paperProc.initAll();
		return paperProc;
	}

	@SuppressWarnings("unchecked")
	public List<String> getSearchLink() {
		Object res;
		try {
			res = invocable.invokeFunction("searchLink");
			if (null == res) {
				return Arrays.asList();
			}
			if (res instanceof List) {
				return (List<String>) res;
			}
			return Arrays.asList(res.toString());
		} catch (NoSuchMethodException | ScriptException e) {
			throw new IllegalArgumentException(e);
		}

	}

	static <T> EntityProcessorInProperties<T> create(Properties props) {
		EntityProcessorInProperties<T> paperProc = new EntityProcessorInProperties<T>(props);
		paperProc.initAll();
		return paperProc;
	}

	private void setSeed(Seed<T> seed) {
		if (StringUtils.startsWith(seed.seedUrl, "keyword:")) {
			System.out.println(seed.seedUrl);
			String val = seed.seedUrl.replaceAll("keyword:[^:]*?:", "");
			System.out.println(val);
			scriptEngine.put("keyword", val);
			rangeStat = RangeStat.SEARCH_CRAWL;
		} else {
			scriptEngine.put("keyword", "");
		}
		scriptEngine.put("seed", seed);
	}

	@SuppressWarnings("unchecked")
	private Seed<T> getSeed() {
		return (Seed<T>) scriptEngine.get("seed");
	}

	/**
	 * 打开js引擎。
	 */
	private void openEngine() {
		scriptEngine = scriptEngineManager.getEngineByName("nashorn");
		// 取消小写util写法，防止歧义
		// scriptEngine.put("util", util);
		scriptEngine.put("Util", util);
		scriptEngine.put("Db", new EntityDB());
		invocable = (Invocable) scriptEngine;
		setSeed(new Seed<T>());
		sb.append("var StringUtils=org.apache.commons.lang.StringUtils;");
		sb.append('\n');
		sb.append('\n');
		sb.append('\n');

	}

	public class EntityDB {
		public boolean isInsert(String value) {
			return true;
		}
	}

	/**
	 * 创建脚本文件
	 */
	@SuppressWarnings("unchecked")
	private void generateJavaScriptFile() {
		// 基本参数相关

		String siteConfScript = props.getProperty("@siteConf");
		if (StringUtils.isNotBlank(siteConfScript)) {
			sb.append("function siteConf(site){");
			sb.append('\n');
			sb.append("    ");
			sb.append(siteConfScript);
			if (!siteConfScript.trim().endsWith(";")) {
				sb.append(';');
			}
			sb.append('\n');
			sb.append('}');
		} else {
			String sleepTime = props.getProperty("@sleep");
			if (StringUtils.isNotBlank(sleepTime)) {
				sleepTime = sleepTime.trim();
				if (sleepTime.endsWith(";")) {
					sleepTime = sleepTime.replaceAll(";$", "");
				}
				if (StringUtils.isNumeric(sleepTime)) {
					sb.append("function siteConf(site){");
					sb.append('\n');
					sb.append("    ");
					sb.append("site.sleepTime=");
					sb.append(sleepTime);
					sb.append(';');
					sb.append('\n');
					sb.append('}');
				}
			}
		}
		// 搜索相关
		String searchLinkScript = StringUtils.trim(props.getProperty("@searchLink"));
		if (StringUtils.isNotBlank(searchLinkScript)) {
			createFunctionNoParametric("searchLink", searchLinkScript);
		}
		// 数据抓取相关
		ArrayList<Integer> pNumList = new ArrayList<>();
		// 中间页面的方法读取
		RangeStat.ALLRANGE.rangeforEach(num -> {
			createNextList(num, pNumList);
		});
		pNumList.clear();
		for (int num = 0; num < maxArgNum; num++) {
			String pName = "p" + num;
			String pScript = props.getProperty('@' + pName);
			if (StringUtils.isNotBlank(pScript)) {
				createFunction(pName, pScript, pNumList);
				pNumList.add(num);
			}
		}
		// 映射判断页面是否为Paper的方法
		String script = props.getProperty("@matchOut");
		if (StringUtils.isNotBlank(script)) {
			createFunction("matchOut", script, pNumList);
		}
		String className = props.getProperty("@Class");
		try {
			clazz = (Class<T>) Class.forName(className);
			createFunctionNoParametric("Class", "'" + className + "'");
		} catch (ClassNotFoundException e1) {
			throw new IllegalArgumentException(e1);
		}
		checkFieldList();

		// 映射字段抽取方法
		fieldList.forEach(field -> {
			createFunction('$' + field.getName(), props.getProperty(field.getName()), pNumList);
		});
		try {
			if (null == scriptFile) {
				scriptFile = File.createTempFile("tpljs-", ".js.tmp", new File(FileUtils.getTempDirectoryPath() + "/"));
			}
			if (scriptFile.isFile() && StringUtils.isNotBlank(FileUtils.readFileToString(scriptFile))) {
				throw new IllegalArgumentException("The javascipt file already exists.[" + scriptFile + "]");
			}
			FileUtils.writeStringToFile(scriptFile, sb.toString(), Charset.forName("UTF-8"));
			sb = null;
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * 检查FieldList
	 */
	private void checkFieldList() {
		if (null == fieldList && null != clazz) {
			fieldList = Lists.newArrayList();
			Arrays.asList(clazz.getFields()).stream().filter(field -> field.getAnnotation(NoMatch.class) == null)
					.forEach(fieldList::add);
		}
	}

	private void createNextList(int num, List<Integer> pNumList) {
		String matchListName = "matchList" + num;
		String nextListName = "nextList" + num;
		String matchListScript = props.getProperty('@' + matchListName);
		String nextListScript = props.getProperty('@' + nextListName);
		if (StringUtils.isNotBlank(matchListScript) && StringUtils.isNotBlank(nextListScript)) {
			createFunction(matchListName, matchListScript, pNumList);
			createFunction(nextListName, nextListScript, pNumList);
		}

	}

	/**
	 * 加载js脚本。
	 */
	@SuppressWarnings("unchecked")
	private void loadJavaScript() {
		try {
			System.err.println(scriptFile.getCanonicalPath());
			invocable.invokeFunction("load", scriptFile.getCanonicalPath());
			IntStream.range(0, maxArgNum).forEach(i -> {
				if (null != scriptEngine.get("p" + i)) {
					pNums.add(i);
				}
			});
			if (clazz == null) {
				String className = (String) invocable.invokeFunction("Class");
				clazz = (Class<T>) Class.forName(className);
			}
			rangeStat.rangeforEach(i -> {
				if (null != scriptEngine.get("matchList" + i) && null != scriptEngine.get("nextList" + i)) {
					listNums.add(i);
				}
			});
		} catch (NoSuchMethodException | ScriptException | IOException | ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}

	}

	/**
	 * 构造无参数方法
	 * 
	 * @param name
	 * @param script
	 */
	private void createFunctionNoParametric(String name, String script) {

		sb.append('\n');
		sb.append("function ");
		sb.append(name);
		sb.append("()").append('{').append('\n');
		sb.append("    ");
		if (isAddReturn(script)) {
			sb.append("return ");
		}
		sb.append(script);
		if (!StringUtils.endsWith(script, ";")) {
			sb.append(';');
		}
		sb.append('\n');
		sb.append('}');
		sb.append('\n');
	}

	private void createFunction(String name, String script, List<Integer> pNumList) {
		sb.append("function ").append(name).append('(');
		sb.append("page");
		sb.append(',');
		sb.append("html");
		pNumList.forEach(pNum -> {
			sb.append(',').append('p').append(pNum);
		});
		sb.append(')');
		sb.append('{');
		sb.append('\n');
		sb.append("    ");
		if (StringUtils.isNotBlank(script)) {
			if (isAddReturn(script)) {
				sb.append("return ");
			}
			sb.append(script);
			if (!script.trim().endsWith(";")) {
				sb.append(';');
			}
		} else {
			sb.append("return null;");
		}
		sb.append('\n');
		sb.append('}');
		sb.append('\n');
		sb.append('\n');
		sb.append('\n');
	}

	/**
	 * 判断是否需要添加 return语句
	 * 
	 * @param script
	 * @return
	 */
	final static boolean isAddReturn(String script) {
		char[] chars = script.toCharArray();
		int length = chars.length;
		int status = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char c = chars[i];
			if (1 == status && '\'' == c) {
				status = 0;
				continue;
			}
			if (2 == status && '"' == c) {
				status = 0;
				continue;
			}
			if (0 == status) {
				if ('\'' == c) {
					status = 1;
					continue;
				}
				if ('"' == c) {
					status = 2;
					continue;
				}
				sb.append(c);
			}
		}
		return !returnPattern.matcher(sb.toString()).find();
	}

	private final static Pattern returnPattern = Pattern.compile("\\breturn\\b");

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	@Override
	public void process(Page page) {
		linkCounter.incrementAndGet();
		long start = System.currentTimeMillis();
		page.putField("_seed_", scriptEngine.get("seed"));
		// 判断是否是详情页面
		if (match("matchOut", page)) {
			processOut(page);
		} else {
			page.putField("_isListPage_", true);
		}
		// 判断是否有列表页面的处理
		listNums.forEach(num -> {
			String matchListName = "matchList" + num;
			String nextListName = "nextList" + num;
			if (match(matchListName, page)) {
				System.out.println(page.getHtml().toString().length());
				URLParser.addRequests(getList(nextListName, page), page);
				System.out.println("123123123123");
			}
		});
		long execTime = System.currentTimeMillis() - start;
		logger.info("process time:{} url:{}", execTime, page.getRequest().getUrl());
	}

	@SuppressWarnings("unchecked")
	private List<Object> getList(String name, Page page) {
		try {
			Object res = invocable.invokeFunction(name, page, page.getHtml());

			List<Object> resList = Collections.EMPTY_LIST;
			if (null != res) {
				if (res instanceof List) {
					resList = (List<Object>) res;
				} else if (res.getClass().isArray()) {
					List<Object> list = Lists.newArrayList();
					int length = Array.getLength(res);
					for (int i = 0; i < length; i++) {
						Object val = Array.get(res, i);
						if (null != val && StringUtils.isNotBlank(val.toString())) {
							list.add(val);
						}
					}
					resList = list;
				} else if (StringUtils.isNotBlank(res.toString())) {
					resList = Arrays.asList(res.toString());
				}
			}
			if (debug) {
				System.out.println(name + "::getList() size=" + resList.size() + " => "
						+ Arrays.toString(resList.stream().limit(10).toArray()));
			}

			return resList;
		} catch (NoSuchMethodException | ScriptException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private boolean match(String name, Page page) {
		try {
			Object obj = invocable.invokeFunction(name, page, page.getHtml());
			if (obj instanceof Boolean && ((Boolean) obj).booleanValue() == true)
				return true;
		} catch (NoSuchMethodException | ScriptException e) {
			throw new IllegalArgumentException(e);
		}
		return false;
	}

	protected void processOut(Page page) {
		outCounter.incrementAndGet();
		ArrayList<Object> argList = new ArrayList<>();
		argList.add(page);
		argList.add(page.getHtml());
		pNums.forEach(pNum -> {
			Page resPage = null;
			Object pUrl = null;
			try {
				pUrl = invocable.invokeFunction("p" + pNum, argList.toArray());
				if (null == pUrl) {
					argList.add(null);
				} else {
					String url = pUrl.toString();
					Request request = URLParser.toRequest(url, page);
					resPage = downloader.download(request, Site.me().toTask());
				}
			} catch (Exception e) {
				logger.info("p" + pNum + "请求失败.  message=" + e.getMessage());
			}
			if (null == resPage) {
				resPage = new Page();
				resPage.setRawText("<html></html>");
				if (null == pUrl) {
					resPage.setUrl(new PlainText(""));
				} else {
					resPage.setUrl(new PlainText(pUrl.toString()));
				}
			}
			argList.add(resPage);
		});
		Object[] args = argList.toArray();
		checkFieldList();
		fieldList.forEach(field -> {
			String name = field.getName();
			try {
				page.putField(name, invocable.invokeFunction('$' + name, args));
			} catch (Exception e) {
				Logger.getAnonymousLogger().info("$" + name + " ; " + e.getMessage());
			}
		});
		page.putField("_finish_", (Closeable) insCount::incrementAndGet);
	}

	@Override
	public Site getSite() {
		return site;
	}

	class MySite extends Site {
		private String uuid;

		public MySite(String uuid) {
			this.uuid = uuid;
		}

		@Override
		public Task toTask() {
			return new Task() {

				@Override
				public String getUUID() {
					return uuid;
				}

				@Override
				public Site getSite() {
					return MySite.this;
				}
			};
		}
	}

	public String getCountManager() {
		return "link:" + linkCounter + ",out:" + outCounter + ",ins:" + insCount;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		scriptEngine.getContext().getBindings(ScriptContext.ENGINE_SCOPE).forEach((k, v) -> {
			if (!StringUtils.startsWith(v.toString(), "fun")) {
				sb.append("var ");
				sb.append(k);
				sb.append(' ');
				sb.append('=');
				sb.append(' ');
			}
			sb.append(v);
			sb.append('\n');
		});
		return sb.toString();
	}

	@Override
	public void close() throws IOException {
		try {
			System.err.println(
					"SeedUrl:" + ((Seed<?>) scriptEngine.get("seed")).seedUrl + " [" + getCountManager() + "]");
		} catch (Exception e) {
			System.out.println("出现异常,只打印数字信息." + getCountManager());
		}
	}

	public ScriptEngine getScriptEngine() {
		return scriptEngine;
	}

	public Spider testSpider(Spider spider, String... urls) {
		if (RangeStat.FULL_CRAWL == rangeStat || RangeStat.INCREMENTAL_CRAWL == rangeStat) {
			spider.test(urls);
		} else if (RangeStat.SEARCH_CRAWL == rangeStat) {
			getSearchLink().forEach(spider::test);
		} else {
			throw new IllegalArgumentException("seed err.  seedUrl = " + getSeed().seedUrl);
		}
		return spider;
	}

	public Spider initSeedUrl(Spider spider) {
		if (RangeStat.FULL_CRAWL == rangeStat || RangeStat.INCREMENTAL_CRAWL == rangeStat) {
			spider.addUrl(getSeed().seedUrl);
		} else if (RangeStat.SEARCH_CRAWL == rangeStat) {
			getSearchLink().forEach((url) -> spider.addRequest(URLParser.toRequest(url)));
		} else {
			throw new IllegalArgumentException("seed err.  seedUrl = " + getSeed().seedUrl);
		}
		return spider;
	}

	public String getCommand(String name) {
		try {
			Object obj = scriptEngine.eval(name);
			if (null != obj)
				return obj.toString();
			return null;
		} catch (ScriptException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
