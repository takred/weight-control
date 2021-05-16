package takred.weightcontrol.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class WeightDto {
    private final String userName;
    private final Double weight;
    private final LocalDateTime date;
    private final UUID id;

    public WeightDto(String userName, Double weight, LocalDateTime date, UUID id) {
        this.userName = userName;
        this.weight = weight;
        this.date = date;
        this.id = id;
    }
}
