package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        // Валидация email
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (!EMAIL_PATTERN.matcher(userDto.getEmail()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        // Проверка email на уникальность при обновлении
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            if (!EMAIL_PATTERN.matcher(userDto.getEmail()).matches()) {
                throw new IllegalArgumentException("Invalid email format");
            }
        }
        
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}