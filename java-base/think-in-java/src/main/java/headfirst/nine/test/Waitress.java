package headfirst.nine.test;


import headfirst.nine.service.MenuComponent;

import java.util.Iterator;

/**
 * @author xq
 */
public class Waitress {
    MenuComponent menuComponent;

    public Waitress(MenuComponent menuComponent) {
        this.menuComponent = menuComponent;
    }

    public void printMenu() {
        menuComponent.print();
    }


    public void printVegetarianMenu() {
        Iterator iterator = menuComponent.createIterator();
        System.out.println("hhhhhhhhhh");
        while (iterator.hasNext()) {
            MenuComponent menuComponent = (MenuComponent) iterator.next();
            try {
                if (menuComponent.isVegetarian()) {
                    menuComponent.print();
                }
            } catch (UnsupportedOperationException u) {
                
            }

        }
    }
}
