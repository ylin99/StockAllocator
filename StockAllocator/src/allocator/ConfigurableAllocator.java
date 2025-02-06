package allocator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class ConfigurableAllocator extends JFrame {
    private JTable orderTable, ruleTable, securityTable, accountTable;
    private OrderTableModel orderModel;
    private RuleTableModel ruleModel;
    private SecurityTableModel securityModel;
    private AccountTableModel accountModel;
    private JButton loadOrdersBtn, loadRulesBtn, loadSecurityBtn, loadAccountsBtn, showInfoBtn, exportBtn;

    public ConfigurableAllocator() {
        initUI();
        loadDefaultData();
    }

    private void initUI() {
        setTitle("Stock Allocation System");
        setLayout(new BorderLayout());
        setSize(1400, 800);

        // Initialize table models
        orderModel = new OrderTableModel();
        ruleModel = new RuleTableModel();
        securityModel = new SecurityTableModel();
        accountModel = new AccountTableModel();

        // Create tables
        orderTable = new JTable(orderModel);
        ruleTable = new JTable(ruleModel);
        securityTable = new JTable(securityModel);
        accountTable = new JTable(accountModel);

        // Initialize buttons
        loadOrdersBtn = new JButton("Load Orders");
        loadRulesBtn = new JButton("Load Rules");
        loadSecurityBtn = new JButton("Load Security");
        loadAccountsBtn = new JButton("Load Accounts");
        showInfoBtn = new JButton("Show Current Data");
        exportBtn = new JButton("Export Results");

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loadOrdersBtn);
        buttonPanel.add(loadRulesBtn);
        buttonPanel.add(loadSecurityBtn);
        buttonPanel.add(loadAccountsBtn);
        buttonPanel.add(showInfoBtn);
        buttonPanel.add(exportBtn);

        // Tabbed pane for different views
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Orders", new JScrollPane(orderTable));
        tabbedPane.addTab("Rules", new JScrollPane(ruleTable));

        // Add action listeners
        loadOrdersBtn.addActionListener(e -> loadCSV("orders"));
        loadRulesBtn.addActionListener(e -> loadCSV("rules"));
        loadSecurityBtn.addActionListener(e -> loadCSV("security"));
        loadAccountsBtn.addActionListener(e -> loadCSV("accounts"));
        showInfoBtn.addActionListener(this::showInfoDialog);
        exportBtn.addActionListener(this::exportResults);

        add(buttonPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void loadCSV(String type) {
        JFileChooser fc = new JFileChooser();
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                switch(type) {
                    case "orders":
                        List<Order> orders = CSVReader.readOrders(fc.getSelectedFile());
                        OrderProcessor.process(orders, ruleModel.getRules());
                        orderModel.setOrders(orders);
                        break;
                    case "rules":
                        ruleModel.setRules(CSVReader.readRules(fc.getSelectedFile()));
                        break;
                    case "security":
                        SecurityMaster.loadFromCSV(fc.getSelectedFile());
                        securityModel.setSecurities(SecurityMaster.getAllSecurities());
                        break;
                    case "accounts":
                        AccountManager.loadFromCSV(fc.getSelectedFile());
                        accountModel.setAccounts(AccountManager.getAllAccounts());
                        break;
                }
            } catch (Exception ex) {
                showError("Error loading file: " + ex.getMessage());
            }
        }
    }

    private void exportResults(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Allocation Results");
        fileChooser.setSelectedFile(new File("allocations.csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
                // Write header
                writer.write("Ticker,Side,InstrumentType,Region,Account");
                writer.newLine();

                // Write data
                for (int row = 0; row < orderModel.getRowCount(); row++) {
                    String[] rowData = {
                            orderModel.getValueAt(row, 0).toString(),
                            orderModel.getValueAt(row, 1).toString(),
                            orderModel.getValueAt(row, 2).toString(),
                            orderModel.getValueAt(row, 3).toString(),
                            orderModel.getValueAt(row, 4).toString()
                    };
                    writer.write(String.join(",", rowData));
                    writer.newLine();
                }

                JOptionPane.showMessageDialog(this,
                        "Successfully exported " + orderModel.getRowCount() + " records!",
                        "Export Complete",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error exporting data: " + ex.getMessage(),
                        "Export Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showInfoDialog(ActionEvent e) {
        JDialog infoDialog = new JDialog(this, "Current System Data", true);
        infoDialog.setLayout(new GridLayout(2, 1));

        JPanel securityPanel = new JPanel(new BorderLayout());
        securityPanel.add(new JLabel("Security Master:"), BorderLayout.NORTH);
        securityPanel.add(new JScrollPane(securityTable), BorderLayout.CENTER);

        JPanel accountPanel = new JPanel(new BorderLayout());
        accountPanel.add(new JLabel("Accounts:"), BorderLayout.NORTH);
        accountPanel.add(new JScrollPane(accountTable), BorderLayout.CENTER);

        infoDialog.add(securityPanel);
        infoDialog.add(accountPanel);
        infoDialog.pack();
        infoDialog.setVisible(true);
    }

    private void loadDefaultData() {
        boolean securityLoaded = loadSecurityCSV(new File("data/security_master.csv"));
        boolean accountsLoaded = loadAccountsCSV(new File("data/accounts.csv"));
        boolean rulesLoaded = loadRulesCSV(new File("data/rules.csv"));

        if (!securityLoaded || !accountsLoaded || !rulesLoaded) {
            showWarning("Some initial files failed to load. Please check:\n" +
                    "- data/security_master.csv\n" +
                    "- data/accounts.csv\n" +
                    "- data/rules.csv\n" +
                    "You can load them manually via the interface.");
        }
    }

    private boolean loadSecurityCSV(File file) {
        try {
            SecurityMaster.loadFromCSV(file);
            securityModel.setSecurities(SecurityMaster.getAllSecurities());
            return true;
        } catch (Exception e) {
            showWarning("Security master load failed: " + e.getMessage());
            return false;
        }
    }

    private boolean loadAccountsCSV(File file) {
        try {
            AccountManager.loadFromCSV(file);
            accountModel.setAccounts(AccountManager.getAllAccounts());
            return true;
        } catch (Exception e) {
            showWarning("Accounts load failed: " + e.getMessage());
            return false;
        }
    }

    private boolean loadRulesCSV(File file) {
        try {
            ruleModel.setRules(CSVReader.readRules(file));
            return true;
        } catch (Exception e) {
            showWarning("Rules load failed: " + e.getMessage());
            return false;
        }
    }


    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Initialization Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConfigurableAllocator allocator = new ConfigurableAllocator();
            allocator.setVisible(true);
            allocator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}