package headfirst.nine.test;

import headfirst.nine.CompositeIterator;
import headfirst.nine.service.MenuComponent;

public class testCompositeIterator {

    public testCompositeIterator(MenuComponent component) {
        CompositeIterator compositeIterator = new CompositeIterator(component.createIterator());
        while (compositeIterator.hasNext()) {
            compositeIterator.next();
        }
    }
}
