package org.yansou.ci.storage.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.yansou.ci.core.db.model.project.SnapshotInfo;
import org.yansou.ci.data.mining.utils.Readability;

public class SnapshonInfoUtils {
    // 过滤词列表
    static String[] filterKeyword = {"_中国电力招标网", "【编辑：信息中心】"};
    // 高亮次词表，满足正则的会被高亮
    static String[] highlightRegexs = {"(?is)(mw|kw|gw|晶|公告|招标|中标|公示|代理|发布日期|电话|传真|联系人|地址|地点)"};

    static public void filterSnapshot(SnapshotInfo snapshot) {
        String ctx = snapshot.getContext();
        if (StringUtils.isBlank(ctx)) {
            return;
        }
        ctx = Readability.createAndInit(ctx).outerHtml();
        // 删除所有A标签
        ctx = ctx.replaceAll("(?is)</?\\s*a\\s*[^>]*>", "");

        // 删除过滤词
        for (String keyword : filterKeyword) {
            ctx = StringUtils.replace(ctx, keyword, "");
        }
        // 设置高亮
        for (String regex : highlightRegexs) {
            StringBuilder sb = new StringBuilder();
            int idx = 0;
            Pattern pattern = Pattern.compile(regex);
            Matcher match = pattern.matcher(ctx);
            while (match.find()) {
                String kw = "<span style=\"color:#f00\">" + match.group(0) + "</span>";
                sb.append(ctx.substring(idx, match.start()));
                sb.append(kw);
                idx = match.end();
            }
            sb.append(ctx.substring(idx, ctx.length() - 1));
            ctx = sb.toString();
        }
        snapshot.setContext(ctx);
    }

}
