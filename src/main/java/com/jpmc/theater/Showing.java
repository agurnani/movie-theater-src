package com.jpmc.theater;

import java.time.LocalDateTime;
import java.util.Objects;

public class Showing {
    private Movie movie;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    public Showing() {
        movie = null;
        sequenceOfTheDay = 0;
        showStartTime = null;
    }
    public void setMovie(Movie m) {
        this.movie = m;
    }
    public void setSequenceOfTheDay(int sd) {
        this.sequenceOfTheDay = sd;
    }

    public void setShowStartTime(LocalDateTime dt) {
        this.showStartTime = dt;
    }

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getShowStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double ticketPrice() {
        return movie.getTicketPrice();
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public double calculateFee(int audienceCount) {
        return movie.calculateTicketPrice(this) * audienceCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Showing showing = (Showing) o;
        return sequenceOfTheDay == showing.sequenceOfTheDay && Objects.equals(movie, showing.movie) && Objects.equals(showStartTime, showing.showStartTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, sequenceOfTheDay, showStartTime);
    }
}
