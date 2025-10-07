package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                    @RequestBody BookingDto bookingDto) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDto, userId));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> updateBookingStatus(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                          @PathVariable Long bookingId,
                                                          @RequestParam Boolean approved) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(bookingId, approved, userId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                     @PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId, userId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getBookingsByUser(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                              @RequestParam(defaultValue = "ALL") String state) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId, state));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                                               @RequestParam(defaultValue = "ALL") String state) {
        return ResponseEntity.ok(bookingService.getBookingsByOwner(ownerId, state));
    }
}