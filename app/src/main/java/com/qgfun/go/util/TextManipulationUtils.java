package com.qgfun.go.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : Cola
 * @date : 2019/6/27 11:14
 * description : 文本操作
 */
public class TextManipulationUtils {

    //取指定文本
        public static String[] takeSpecifiedText(String str, String left, String right) {
            if ("".equals(str) || "".equals(left) || "".equals(right)) {
                return new String[0];
            }
            return regularMatch(str, "(?<=\\Q" + left + "\\E).*?(?=\\Q" + right + "\\E)");
        }

        public static String takeSpecifiedText2(String str, String left, String right) {
            String[] temp = takeSpecifiedText(str, left, right);
            if (temp.length > 0) {
                return temp[0];
            }
            return "";
        }

    public static String[] regularMatch(String text, String statement) {
        Matcher mr = Pattern.compile(statement, 40).matcher(text);
        List<String> list = new ArrayList();
        while (mr.find()) {
            list.add(mr.group());
        }
        return list.toArray(new String[list.size()]);
    }

    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

}

