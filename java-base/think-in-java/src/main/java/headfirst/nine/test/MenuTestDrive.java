package headfirst.nine.test;

import headfirst.nine.Menu;
import headfirst.nine.menuitem.MenuItem;
import headfirst.nine.service.MenuComponent;

import java.util.ArrayList;


/**
 * @author xq
 */
public class MenuTestDrive {
    public static void main(String[] args) {
        MenuComponent pancakeHouseMenu = new Menu("PANCAKE HOUSE MENU", "Breakfast");
        MenuComponent dinerMenu = new Menu("DINER  MENU", "Breakfast");
        MenuComponent cafeMenu = new Menu(" CAFE MENU", "DESSERT");
        MenuComponent dessertMenu = new Menu("DESSERT MENU", "DESSERT");

        MenuComponent allMenus = new Menu("all Menus", "All menus combined");
        allMenus.add(pancakeHouseMenu);
        allMenus.add(dinerMenu);
        allMenus.add(cafeMenu);
        dinerMenu.add(new MenuItem("Pasta", "Spaghetti with Marinara Sauce ,and a silce of sourdough bread", true, 3.89));
        dinerMenu.add(dessertMenu);
        dessertMenu.add(new MenuItem("xq","Hello World",true,1.59));
        Waitress waitress = new Waitress(allMenus);
        waitress.printMenu();
        waitress.printVegetarianMenu();

      }

    /**
     * 吧add添加方法封装起来  让代码边的简洁
     *
     * @param menus
     * @return
     */
    public static ArrayList addAll(Menu... menus) {
        System.out.println(menus.length);
        ArrayList resultList = new ArrayList();
        for (int i = 0; i < menus.length; i++) {
            resultList.add(menus[i]);
        }
        return resultList;
    }
}
