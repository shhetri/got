package com.got.helpers;

public class StringHelper {
    public static String stripTrailingSlash(String string) {
        if (!string.endsWith("/")) return string;

        return stripTrailingSlash(string.substring(0, string.length() - (string.endsWith("/") ? 1 : 0)));
    }

    public static String camelCase(String string) {
        StringBuilder sb = new StringBuilder();
        boolean capitalizeNext = false;
        for (char c:string.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    sb.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    public static String generateSetter(String string) {
        string = camelCase(string);
        string = "set" + Character.toUpperCase(string.charAt(0)) + string.substring(1, string.length());

        return string;
    }
}
