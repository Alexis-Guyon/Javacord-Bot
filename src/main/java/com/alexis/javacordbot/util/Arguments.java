package com.alexis.javacordbot.util;

import java.util.ArrayList;

public class Arguments extends ArrayList<String> {

    public Arguments(String in) {
        String out;

        if (in.endsWith(" ")) {
            out = in;
        } else {
            out = in + " ";
        }

        int stringIndex = 0;
        int stringEndIndex = 0;
        boolean isGrouped = false;

        for (int i = 0; i < out.length(); i++) {
            char c = out.charAt(i);

            if (c == ' ' && !isGrouped) {
                if (stringEndIndex == 0) {
                    add(out.substring(stringIndex, i));
                } else {
                    add(out.substring(stringIndex, stringEndIndex));
                    stringEndIndex = 0;
                }
                stringIndex = i + 1;
            } else if (c == '"') {
                if (isGrouped) {
                    stringEndIndex = i;
                    isGrouped = false;
                } else {
                    isGrouped = true;
                    stringIndex = i + 1;
                }
            }
        }
    }

    public String getTrailing(int index) {
        StringBuilder sb = new StringBuilder();

        for (int i = index; i < size(); i++) {
            if (i != index) {
                sb.append(' ');
            }

            sb.append(get(i));
        }

        return sb.toString();
    }

}
