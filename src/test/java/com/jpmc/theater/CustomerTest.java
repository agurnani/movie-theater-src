package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testEquals() {
        Customer c1 = new Customer("Anil", "1");
        Customer c2 = new Customer("Anil", "1");
        assertEquals(c1,c2);
    }

    @Test
    void testNotEquals1() {
        Customer c1 = new Customer("Anil", "1");
        Customer c2 = new Customer("Anil", "2");
        assertNotEquals(c1,c2);
    }

    @Test
    void testNotEquals2() {
        Customer c1 = new Customer("Anil", "1");
        Customer c2 = new Customer("Sunil", "1");
        assertNotEquals(c1,c2);
    }

    @Test
    void testHashCode() {
        Customer c1 = new Customer("Anil", "1");
        Customer c2 = new Customer("Anil", "1");
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        Customer c1 = new Customer("Anil", "1");
        assertEquals("name: Anil", c1.toString());
    }

    @Test
    void testGetAndSet() {
        Customer c1 = new Customer("Anil", "id1");
        c1.setName("Sunil");
        c1.setId("id2");
        assertEquals("Sunil", c1.getName());
        assertEquals("id2", c1.getId());
    }
}