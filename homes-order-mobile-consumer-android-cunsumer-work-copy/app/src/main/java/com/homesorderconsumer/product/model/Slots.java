package com.homesorderconsumer.product.model;

/**
 * Created by innoppl on 02/04/18.
 */

public class Slots {

    boolean morning=false;
    boolean afternoon=false;
    boolean evening=false;

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isAfternoon() {
        return afternoon;
    }

    public void setAfternoon(boolean afternoon) {
        this.afternoon = afternoon;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }
}
