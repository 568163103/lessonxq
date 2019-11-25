package headfirst.nine.menuitem;



import java.util.ArrayList;
import java.util.Iterator;

public class PancakeHouseIterator implements Iterator {
    ArrayList menuItems;
    int position = 0;

    public PancakeHouseIterator(ArrayList menuItems) {
        this.menuItems = menuItems;
    }

    public boolean hasNext() {
        if (position >= menuItems.size() || menuItems == null) {
            return false;
        } else {
            return true;
        }
    }

    public Object next() {
        MenuItem menuItem = (MenuItem) menuItems.get(position);
        position = position + 1;
        return menuItem;
    }

    public void remove() {

    }
}
