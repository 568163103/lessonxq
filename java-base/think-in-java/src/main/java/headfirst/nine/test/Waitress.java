package headfirst.nine.test;

import headfirst.nine.dinemenu.DinerMenu;
import headfirst.nine.dinemenu.DinerMenuIterator;
import headfirst.nine.menuitem.MenuItem;
import headfirst.nine.menuitem.PancakeHouseIterator;
import headfirst.nine.menuitem.PancakeHouseMenu;
import headfirst.nine.service.Iterator;

/**
 * @author xq
 */
public class Waitress {
    PancakeHouseMenu pancakeHouseMenu;
    DinerMenu dinerMenu;

    public Waitress(PancakeHouseMenu pancakeHouseMenu, DinerMenu dinerMenu) {
        this.pancakeHouseMenu = pancakeHouseMenu;
        this.dinerMenu = dinerMenu;
    }

    public void printMenu() {
        Iterator pancakeHouseMenuIterator = pancakeHouseMenu.createIterator();
        printMenu(pancakeHouseMenuIterator);
        Iterator dinerMenuIterator = dinerMenu.createIterator();
        printMenu(dinerMenuIterator);
    }

    public void printMenu(Iterator iterator) {
        while (iterator.hasNext()) {
            MenuItem menuItem = (MenuItem) iterator.next();
            System.out.println(menuItem.getName());
            System.out.println(menuItem.getDescription());
            System.out.println(menuItem.getPrice());
        }
    }
}
