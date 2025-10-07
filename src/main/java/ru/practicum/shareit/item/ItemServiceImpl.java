package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final Map<Long, Item> items = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final UserService userService;

    @Autowired
    public ItemServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ItemDto createItem(ItemDto itemDto, Long ownerId) {
        // Проверка существования пользователя
        try {
            userService.getUserById(ownerId);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException("User not found");
        }
        
        Item item = ItemMapper.toItem(itemDto);
        item.setId(idGenerator.getAndIncrement());
        User owner = new User();
        owner.setId(ownerId);
        item.setOwner(owner);
        items.put(item.getId(), item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(Long itemId, ItemDto itemDto, Long ownerId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new ItemNotFoundException("Item not found");
        }
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new ItemNotFoundException("Only owner can update item");
        }
        
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        
        items.put(itemId, item);
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto getItemById(Long itemId, Long userId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new ItemNotFoundException("Item not found");
        }
        return ItemMapper.toItemDto(item);
    }

    @Override
    public List<ItemDto> getItemsByOwner(Long ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner().getId().equals(ownerId))
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> searchItems(String text) {
        if (text == null || text.isBlank()) {
            return new ArrayList<>();
        }
        
        return items.values().stream()
                .filter(item -> item.getAvailable() != null && item.getAvailable())
                .filter(item -> {
                    // Проверяем, что имя и описание не null перед сравнением
                    String name = item.getName();
                    String description = item.getDescription();
                    return (name != null && name.toLowerCase().contains(text.toLowerCase())) ||
                           (description != null && description.toLowerCase().contains(text.toLowerCase()));
                })
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(Long itemId, Long ownerId) {
        Item item = items.get(itemId);
        if (item == null) {
            throw new ItemNotFoundException("Item not found");
        }
        if (!item.getOwner().getId().equals(ownerId)) {
            throw new ItemNotFoundException("Only owner can delete item");
        }
        items.remove(itemId);
    }
}