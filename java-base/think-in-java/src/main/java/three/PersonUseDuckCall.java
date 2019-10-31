package three;

public class PersonUseDuckCall  extends Person{
     public PersonUseDuckCall(){
      quackBehavior = new PersonNoQuack();
     }
}
