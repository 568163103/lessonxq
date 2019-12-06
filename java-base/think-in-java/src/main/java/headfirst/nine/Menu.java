package headfirst.nine;

import headfirst.nine.service.MenuComponent;

import java.util.ArrayList;
import java.util.Iterator;

public class Menu extends MenuComponent {
    ArrayList<MenuComponent> menuComponents = new ArrayList();
    String name;
    String description;

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Override
    public void  add(MenuComponent menuComponent){
        menuComponents.add(menuComponent);
    }

    @Override
    public void remove(MenuComponent menuComponent) {
        menuComponents.remove(menuComponent);
    }

    @Override
    public MenuComponent getChild(int i) {
        return  menuComponents.get(i);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void print() {
        System.out.println("\n"+getName());
        System.out.println(","+getDescription());
        System.out.println("------------------------------");

        Iterator iterator = menuComponents.iterator();
        while (iterator.hasNext()){
         MenuComponent menuComponent =(MenuComponent) iterator.next();
         menuComponent.print();
        }
    }

    @Override
    public Iterator createIterator() {
        return new CompositeIterator(menuComponents.iterator());
    }
}
