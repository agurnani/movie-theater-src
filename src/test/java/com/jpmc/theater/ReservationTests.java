package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationTests {

    @Test
    void totalFee() {
        var customer = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.now()
        );
        Reservation reservation = new Reservation(customer, showing, 3);
        assertEquals(37.5, reservation.totalFee());
    }

    @Test
    void setAndGetTest() {
        var customer1 = new Customer("John Doe", "unused-id");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.now()
        );
        var showing2 = new Showing(
                new Movie("Avatar", Duration.ofMinutes(90), 12.5, 1),
                1,
                LocalDateTime.now()
        );
        Reservation reservation = new Reservation(customer1, showing, 3);
        var customer2 = new Customer("Anil Gurnani", "unused-id-2");
        reservation.setCustomer(customer2);
        assertEquals(customer2, reservation.getCustomer());
        reservation.setShowing(showing2);
        assertEquals(showing2, reservation.getShowing());
        reservation.setAudienceCount(5);
        assertEquals(5, reservation.getAudienceCount());
    }
}
