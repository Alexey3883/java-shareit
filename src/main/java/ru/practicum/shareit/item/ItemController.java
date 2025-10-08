package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                              @Valid @RequestBody ItemDto itemDto) {
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