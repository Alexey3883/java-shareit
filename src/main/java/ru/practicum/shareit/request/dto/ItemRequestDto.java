package ru.practicum.shareit.request.dto;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.shareit.item.dto.ItemDto;

public class ItemRequestDto {
    private Long id;
    private String description;
    private LocalDateTime created;
    private List<ItemDto> items;

    public ItemRequestDto() {
    }

    public ItemRequestDto(Long id, String description, LocalDateTime created, List<ItemDto> items) {
        this.id = id;
        this.description = description;
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

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}