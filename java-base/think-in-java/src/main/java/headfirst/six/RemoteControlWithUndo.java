package headfirst.six;

public class RemoteControlWithUndo {
    Command [] onCommands;
    Command [] offCommands;
    Command undoCommand;
    public  RemoteControlWithUndo(){
        onCommands = new Command[7];
        offCommands = new Command[7];
        NoCommand noCommand = new NoCommand();
        for (int i = 0; i < 7; i++) {
            onCommands[i] = noCommand ;
            offCommands[i] = noCommand ;
        }
        undoCommand = noCommand;
    }
    public void setCommand(int slot, Command onCommand , Command offCommand)
    {
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot){
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot)
    {
       offCommands[slot].execute();
       undoCommand =  offCommands[slot];
    }

    public void undoButtonPushed(){
        undoCommand.undo();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n----- Remote Controller -------\n");
        for (int i = 0; i < onCommands.length; i++) {
            sb.append("{slot" + i + "}" + onCommands[i].getClass().getName() + "     " + offCommands[i].getClass().getName() + "\n");
        }
        sb.append("[undo]"+undoCommand.getClass().getName());
        return sb.toString();
    }
}
