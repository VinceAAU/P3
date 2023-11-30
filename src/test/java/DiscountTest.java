import dk.aau.student.AktuelMenu.Discount;
import dk.aau.student.AktuelMenu.DaySelector;
import dk.aau.student.AktuelMenu.MenuItem;
import dk.aau.student.AktuelMenu.OrderItem;
import org.junit.jupiter.api.Assertions;        //imports jupiter assertions
import org.junit.jupiter.api.Test;              //import jupiter tests
import java.util.ArrayList;

public class DiscountTest {

    private int finalCalcedPrice;

    @Test
    public void doesItemHaveDiscount(){
        ArrayList<MenuItem> menuList = new ArrayList<>();       //creates a menuitem list
        menuList.add(new MenuItem("borger","Okse Burger",10000));   //creates menu item 1
        menuList.add(new MenuItem("caesar","Caesar Salad",5000));   //creates menu item 2

        ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();    // creates order list
        orderList.add(new OrderItem(menuList.get(0),null,null,"yes")); //adds item 1 to the order list
        orderList.add(new OrderItem(menuList.get(1),null,null,"yes"));


        DaySelector Daysselection = DaySelector.always();
        Discount DiscountTester = new Discount(orderList.get(0).calcPrice(),1,Daysselection);
        Assertions.assertTrue(DiscountTester.applies());

    }

    @Test
    public void DoesItemNotHaveDiscount(){
        ArrayList<MenuItem> menuList = new ArrayList<>();       //creates a menuitem list
        menuList.add(new MenuItem("borger","Okse Burger",10000));   //creates menu item 1
        menuList.add(new MenuItem("caesar","Caesar Salad",5000));   //creates menu item 2

        ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();    // creates order list
        orderList.add(new OrderItem(menuList.get(0),null,null,"yes")); //adds item 1 to the order list
        orderList.add(new OrderItem(menuList.get(1),null,null,"yes"));


        DaySelector Daysselection = DaySelector.never();
        Discount DiscountTester = new Discount(0,1,Daysselection);
        Assertions.assertFalse(DiscountTester.applies());
    }
    @Test
    public void DoesItemGetDiscount(){

        ArrayList<MenuItem> menuList = new ArrayList<>();       //creates a menuitem list
        menuList.add(new MenuItem("borger","Okse Burger",10000));   //creates menu item 1
        menuList.add(new MenuItem("caesar","Caesar Salad",5000));   //creates menu item 2

        ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();    // creates order list
        orderList.add(new OrderItem(menuList.get(0),null,null,"yes")); //adds item 1 to the order list
        orderList.add(new OrderItem(menuList.get(1),null,null,"yes"));

        Discount DiscountTester = new Discount(-5000,1,DaySelector.always()); //creates a new discount for a single item with a 50kr discount
        menuList.get(0).setDiscount(DiscountTester); //Sets the discount in the orderitem to have the discount from discountTester
        this.finalCalcedPrice = orderList.get(0).calcPrice();   //gets the new price
        Assertions.assertEquals(5000,finalCalcedPrice); //checks if the final price is equal to the expected value.

    }
}
