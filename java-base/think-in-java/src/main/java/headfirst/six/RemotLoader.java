package headfirst.six;

public class RemotLoader {
    public static void main(String[] args) {
        RemoteControlWithUndo  remoteControlWithUndo = new RemoteControlWithUndo();
        Light light = new Light();
        LightOnCommand lightOnCommand = new LightOnCommand(light);
        LightOffCommand lightOffCommand = new LightOffCommand(light);
        remoteControlWithUndo.setCommand(0,lightOnCommand,lightOffCommand);
        remoteControlWithUndo.onButtonWasPushed(0);
        remoteControlWithUndo.offButtonWasPushed(0);
        System.out.println(remoteControlWithUndo);
        remoteControlWithUndo.undoButtonPushed();
        remoteControlWithUndo.offButtonWasPushed(0);
        remoteControlWithUndo.onButtonWasPushed(0);
        System.out.println(remoteControlWithUndo);
        remoteControlWithUndo.undoButtonPushed();
    }
}
