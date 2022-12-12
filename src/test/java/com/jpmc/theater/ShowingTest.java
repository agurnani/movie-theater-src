package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ShowingTest {

    @Test
    void getMovie() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        Showing showing = new Showing(spiderMan, 1, LocalDateTime.of(LocalDate.now(), LocalTime.now()));

        assertEquals(spiderMan, showing.getMovie());
    }

    @Test
    void getAndSetTest() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        LocalDateTime showStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        Showing showing = new Showing();
        showing.setMovie(spiderMan);
        showing.setShowStartTime(showStartTime);
        showing.setSequenceOfTheDay(3);

        assertEquals(showStartTime, showing.getShowStartTime());
        assertEquals(3, showing.getSequenceOfTheDay());
    }

    @Test
    void isSequence() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        LocalDateTime showStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        Showing showing = new Showing(spiderMan, 1, showStartTime);

        assertTrue(showing.isSequence(1));
    }

    @Test
    void getMovieFee() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        LocalDateTime showStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        Showing showing = new Showing(spiderMan, 1, showStartTime);

        assertEquals(12.5, showing.ticketPrice());
    }

    @Test
    void getSequenceOfTheDay() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        LocalDateTime showStartTime = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        Showing showing = new Showing(spiderMan, 1, showStartTime);

        assertEquals(1, showing.getSequenceOfTheDay());
    }

    @Test
    void calculateFee() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        LocalDateTime showStartTime = LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20));
        Showing showing = new Showing(spiderMan, 5, showStartTime);

        assertEquals(12.5, showing.calculateFee(1));
        assertEquals(25, showing.calculateFee(2));
        assertEquals(50, showing.calculateFee(4));
    }

    @Test
    void hashCodeTest() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 2);
        // date cannot be 7 and time cannot be between 11 and 4 as other discounts apply for those
        LocalDateTime showStartTime = LocalDateTime.of(LocalDate.of(2022,11,16), LocalTime.of(10,20));
        Showing showing1 = new Showing(spiderMan, 5, showStartTime);
        Showing showing2 = new Showing(spiderMan, 5, showStartTime);
        Showing showing3 = new Showing(spiderMan, 1, showStartTime);

        assertEquals(showing1.hashCode(), showing2.hashCode());
        assertNotEquals(showing1.hashCode(),showing3.hashCode());
    }
}