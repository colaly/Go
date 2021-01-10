package com.qgfun.go.util;

import android.text.TextUtils;

import org.jetbrains.annotations.TestOnly;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Cola
 * date 2021/1/10 17:34
 */
public class UrlRegUtils {
    public static boolean allMatcher(String url,String... args){
        if (TextUtils.isEmpty(url)){
            return false;
        }
        StringBuilder sb=new StringBuilder();
        String split=".*";
        sb.append(split);
        for (String arg : args) {
            sb.append(arg).append(split);
        }
        String pattern = sb.toString();

        Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = r.matcher(url);
        return m.matches();
    }

    public static boolean orMatcher(String url,String... args){
        if (TextUtils.isEmpty(url)){
            return false;
        }
        StringBuilder sb=new StringBuilder();
        String split=".*";
        sb.append(split);
        for (String arg : args) {
            sb.append(arg).append(split).append("|");
        }
        String pattern = sb.toString().substring(0,sb.length()-1);

        Pattern r = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = r.matcher(url);
        return m.matches();
    }

}
