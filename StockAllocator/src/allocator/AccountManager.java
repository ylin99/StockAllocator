package allocator;

import java.io.File;
import java.util.*;

public class AccountManager {
    private static Set<String> accounts = new HashSet<>();

/*    public static void loadDefaults() {
        accounts.clear();
        accounts.add("ABC");
        accounts.add("XYZ");
        accounts.add("ZZZ");
    }*/

    public static void loadFromCSV(File file) throws Exception {
        Set<String> newAccounts = new HashSet<>();
        List<String[]> records = CSVReader.readGenericCSV(file);
        for (String[] record : records) {
            if (record.length >= 1) {
                newAccounts.add(record[0].trim().toUpperCase());
            }
        }
        accounts = newAccounts;
    }

    public static List<String> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    public static boolean isValidAccount(String account) {
        return accounts.contains(account.toUpperCase());
    }
}