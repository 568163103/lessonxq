package headfirst.six;

public class SimpleRemoteControlTest {

    public static void main(String[] args) {
        //在这里相当于我们的遥控器 客户
        SimpleRemoteControl simpleRemoteControl  = new SimpleRemoteControl();

        Light light  =  new Light();
        LightOnCommand lightOnCommand  = new LightOnCommand(light);
        Garage garage = new Garage();
        GarageDoorOpenCommand garageDoorOpenCommand = new GarageDoorOpenCommand(garage);
        simpleRemoteControl.setCommand(lightOnCommand);
        simpleRemoteControl.buttonWasPressed();
        simpleRemoteControl.setCommand(garageDoorOpenCommand);
        simpleRemoteControl.buttonWasPressed();

    }
}
