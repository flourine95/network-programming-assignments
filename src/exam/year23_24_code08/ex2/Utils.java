package exam.year23_24_code08.ex2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Utils {

    private static final String DELIMITER = "\t";

    public static List<Integer> parseParamInt(String param) {
        try {
            return Arrays.stream(param.split(DELIMITER))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            System.err.println("Invalid parameter format: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public static List<String> parseParam(String param) {
        return Arrays.stream(param.split(DELIMITER))
                .map(String::trim)
                .toList();
    }

    public static boolean validateParams(String param, int expectedSize) {
        List<String> params = parseParam(param);
        if (params.size() != expectedSize) {
            System.out.println("Invalid number of parameters. Expected " + expectedSize);
            return false;
        }
        return true;
    }
}
