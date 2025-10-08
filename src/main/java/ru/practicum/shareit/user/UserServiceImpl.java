package ru.practicum.shareit.user;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserAlreadyExistsException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    @Override
    public UserDto createUser(UserDto userDto) {
        // Проверка на пустой email
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Проверка формата email
        if (!EMAIL_PATTERN.matcher(userDto.getEmail()).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        // Проверка на уникальность email
        for (User existingUser : users.values()) {
            if (userDto.getEmail().equals(existingUser.getEmail())) {
                throw new UserAlreadyExistsException("Email already exists");
            }
        }

        User user = UserMapper.toUser(userDto);
        user.setId(idGenerator.getAndIncrement());
        users.put(user.getId(), user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // Проверка email на уникальность
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            // Проверка формата email
            if (!EMAIL_PATTERN.matcher(userDto.getEmail()).matches()) {
                throw new IllegalArgumentException("Invalid email format");
            }

            // Проверка на уникальность email (если он изменился)
            if (!userDto.getEmail().equals(user.getEmail())) {
                for (User existingUser : users.values()) {
                    if (userDto.getEmail().equals(existingUser.getEmail())) {
                        throw new UserAlreadyExistsException("Email already exists");
                    }
                }
            }

            user.setEmail(userDto.getEmail());
        }

        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }

        users.put(id, user);
        return UserMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = users.get(id);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users.values()) {
            userDtos.add(UserMapper.toUserDto(user));
        }
        return userDtos;
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }
}