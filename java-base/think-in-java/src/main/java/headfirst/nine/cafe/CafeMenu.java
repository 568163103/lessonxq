package headfirst.nine.cafe;

import headfirst.nine.menuitem.MenuItem;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * @author xq
 */
public class CafeMenu   {
    Hashtable menuItems = new Hashtable();

    public CafeMenu() {
        addItem("1 s K&B s Pancake Breakfast", "Hello World", false, 2.99);
        addItem("2 s Regular Pancake Breakfast", "Hello World", false, 2.99);
        addItem("3 s Blueberry Pancake Breakfast", "Hello World", false, 3.49);
        addItem("4 s Waffles", "Hello World", false, 3.05);
    }



    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.put(menuItem.getName(),menuItem);
    }


    public Iterator createIterator() {
        return menuItems.values().iterator();
    }
}
