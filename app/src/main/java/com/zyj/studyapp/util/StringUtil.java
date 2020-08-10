package com.zyj.studyapp.util;

public class StringUtil {
    public static boolean isEmpty(String string){
        if (string == null) return true;
        if (string.length() == 0) return true;
        if (string.equals("null")) return true;
        return false;
    }
}
