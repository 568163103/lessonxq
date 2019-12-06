package headfirst.nine.menuitem;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xq
 */
public class PancakeHouseMenu   {
    ArrayList menuItems;

    public PancakeHouseMenu() {
        this.menuItems = new ArrayList();
        addItem("K&B s Pancake Breakfast", "Hello World", false, 2.99);
        addItem("Regular Pancake Breakfast", "Hello World", false, 2.99);
        addItem("Blueberry Pancake Breakfast", "Hello World", false, 3.49);
        addItem("Waffles", "Hello World", false, 3.59);
    }

    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.add(menuItem);
    }


    public Iterator createIterator() {
        return menuItems.iterator();
    }
}
