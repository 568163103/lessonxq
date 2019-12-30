package concurrentprogramming.nine;

/**
 * @author xq
 */
public class ClassInit {
   static class Parent{
       static int value = 10;

       static {
           value = 20;
       }
   }

   static class Child extends Parent{
       static  int i = value;
   }

    public static void main(String[] args) {
        System.out.println(Parent.value);
    }



}


