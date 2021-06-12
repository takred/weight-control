package takred.weightcontrol;

import org.junit.jupiter.api.Test;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.service.WeightService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WeightServiceTest {

    @Test
    void lessThanTen() {
        WeightService weightService = new WeightService(null, null, null);

        LocalDateTime now = LocalDateTime.now();
        UUID id = UUID.randomUUID();
        List<WeightDto> dtos = new ArrayList<>(List.of(new WeightDto("a", 10.2, now, id)));
        List<WeightDto> dtos2 = new ArrayList<>(List.of(new WeightDto("a", 10.2, now, id)));
        List<WeightDto> newDto = weightService.getLastTenWeights(dtos);
        assertEquals(dtos2, newDto);
    }

    @Test
    void moreThanTen() {
        WeightService weightService = new WeightService(null, null, null);
        LocalDateTime now = LocalDateTime.now();
        UUID id = UUID.randomUUID();
        List<WeightDto> dtos = new ArrayList<>();
        List<WeightDto> dtos2 = new ArrayList<>();
        List<WeightDto> dtos3 = new ArrayList<>();

        for (int i = 0; i < 11; i++) {
            dtos.add(new WeightDto("" + i, (double) i, now, id));
            dtos2.add(new WeightDto("" + i, (double) i, now, id));
        }

        for (int i = 1; i < 11; i++) {
            dtos3.add(dtos.get(dtos.size() - i));
        }

        List<WeightDto> newDto = weightService.getLastTenWeights(dtos);
        assertEquals(10, newDto.size());
        assertEquals(dtos3, newDto);
        assertNotEquals(dtos2, newDto);
    }
}
