package takred.weightcontrol.builder;

import takred.weightcontrol.dto.WeightDto;

import java.time.LocalDateTime;
import java.util.UUID;

public class WeightDtoBuilder {
    private String userName;
    private Double weight;
    private LocalDateTime date;
    private UUID id;

    public WeightDtoBuilder withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public WeightDtoBuilder withWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    public WeightDtoBuilder withDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public WeightDtoBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public WeightDto build() {
        return new WeightDto(userName, weight, date, id);
    }
}
