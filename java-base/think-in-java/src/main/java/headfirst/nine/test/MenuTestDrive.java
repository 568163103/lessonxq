package headfirst.nine.test;

import headfirst.nine.cafe.CafeMenu;
import headfirst.nine.dinemenu.DinerMenu;
import headfirst.nine.menuitem.PancakeHouseMenu;
import headfirst.nine.service.Menu;

import java.util.ArrayList;


/**
 * @author xq
 */
public class MenuTestDrive {
    public static void main(String[] args) {
        DinerMenu pancakeHouseMenu = new DinerMenu();
        PancakeHouseMenu dinerMenu = new PancakeHouseMenu();
        CafeMenu cafeMenu = new CafeMenu();
        Waitress waitress = new Waitress(addAll(pancakeHouseMenu, dinerMenu, cafeMenu));
        waitress.printMenu();
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
