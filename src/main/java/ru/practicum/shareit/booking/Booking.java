package ru.practicum.shareit.booking;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import java.time.LocalDateTime;

public class Booking {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Item item;
    private User booker;
    private String status;

    public Booking() {
    }

    public Booking(Long id, LocalDateTime start, LocalDateTime end, Item item, User booker, String status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.item = item;
        this.booker = booker;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}