package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalDateProviderTests {
    @Test
    void makeSureCurrentTime() {
        LocalDate timeNow = LocalDate.now();
        assertEquals(timeNow, LocalDateProvider.singleton().currentDate());
    }
}
