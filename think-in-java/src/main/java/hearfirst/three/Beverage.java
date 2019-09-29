package hearfirst.three;

public abstract class Beverage {
    public String description = "Unknown Beverage";
    public final String TALL = "TALL";
    public final String GRANDE = "GRANDE";
    public final String VENTI = "VENTI";

    public String getDescription() {
        return description;
    }

    public abstract double cost();
}
