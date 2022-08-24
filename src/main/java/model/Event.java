package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class Event {

    private int id;
    private String name;
    private String place;
    private double price;
    private boolean isOnline;

    private EventType eventType;


    public Event(String name, String place, double price, boolean isOnline, EventType eventType) {
        this.name = name;
        this.place = place;
        this.price = price;
        this.isOnline = isOnline;
        this.eventType = eventType;
    }
}
