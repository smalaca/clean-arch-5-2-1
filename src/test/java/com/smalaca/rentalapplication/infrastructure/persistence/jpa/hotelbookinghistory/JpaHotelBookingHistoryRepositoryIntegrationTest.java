package com.smalaca.rentalapplication.infrastructure.persistence.jpa.hotelbookinghistory;

import com.smalaca.rentalapplication.domain.hotelbookinghistory.HotelBookingHistory;
import com.smalaca.rentalapplication.domain.hotelbookinghistory.HotelBookingHistoryAssertion;
import com.smalaca.rentalapplication.domain.hotelbookinghistory.HotelBookingHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Tag("DomainRepositoryIntegrationTest")
class JpaHotelBookingHistoryRepositoryIntegrationTest {
    @Autowired
    private HotelBookingHistoryRepository repository;
    @Autowired private SpringJpaHotelBookingHistoryRepository jpaRepository;
    private String hotelId;

    @AfterEach
    void deleteHotelBookingHistory() {
        if (hotelId != null) {
            jpaRepository.deleteById(hotelId);
        }
    }

    @Test
    void shouldRecognizeHotelBookingHistoryDoesNotExist() {
        String id = randomId();

        assertThat(repository.existsFor(id)).isFalse();
    }

    @Test
    void shouldRecognizeHotelBookingHistoryExists() {
        String id = randomId();
        repository.save(new HotelBookingHistory(id));

        assertThat(repository.existsFor(id)).isTrue();
    }

    @Test
    @Transactional
    void shouldFindExistingHotelBookingHistory() {
        hotelId = randomId();
        int hotelRoomNumber = 78;
        LocalDateTime bookingDateTime = LocalDateTime.now();
        String tenantId = randomId();
        List<LocalDate> days = asList(LocalDate.now());
        HotelBookingHistory hotelBookingHistory = new HotelBookingHistory(hotelId);
        hotelBookingHistory.add(hotelRoomNumber, bookingDateTime, tenantId, days);
        repository.save(hotelBookingHistory);

        HotelBookingHistory actual = repository.findFor(hotelId);

        HotelBookingHistoryAssertion
                .assertThat(actual).hasHotelRoomBookingHistoryFor(hotelRoomNumber, bookingDateTime, tenantId, days);
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }
}