Stock Order Allocation System
A Java GUI application for allocating stock orders to different accounts based on configurable rules. 
Processes CSV files and provides export functionality.

Features
-CSV File Processing
-Load Security Master Data (Ticker-Region mappings)
-Manage Account Lists
-Configure Allocation Rules
-Process Order Files

GUI Interface
-View loaded data in tabular format
-Real-time validation and error display

Core Functionality
-Rule-based order allocation with priority system
-Error handling for invalid/missing data
-Export results to CSV
-Display current system state (Security Master + Accounts)


Interface Guide
-Load Files 
    Use the buttons to load orders.csv, accounts.csv,rules.csv and orders.csv. File format please refer to the files in /data/
-Process Orders
    Orders are automatically processed after loading
Results appear in the Orders tab
-Export Results
    Click Export Results to save allocations
