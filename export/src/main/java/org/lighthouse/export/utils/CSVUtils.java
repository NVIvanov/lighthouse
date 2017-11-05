package org.lighthouse.export.utils;

import java.io.IOException;
import java.util.List;

/**
 * @author nivanov
 * on 02.11.2017.
 */
public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ';';

    public static void writeLine(StringBuilder sb, List<String> values) throws IOException {
        writeLine(sb, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(StringBuilder sb, List<String> values, char separators) throws IOException {
        writeLine(sb, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(StringBuilder sb, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");

    }

}
