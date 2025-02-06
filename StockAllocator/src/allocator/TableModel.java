// TableModels.java
package allocator;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

class OrderTableModel extends AbstractTableModel {
    private final String[] COLUMNS = {"Ticker", "Side", "Instrument Type", "Region", "Account"};
    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return orders.size(); }
    @Override public int getColumnCount() { return COLUMNS.length; }

    @Override
    public Object getValueAt(int row, int column) {
        Order order = orders.get(row);
        switch (column) {
            case 0: return order.getTicker();
            case 1: return order.getSide();
            case 2: return order.getInstrumentType();
            case 3: return order.getRegion();
            case 4: return order.getAccount();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
}

class RuleTableModel extends AbstractTableModel {
    private final String[] COLUMNS = {"Priority", "Ticker", "Side", "Instrument", "Region", "Account"};
    private List<Rule> rules = new ArrayList<>();

    public void setRules(List<Rule> rules) {
        this.rules = rules;
        fireTableDataChanged();
    }

    public List<Rule> getRules() { return rules; }

    @Override public int getRowCount() { return rules.size(); }
    @Override public int getColumnCount() { return COLUMNS.length; }

    @Override
    public Object getValueAt(int row, int column) {
        Rule rule = rules.get(row);
        switch (column) {
            case 0: return rule.getPriority();
            case 1: return rule.getTicker();
            case 2: return rule.getSide();
            case 3: return rule.getInstrumentType();
            case 4: return rule.getRegion();
            case 5: return rule.getAccount();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
}

class SecurityTableModel extends AbstractTableModel {
    private final String[] COLUMNS = {"Ticker", "Region"};
    private List<String[]> securities = new ArrayList<>();

    public void setSecurities(List<String[]> securities) {
        this.securities = securities;
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return securities.size(); }
    @Override public int getColumnCount() { return COLUMNS.length; }

    @Override
    public Object getValueAt(int row, int column) {
        return securities.get(row)[column];
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
}

class AccountTableModel extends AbstractTableModel {
    private final String[] COLUMNS = {"Account"};
    private List<String> accounts = new ArrayList<>();

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return accounts.size(); }
    @Override public int getColumnCount() { return COLUMNS.length; }

    @Override
    public Object getValueAt(int row, int column) {
        return accounts.get(row);
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
}

