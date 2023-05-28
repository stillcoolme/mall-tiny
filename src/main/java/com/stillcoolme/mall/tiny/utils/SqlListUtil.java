package com.stillcoolme.mall.tiny.utils;

import com.stillcoolme.mall.tiny.vo.flink.SqlInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>转换sql文本</p>
 *
 * @author stillcoolme
 * @version V1.0.0
 * @date 2022/11/29 20:09
 */
public class SqlListUtil {
    private static final Pattern PATTERN_STATEMENT = Pattern.compile("[^\\\\];");
    private static final Pattern PATTERN_STRING = Pattern.compile("(\"|')([^\"^']*)(\"|')");
    private static final Pattern PATTERN_SINGLE_LINE = Pattern.compile("--.*");
    private static final Pattern PATTERN_MULTI_LINE = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL);


    public static List<SqlInfo> getSqlList(String context) {
        Map<Integer, Integer> enterMap = new TreeMap<Integer, Integer>();
        int enterCount = 1;
        for (int i = 0; i < context.length(); i++) {
            if (context.charAt(i) == '\n') {
                enterMap.put(i, enterCount++);
            }
        }
        enterMap.put(context.length(), enterCount++);
        List<SqlInfo> list = new ArrayList<>();
        Matcher match = PATTERN_STATEMENT.matcher(context);
        int index = 0;
        while (match.find()) {
            if (isInComment(context, match.start() + 1)
                    || isMatch(context.substring(index, match.start() + 1), '\'')
                    || isMatch(context.substring(index, match.start() + 1), '\"')) {
                continue;
            }
            String str = context.substring(index, match.start() + 1)
                    .replaceAll("\\\\;", ";");
            str = str.replaceAll("^;", "");

            if (!"".equals(str) & !isCommentClause(str)) {
                int maxEnters = 0;
                int lastEnter = 0;
                int firstLineIndex = 0;
                int loc = index - 1;
                for (Integer i : enterMap.keySet()) {
                    if (loc > i) {
                        maxEnters = enterMap.get(i);
                        lastEnter = i;
                    }
                    if (loc <= i) {
                        if (loc == i) {
                            firstLineIndex = 0;
                        } else {
                            firstLineIndex = loc - lastEnter;
                        }
                        break;
                    }
                }

                SqlInfo sqlInfo = new SqlInfo();
                sqlInfo.setSqlContent(str);
                sqlInfo.setLine(maxEnters + 1);
                sqlInfo.setFirstLineIndex(firstLineIndex);
                list.add(sqlInfo);
            }
            index = match.start() + 2;
            System.out.println("index : " + index);
        }
        if (context. substring(index) != null
                && context.substring(index).trim().length() != 0) {
            String str = context.substring(index).replaceAll("\\\\;", ";");
            str = str.replaceAll("^;", "");
            if (!"".equals(str) && !isCommentClause(str)) {
                int loc = index - 1;
                int maxEnters = 0;
                int lastEnter = 0;
                int firstLineIndex = 0;
                for (Integer i: enterMap.keySet()) {
                    if (index > i) {
                        maxEnters = enterMap.get(i);
                        lastEnter = i;
                    }
                    if (index <= i) {
                        if (index == i) {
                            firstLineIndex = 0;
                        } else {
                            firstLineIndex = index - lastEnter;
                            break;
                        }
                    }
                }

                SqlInfo sqlInfo = new SqlInfo();
                sqlInfo.setSqlContent(str);
                sqlInfo.setLine(maxEnters + 1);
                sqlInfo.setFirstLineIndex(firstLineIndex);
                list.add(sqlInfo);
            }
        }
        return list;
    }

    private static boolean isCommentClause(String str) {
        String trimStr = str.trim();
        if (trimStr.startsWith("/*") && trimStr.endsWith("*/")) {
            return true;
        }
        boolean res = true;
        String[] lines = StringUtils.split(str, "\n");
        for (String line : lines) {
            String val = line.trim();
            if (StringUtils.isEmpty(val) || val.startsWith("--")) {
                res = true;
            } else {
                return false;
            }

        }
        return res;
    }


    private static boolean isInComment(String context, int index) {
        Matcher singLeMatch = PATTERN_SINGLE_LINE.matcher(context);
        while (singLeMatch.find()) {
            int start = singLeMatch.start();
            int end = singLeMatch.end() - 1;
            if (index > start && index <= end) {
                return true;
            }
        }
        Matcher multiMatch = PATTERN_MULTI_LINE.matcher(context);
        while (multiMatch.find()) {
            int start = multiMatch.start();
            int end = multiMatch.end() - 1;
            if (index > start && index < end) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMatch(String source, char pattern) {
        int count = 0;
        for (int i = 0; i < source.length(); i++) {
            if (source . charAt(i) == pattern) {
                count++ ;
            }
            if (source . charAt(i) == '\\' & i < source.length() - 1
                    && source. charAt(i + 1) == pattern) {
                i++;
            }
        }
        return count % 2 == 0;


    }
}