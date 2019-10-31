package headfirst.six;

import three.Person;

public class CellingFanHighCommand implements Command {
    CeilingFan ceilingFan;
    int prevSpeed;

    public CellingFanHighCommand(CeilingFan ceilingFan) {
        this.ceilingFan = ceilingFan;
    }

    public void execute() {
        this.prevSpeed = ceilingFan.getSpeed();
        ceilingFan.high();
    }
    /**
     *回退上一次执行的操作
     */
    public void undo() {
        if (prevSpeed == CeilingFan.HIGH) {
            ceilingFan.high();
        } else if (prevSpeed == CeilingFan.LOW) {
            ceilingFan.low();
        } else if (prevSpeed == CeilingFan.MEDIUM) {
            ceilingFan.medium();
        } else if (prevSpeed == CeilingFan.OFF) {
            ceilingFan.off();
        }


    }
}
