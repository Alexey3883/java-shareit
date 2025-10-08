package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    @Override
    public BookingDto createBooking(BookingDto bookingDto, Long userId) {
        return null;
    }

    @Override
    public BookingDto updateBookingStatus(Long bookingId, Boolean approved, Long userId) {
        return null;
    }

    @Override
    public BookingDto getBookingById(Long bookingId, Long userId) {
        return null;
    }

    @Override
    public List<BookingDto> getBookingsByUser(Long userId, String state) {
        return null;
    }

    @Override
    public List<BookingDto> getBookingsByOwner(Long ownerId, String state) {
        return null;
    }
}