AKTUEL's digital ordering system.

AKTUEL's digital ordering system, *ADOS* for short, is a digital ordering system designed by AAU software group 8.


### How to use ADOS
To use ADOS make sure to setup your apache tomcat servlet.
ADOS uses 4 separate HTML frontends to work; Admin, guest, kitchen & payment.

#### Setting up the apache tomcat servlet


### admin frontend
ADOS' admin frontend allows the admin user of ADOS to add, remove or change menu items for the selected menu.

#### How to access the admin frontend.
to access the admin frontend, enter the url:(IP):8080/P3_war/AdminFrontend.html

#### How to add new menu items.
To add a menu item to a menu the user needs to do the following:
##### Step 1:
Enter the Display name, the internal name and the price.

##### Step 2:
Select if there are suppose to be options and(or) additions for the item.

##### Step 2.5:
Enter the needed information for the options and(or) additions

##### Step 3:
Select if there are any days where a discount should be applied.

##### Step 3.5:
Enter the discount amount and how many items needs to be selected to apply the discount.

##### Step 4:
Insert the item into the list.

##### Step 5:
Repeat step 1-4 for more items.

---

#### How to send a new menu to the server.
To send a new menu to the server the users needs to do the following:
##### Step 1:
Create a list of items using the method described before.

##### Step 2:
Enter a menu ID for the new menu.

##### Step 3:
Enter a starting time and an ending time for the availability for the menu

##### Step 4:
Select the day(s) that the menu is a available.

##### Step 5:
Enter the name of the restaurant that the menu is attached to.

##### Step 6:
Send the new menu to the server.

---

#### Change the info of an item for a menu.
To change the info for a menu the user needs to do the following:

##### Step 1:
Import the menu id.

##### Step 2:
Select the item that you wish to edit by entering the index of the item, the list starts at 0.

##### Step 2.2:
Select the option that you wish to edit by entering the index of the option, the option list starts at 0.

##### Step 2.3: 
Select the addition that you wish to edit by entering the index of the addition, the addition list starts at 0.

##### Step 3:
Edit the wished information and submit.

##### Step 4:
Follow step 5 and 6 from [[#How to add new menu items.]]

#### Deleting an item from the menu
To delete an item from the menu the user needs to do the following:

##### Step 1:
Import the menu id in the change info section.

##### Step 2:
Select the item, option or addition by entering the index, the list starts at index 0.

##### Step 3:
Delete the wished item, option or addition by clicking the respective button.

#### How to generate new QR codes.
To generate a new QR code the user needs to do the following:

##### Step 1:
Select the restaurant that the QR code is going to be used in.

##### Step 2:
Enter the table ID for the table that the QR code is going to be used by.

##### Step 3:
Click submit.