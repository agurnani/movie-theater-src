package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.DoubleStream;

public class Movie {
    private static final int SPECIAL_MOVIE_CODE = 1;

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRunningTime(Duration runningTime) {
        this.runningTime = runningTime;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getSpecialCode() {
        return specialCode;
    }

    public void setSpecialCode(int specialCode) {
        this.specialCode = specialCode;
    }

    public Movie() {
        title = null;
        description = null;
        runningTime = null;
        ticketPrice = 0.0;
        specialCode = 0;
    }
    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay(), showing.getShowStartTime());
    }

    private double getDiscount(int showSequence, LocalDateTime startTime) {
        double specialDiscount = 0;
        if (SPECIAL_MOVIE_CODE == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }

        double sequenceDiscount = 0;
        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {

            sequenceDiscount = 2; // $2 discount for 2nd show
        }

        // new requirement
        // Any movies showing starting between 11AM ~ 4pm, you'll get 25% discount
        double timeDiscount = 0;

        if( startTime.getHour() >= 11 && startTime.getHour() <= 16 ) {
            timeDiscount = ticketPrice * 0.25;
        }

        // new requirement
        // Any movies showing on 7th, you'll get 1$ discount
        double day7discount = 0;
        if( startTime.getDayOfMonth() == 7 ) {
            day7discount = 1;
        }

        // biggest discount wins
        return DoubleStream.of(specialDiscount, sequenceDiscount, timeDiscount, day7discount).max().getAsDouble();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}