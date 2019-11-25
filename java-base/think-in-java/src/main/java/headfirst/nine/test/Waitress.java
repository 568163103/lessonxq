package headfirst.nine.test;


import headfirst.nine.menuitem.MenuItem;

import headfirst.nine.service.Menu;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author xq
 */
public class Waitress {
    ArrayList menuList;

    public Waitress(ArrayList menuList) {
        this.menuList = menuList;
    }

    public void printMenu() {
        Iterator menuIterator = menuList.iterator();
        while (menuIterator.hasNext()) {
            Menu menu = (Menu) menuIterator.next();
            printMenu(menu.createIterator());
        }
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
