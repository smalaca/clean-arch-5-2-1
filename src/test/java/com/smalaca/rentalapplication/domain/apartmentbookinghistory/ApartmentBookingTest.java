package com.smalaca.rentalapplication.domain.apartmentbookinghistory;

import com.smalaca.rentalapplication.domain.period.Period;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.smalaca.rentalapplication.domain.apartmentbookinghistory.ApartmentBookingAssertion.assertThat;

class ApartmentBookingTest {

    @Test
    void shouldCreateStartApartmentBookingWithAllRequiredInformation() {
        LocalDateTime bookingDateTime = LocalDateTime.of(2020, 1, 2, 3, 4);
        String ownerId = "123";
        String tenantId = "234";
        LocalDate start = LocalDate.of(2020, 2, 1);
        LocalDate end = LocalDate.of(2020, 2, 8);

        ApartmentBooking actual = ApartmentBooking.start(bookingDateTime, ownerId, tenantId, new Period(start, end));

        assertThat(actual)
                .isStart()
                .hasBookingDateTimeEqualTo(bookingDateTime)
                .hasOwnerIdEqualTo(ownerId)
                .hasTenantIdEqualTo(tenantId)
                .hasPeriodThatHas(start, end);
    }

    @Test
    void shouldVerifyEqualsAndHashCode() {
        EqualsVerifier.simple().forClass(ApartmentBooking.class).verify();
    }
}