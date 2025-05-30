package cn.edu.nju.supercode.util;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OutputUtil {
    private static String process(String s) {
        if (s == null)
            return null;
        String[] lines = s.split("\\r?\\n");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String trimmed = lines[i].replaceAll("\\s+$", "");
            result.append(trimmed);
            if (i < lines.length - 1)
                result.append('\n');
        }
        return result.toString();
    }

    public static boolean judge(String s1, String s2) {
        if (s1 == null || s2 == null)
            return false;
        return Objects.equals(process(s1), process(s2));
    }
}