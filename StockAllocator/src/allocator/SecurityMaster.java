package allocator;

import java.io.File;
import java.util.*;

public class SecurityMaster {
    private static Map<String, String> securities = new HashMap<>();

    /*public static void loadDefaults() {
        securities.clear();
        securities.put("AAPL", "US");
        securities.put("IBM", "US");
        securities.put("AI FP", "Europe");
    }*/

    public static void loadFromCSV(File file) throws Exception {
        Map<String, String> newSecurities = new HashMap<>();
        List<String[]> records = CSVReader.readGenericCSV(file);
        for (String[] record : records) {
            if (record.length >= 2) {
                newSecurities.put(record[0].trim().toUpperCase(), record[1].trim());
            }
        }
        securities = newSecurities;
    }

    public static String getRegion(String ticker) {
        return securities.getOrDefault(ticker.toUpperCase(), "Unknown");
    }

    public static List<String[]> getAllSecurities() {
        List<String[]> result = new ArrayList<>();
        for (Map.Entry<String, String> entry : securities.entrySet()) {
            result.add(new String[]{entry.getKey(), entry.getValue()});
        }
        return result;
    }

}