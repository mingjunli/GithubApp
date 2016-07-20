package com.anly.githubapp.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mingjun on 16/7/20.
 */
public class StringUtil {

    public static String replaceAllBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
    
    public static String trimNewLine(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
