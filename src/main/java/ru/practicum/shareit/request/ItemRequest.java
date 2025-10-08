package ru.practicum.shareit.request;

import ru.practicum.shareit.user.User;
import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.shareit.item.model.Item;

public class ItemRequest {
    private Long id;
    private String description;
    private User requester;
    private LocalDateTime created;
    private List<Item> items;

    public ItemRequest() {
    }

    public ItemRequest(Long id, String description, User requester, LocalDateTime created, List<Item> items) {
        this.id = id;
        this.description = description;
        this.requester = requester;
        this.created = created;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}