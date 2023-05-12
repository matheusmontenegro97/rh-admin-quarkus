package ifpe.br.com.controller;

import ifpe.br.com.model.Clocking;
import ifpe.br.com.repository.ClockingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClockingControllerTest {

    @Mock
    private ClockingRepository clockingRepository;

    @InjectMocks
    private ClockingController clockingController;

    @Test
    void saveClockingSuccessTest() throws Exception {
        Clocking clocking = new Clocking();
        clocking.setEmployeeCode(UUID.randomUUID().toString());
        clocking.setDate(LocalDate.of(2023,2,19));
        clocking.setClockIn(LocalTime.of(9,2));
        clocking.setClockInLunch(LocalTime.of(12,6));
        clocking.setClockOutLunch(LocalTime.of(13,6));
        clocking.setClockInLunch(LocalTime.of(18,2));

        when(clockingRepository.saveClocking(any())).thenReturn(clocking);

        Clocking response = clockingController.saveClocking(clocking);

        assertEquals(clocking, response);
    }

}
