package allocator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class OrderProcessor {
    public static void process(List<Order> orders, List<Rule> rules) {
        // Sort rules by priority (lowest first)
        rules.sort(Comparator.comparingInt(Rule::getPriority));

        List<String> validInstrumentType = new ArrayList<>(Arrays.asList("PHYSICAL","SWAP","BOND","CFD"));

        for (Order order : orders) {
            if ("Unknown".equalsIgnoreCase(order.getRegion())) {
                order.setAccount("ERROR: Unknown region");
                continue;
            }
            if (!validInstrumentType.contains(order.getInstrumentType().toUpperCase())){
                order.setAccount("ERROR: Invalid InstrumentType");
                continue;
            }

            for (Rule rule : rules) {
                if (rule.matches(order)) {
                    order.setAccount(rule.getAccount());
                    break; // Stop checking rules once a match is found
                }
            }
        }
    }
}