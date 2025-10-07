package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                              @RequestBody ItemDto itemDto) {
        // Валидация полей
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

        if (itemDto.getAvailable() == null) {
            throw new IllegalArgumentException("Available field is required");
        }

        return ResponseEntity.ok(itemService.createItem(itemDto, ownerId));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                              @PathVariable Long itemId,
                                              @RequestBody ItemDto itemDto) {
        return ResponseEntity.ok(itemService.updateItem(itemId, itemDto, ownerId));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                               @PathVariable Long itemId) {
        return ResponseEntity.ok(itemService.getItemById(itemId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemsByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return ResponseEntity.ok(itemService.getItemsByOwner(ownerId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String text) {
        // Если текст пустой, возвращаем пустой список
        if (text.isBlank()) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(itemService.searchItems(text));
    }
}