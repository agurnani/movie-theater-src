package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Theater {

    LocalDateProvider provider;
    private final List<Showing> schedule;

    private static final Logger logger = LoggerFactory.getLogger(Theater.class);
    public Theater(LocalDateProvider provider) {
        this.provider = provider;

        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        schedule = List.of(
            new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
            new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
            new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
            new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
            new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
            new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
            new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
            new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
            new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }

    public String printSchedule() {
        final String CRLF = "\r\n";
        StringBuilder stringBuilder = new StringBuilder();

        LocalDate currentDate = provider.currentDate();
        if( currentDate != null ) {
            stringBuilder.append(currentDate);
        }
        stringBuilder.append(CRLF);
        stringBuilder.append("===================================================");
        stringBuilder.append(CRLF);
        schedule.forEach(s ->
                {
                    stringBuilder.append(s.getSequenceOfTheDay());
                    stringBuilder.append(": ");
                    stringBuilder.append(s.getShowStartTime());
                    stringBuilder.append(" ");
                    stringBuilder.append(s.getMovie().getTitle());
                    stringBuilder.append(" ");
                    stringBuilder.append(humanReadableFormat(s.getMovie().getRunningTime()));
                    stringBuilder.append(" $");
                    stringBuilder.append(s.ticketPrice());
                    stringBuilder.append(CRLF);
                }
        );
        stringBuilder.append("===================================================");
        stringBuilder.append(CRLF);
        return stringBuilder.toString();
    }

    public List<Showing> getSchedule() {
        return this.schedule;
    }

    public String printScheduleJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(schedule);
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public static void main(String[] args) throws JsonProcessingException {
        Theater theater = new Theater(LocalDateProvider.singleton());
        String scheduleStr = theater.printSchedule();
        if( scheduleStr != null ) {
            logger.info("{}",scheduleStr);
        }
        String scheduleJsonStr = theater.printScheduleJson();
        if( scheduleJsonStr != null ) {
            logger.info("{}",scheduleJsonStr);
        }
    }
}
