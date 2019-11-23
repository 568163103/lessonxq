package headfirst.nine.dinemenu;

import headfirst.nine.menuitem.MenuItem;
import headfirst.nine.service.Iterator;


/**
 * @author hc
 */
public class DinerMenu {
    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    MenuItem[] menuItems;

    public DinerMenu() {
        menuItems = new MenuItem[MAX_ITEMS];
        addItem("1 K&B s Pancake Breakfast", "Hello World", false, 2.99);
        addItem("2 Regular Pancake Breakfast", "Hello World", false, 2.99);
        addItem("3 Blueberry Pancake Breakfast", "Hello World", false, 3.49);
        addItem("4 Waffles", "Hello World", false, 3.05);
    }

    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        if (numberOfItems >= MAX_ITEMS) {
            System.out.println("rang items index");
        } else {
            menuItems[numberOfItems] = menuItem;
            numberOfItems = numberOfItems+1;
        }
    }


//    public MenuItem[] getMenuItems() {
//        return menuItems;
//    }

    public Iterator createIterator() {
        return new DinerMenuIterator(menuItems);
    }
}
