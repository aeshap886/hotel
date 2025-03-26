package edu.sabanciuniv.hotelbookingapp.service;

import edu.sabanciuniv.hotelbookingapp.model.Booking;
import edu.sabanciuniv.hotelbookingapp.model.dto.BookingDTO;
import edu.sabanciuniv.hotelbookingapp.model.dto.BookingInitiationDTO;

import java.util.List;

public interface BookingService {

    Booking saveBooking(BookingInitiationDTO bookingInitiationDTO, Long customerId);

    /**
     * Confirms a booking with payment details
     * @param bookingInitiationDTO The booking initiation data including payment method
     * @param customerId The ID of the customer making the booking
     * @return The confirmed booking as a DTO
     */
    BookingDTO confirmBooking(BookingInitiationDTO bookingInitiationDTO, Long customerId);
    
    /**
     * Confirms a booking with specific payment details
     * @param bookingInitiationDTO The booking initiation data
     * @param customerId The ID of the customer making the booking
     * @param paymentMethod The payment method (e.g., "CREDIT_CARD", "PAYPAL")
     * @param paymentReference The payment reference or transaction ID
     * @return The confirmed booking as a DTO
     */
    BookingDTO confirmBookingWithPayment(BookingInitiationDTO bookingInitiationDTO, 
                                       Long customerId,
                                       String paymentMethod, 
                                       String paymentReference);

    List<BookingDTO> findAllBookings();

    BookingDTO findBookingById(Long bookingId);

    List<BookingDTO> findBookingsByCustomerId(Long customerId);

    BookingDTO findBookingByIdAndCustomerId(Long bookingId, Long customerId);

    List<BookingDTO> findBookingsByManagerId(Long managerId);

    BookingDTO findBookingByIdAndManagerId(Long bookingId, Long managerId);

    BookingDTO mapBookingModelToBookingDto(Booking booking);
}