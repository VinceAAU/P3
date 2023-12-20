AKTUEL's digital ordering system.
=================================

AKTUEL's digital ordering system, *ADOS* for short, is a digital ordering system designed by AAU software group 8.

## Compilation

Before compilation, download and install [Apache Maven](https://maven.apache.org/) and a Java Development Kit.
Then, at the root folder of the project, type:

    mvn package

This should generate a `.war` file, which is the web archive.

## Set up

We recommend the use of [Apache Tomcat](https://tomcat.apache.org/) as servlet software. 
Put the generated `.war` file into the `webapps/` directory of the Tomcat installation, and start tomcat.
With the default configuration, it should be accessible at `localhost:8080/<web archive name>/`

## Usage
ADOS uses 4 separate HTML frontends to work: Admin, customer, kitchen & payment.

### Admin frontend
ADOS' admin frontend allows the admin user of ADOS to add, remove or change menu items for the selected menu.
To access the admin frontend, go to the `/AdminFrontend.html` url.

#### How to add new menu items.
To add a menu item to a menu the user needs to do the following:

1. Enter the Display name, the internal name and the price.
2. Select if there are supposed to be options and(or) additions for the item.
   1. Enter the needed information for the options and(or) additions
3. Select if there are any days when a discount should be applied. 
   1. Enter the discount amount and how many items needs to be selected to apply the discount.
4. Insert the item into the list.
5. Repeat step 1-4 for more items.

---

#### How to send a new menu to the server.
To send a new menu to the server the users needs to do the following:

1. Create a list of items using the method described before.
2. Enter a menu ID for the new menu.
3. Enter a starting time and an ending time for the availability for the menu
4. Select the day(s) that the menu is available.
5. Enter the name of the restaurant that the menu is attached to.
6. Send the new menu to the server.

---

#### Change the info of an item for a menu.
To change the info for a menu the user needs to do the following:

1. Import the menu id.
2. Select the item that you wish to edit by entering the index of the item, the list starts at 0.
   1. Select the option that you wish to edit by entering the index of the option, the option list starts at 0.
   2. Select the addition that you wish to edit by entering the index of the addition, the addition list starts at 0.
3. Edit the wished information and submit.
4. Follow step 5 and 6 from Section [How to add new menu items.](####how-to-add-new-menu-items)

To delete an item from the menu the user needs to do the following:

1. Import the menu id in the change info section.
2. Select the item, option or addition by entering the index, the list starts at index 0.
3. Delete the wished item, option or addition by clicking the respective button.

---

#### How to generate new QR codes.
To generate a new QR code the user needs to do the following:
1. Select the restaurant that the QR code is going to be used in.
2. Enter the table ID for the table that the QR code is going to be used by.
3. Click submit.