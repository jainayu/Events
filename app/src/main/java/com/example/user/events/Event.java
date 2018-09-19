package com.example.user.events;

import java.util.UUID;

public class Event {
    String name;
    String roomNo;
    String organizer;
    String date;
    UUID id;

    public Event() {

    }
    public Event(String name, String roomNo, String organizer) {
        this.name = name;
        this.roomNo = roomNo;
        this.date = date;
        this.organizer = organizer;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDate() {return date; }

    public void setDate(String date){ this.date = date; }
    public UUID getId() { return id; }

    public void setId(UUID id) { this.id = id; }

}
