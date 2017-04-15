package com.got.utilitites.helpers;

public class StringHelper {
    public static String stripTrailingSlash(String string) {
        if (!string.endsWith("/")) return string;

        return stripTrailingSlash(string.substring(0, string.length() - (string.endsWith("/") ? 1 : 0)));
    }
}
