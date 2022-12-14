package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TheaterTests {
    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 4);
        assertEquals(50, reservation.totalFee());
    }

    @Test
    void totalFeeForCustomerThrows() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Customer john = new Customer("John Doe", "id-12345");
        assertThrows(IllegalStateException.class, () -> theater.reserve(john, 11, 4));
    }

    @Test
    void printMovieSchedule() {
        LocalDate ld = LocalDateProvider.singleton().currentDate();
        final String expectedOutput = ld+"\r\n" +
                "===================================================\r\n" +
                "1: "+ld+"T09:00 Turning Red (1 hour 25 minutes) $11.0\r\n" +
                "2: "+ld+"T11:00 Spider-Man: No Way Home (1 hour 30 minutes) $12.5\r\n" +
                "3: "+ld+"T12:50 The Batman (1 hour 35 minutes) $9.0\r\n" +
                "4: "+ld+"T14:30 Turning Red (1 hour 25 minutes) $11.0\r\n" +
                "5: "+ld+"T16:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5\r\n" +
                "6: "+ld+"T17:50 The Batman (1 hour 35 minutes) $9.0\r\n" +
                "7: "+ld+"T19:30 Turning Red (1 hour 25 minutes) $11.0\r\n" +
                "8: "+ld+"T21:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5\r\n" +
                "9: "+ld+"T23:00 The Batman (1 hour 35 minutes) $9.0\r\n" +
                "===================================================\r\n";

        Theater theater = new Theater(LocalDateProvider.singleton());
        String scheduleStr = theater.printSchedule();
        assertEquals(expectedOutput, scheduleStr);
    }
    @Test
    void printMovieScheduleJson() throws JsonProcessingException {
        Theater theater = new Theater(LocalDateProvider.singleton());
        List<Showing> scheduleOriginal = theater.getSchedule();
        String jsonSchedule = theater.printScheduleJson();
        // convert json back to object
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        TypeFactory typeFactory = mapper.getTypeFactory();
        List<Showing> scheduleFromJson = mapper.readValue(jsonSchedule, typeFactory.constructCollectionType(List.class, Showing.class));
        assertTrue(scheduleFromJson.size()==scheduleOriginal.size() && scheduleFromJson.containsAll(scheduleOriginal) && scheduleOriginal.containsAll(scheduleFromJson) );
    }

    @Test
    void mainTest() {
        assertDoesNotThrow(()->Theater.main(null));
    }
}
