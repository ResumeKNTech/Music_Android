package com.android_app.music.utils;

public class StringUtil {

    public static boolean isEmpty(String input) {
        return input == null || input.isEmpty() || ("").equals(input.trim());
    }
}

