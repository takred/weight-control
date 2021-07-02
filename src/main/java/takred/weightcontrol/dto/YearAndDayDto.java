package takred.weightcontrol.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class YearAndDayDto {
    private final Integer year;
    private final Integer day;

    public YearAndDayDto(Integer year, Integer day) {
        this.year = year;
        this.day = day;
    }

    public YearAndDayDto(LocalDateTime localDateTime) {
        this.year = localDateTime.getYear();
        this.day = localDateTime.getDayOfYear();
    }
}
