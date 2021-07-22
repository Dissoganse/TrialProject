# TrialProject

Trial project is an Eclipse project with GUI on JavaFX and HSQLDB database.
Use with JDK 1.8

Capabilities:
1. add, edit and delete clients data, loans data, loan offers data in corresponding database tables.
2. Search entities in tables by arbitrary number of column names

Loan offers data table is implemented with 2 foreign keys: Client Id and Credit Id and cascade deleting and updating.

4 domain zones of client's emails domain names are supported: ru, com, org, ua

In Credit offers tab only payment date that is later than the system date is allowed for adding offers


For deleting item:
1. Enter search keys in top fields
2. Press button "Search"
3. A set of strings with buttons "Delete" and "Offer credit" will appear
4. Press the button "Delete" near appropriate entity
  
For entering client or credit in Credit Offers tab:
1. Enter search keys in top fields on Clients tab or on Credits tab
2. Press button "Search"
3. A set of strings with buttons "Delete" and "Offer credit" will appear
4. Press the button "Offer credit" near appropriate entity
5. Switch to Credit offers tab. Data will be entered
  
For changing client or credit in a particular credit offer:
1. Enter search keys in top fields on Credit offers tab
2. Press button "Search"
3. A set of strings with buttons "Delete", "Change client" and "Change credit" will appear
4. Press the button "Change client" or button "Change credit" near appropriate entity
5. Tab will be automatically switched to appropriate tab
6. Enter search keys in top fields on opened tab
7. Press button "Search"
8. A set of strings with buttons "Delete" and "Offer credit" will appear
9. Press the button "Offer credit" near appropriate entity
10. Tab will be automatically switched to Credit offers tab
11. Press "Save changes" button below the results

