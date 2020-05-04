package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Albert Pinto
 */
public class Exercises extends Event {
    private String name;
    private DateTime endTime;

    public Exercises(String name, String description, DateTime dateTime, DateTime endTime) {
        super(description, dateTime);
        this.name = name;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }
}
