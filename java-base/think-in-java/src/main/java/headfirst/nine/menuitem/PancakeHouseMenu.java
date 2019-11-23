package headfirst.nine.menuitem;

import headfirst.nine.dinemenu.DinerMenuIterator;
import headfirst.nine.service.Iterator;

import java.util.ArrayList;

/**
 * @author xq
 */
public class PancakeHouseMenu {
    ArrayList menuItems;

    public PancakeHouseMenu() {
        this.menuItems = new ArrayList();
        addItem("K&B s Pancake Breakfast","Hello World",false,2.99);
        addItem("Regular Pancake Breakfast","Hello World",false,2.99);
        addItem("Blueberry Pancake Breakfast","Hello World",false,3.49);
        addItem("Waffles","Hello World",false,3.59);
    }

    public void addItem(String name, String description, boolean vegetarian, double price) {
        MenuItem menuItem = new MenuItem(name, description, vegetarian, price);
        menuItems.add(menuItem);
    }

//    public ArrayList getMenuItems() {
//        return menuItems;
//    }

    public Iterator createIterator() {
        return new PancakeHouseIterator(menuItems);
    }
}
