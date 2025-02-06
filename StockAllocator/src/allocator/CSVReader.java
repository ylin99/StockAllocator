package allocator;

import java.io.*;
import java.util.*;

public class CSVReader {
    /*public static List<Order> readOrders(File file) throws IOException {
        List<Order> orders = new ArrayList<>();
        for (String[] record : readGenericCSV(file)) {
            if (record.length >= 3) {
                orders.add(new Order(
                        record[0].trim(),
                        record[1].trim(),
                        record[2].trim()
                ));
            }
        }
        return orders;
    }*/
    public static List<Order> readOrders(File file) throws IOException {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue; // Skip header row
                }
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    orders.add(new Order(
                            parts[0].trim(),
                            parts[1].trim(),
                            parts[2].trim()
                    ));
                }
            }
        }
        return orders;
    }

    public static List<Rule> readRules(File file) throws IOException {
        List<Rule> rules = new ArrayList<>();
        List<String[]> records = readGenericCSV(file);
        if (records.isEmpty()) return rules;

        boolean hasHeader = records.get(0)[0].equalsIgnoreCase("priority");
        int startIndex = hasHeader ? 1 : 0;

        for (int i = startIndex; i < records.size(); i++) {
            String[] parts = records.get(i);
            if (parts.length >= 6) {
                rules.add(new Rule(
                        Integer.parseInt(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim()
                ));
            }
        }
        return rules;
    }

    public static List<String[]> readGenericCSV(File file) throws IOException {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line.split(","));
            }
        }
        return records;
    }
}