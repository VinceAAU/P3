/*
import dk.aau.student.AktuelMenu.MenuItem;      //Imports MenuItem.java
import dk.aau.student.AktuelMenu.PaymentServlet;       //Imports Payment.java
import dk.aau.student.AktuelMenu.OrderItem;     //imports OrderItem.Java
import dk.aau.student.AktuelMenu.Order;         //import Order.java
import org.junit.jupiter.api.Assertions;        //imports jupiter assertions
import org.junit.jupiter.api.Test;              //import jupiter tests
import java.util.ArrayList;                     //imports java util arraylist



public class PaymentTest {

    @Test
    public void doesObjectHaveContent(){
        PaymentServlet PaymentTestObject = new PaymentServlet();
        ArrayList<MenuItem> menuList = new ArrayList<>();
        menuList.add(new MenuItem("borger","Okse Burger",10000));   //creates a menu item in a list

        Assertions.assertTrue(!menuList.isEmpty());     //Tests if the menulist has content
    }
    @Test
    public void doesObjectPrint(){
        PaymentServlet paymentTestObject = new PaymentServlet();  //creates a payment object

        ArrayList<MenuItem> menuList = new ArrayList<>();       //creates a menuitem list
        menuList.add(new MenuItem("borger","Okse Burger",10000));   //creates menu item 1
        menuList.add(new MenuItem("caesar","Caesar Salad",5000));   //creates menu item 2

        ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();    // creates order list
        orderList.add(new OrderItem(menuList.get(0),null,null,"yes")); //adds item 1 to the order list
        orderList.add(new OrderItem(menuList.get(1),null,null,"yes")); //adds item 2 to the order list

        Order orderExample = new Order(10,17,orderList); //creates order from order list

        paymentTestObject.payment(orderList); //runs payment method with order list

        System.out.print("\n PAYMENT PRINT TEST \n");
        System.out.println("Receipt ID: " + orderExample.getOrderId() + orderExample.getTableId());

        System.out.println("Items:");
        for (int i = 0; i < orderList.size(); i++) {
            System.out.println(menuList.get(i).getDisplayName() + " " + menuList.get(i).getPrice()/100 + "kr.");
        }
        System.out.println("Price: " + paymentTestObject.getPrice()/100 + "kr.");
    }
}
*/