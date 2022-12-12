package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieTests {
    @Test
    void calculateTicketPriceForSpecial() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20)));
        assertEquals(10, spiderMan.calculateTicketPrice(showing));
    }
    @Test
    void calculateTicketPriceForDiscountSequence1() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20)));
        // ticketPrice should be 9.5 => 12.5 - $3 discount for first show
        assertEquals(9.5, spiderMan.calculateTicketPrice(showing));
    }
    @Test
    void movieWithOnlyDiscountSequence2() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 4);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        Showing showing = new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20)));
        assertEquals(10.5, spiderMan.calculateTicketPrice(showing));
    }
    @Test
    void specialMovieWithSmallDiscountSequence1() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),20, 1);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20)));
        assertEquals(16, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void specialMovieWithBigDiscountSequence1() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),5, 1);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20)));
        assertEquals(2, spiderMan.calculateTicketPrice(showing));
    }
    @Test
    void specialMovieWithSmallDiscountSequence2() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),20, 1);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        Showing showing = new Showing(spiderMan, 2,LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20)));
        assertEquals(16, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void specialMovieWithBigDiscountSequence2() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),5, 1);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        Showing showing = new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20)));
        assertEquals(3, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void movieWithBiggestTimeDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),40, 1);
        Showing showing = new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(11,30)));
        assertEquals(30, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void movieWithBiggestDay7Discount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),4, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.of(2022,11,7), LocalTime.of(10,30)));
        assertEquals(3, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void testEquals() {
        Movie m1 = new Movie("Spider-Man", Duration.parse("PT1H20M"), 10, 1);
        Movie m2 = new Movie("Spider-Man", Duration.parse("PT1H20M"), 10, 1);

        assertEquals(m1, m2);
    }

    @Test
    void testHashCode() {
        Movie m1 = new Movie("Spider-Man", Duration.parse("PT1H20M"), 10, 1);
        Movie m2 = new Movie("Spider-Man", Duration.parse("PT1H20M"), 10, 1);

        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void testSetAndGet() {
        Movie movie = new Movie();
        movie.setTitle("Avatar");
        movie.setDescription("Awesome!");
        movie.setRunningTime(Duration.parse("PT2H30M"));
        movie.setTicketPrice(100.00);
        movie.setSpecialCode(3);
        assertEquals("Avatar", movie.getTitle());
        assertEquals("Awesome!", movie.getDescription());
        assertEquals(Duration.parse("PT2H30M"), movie.getRunningTime());
        assertEquals(100.00, movie.getTicketPrice());
        assertEquals(3, movie.getSpecialCode());
    }
}
