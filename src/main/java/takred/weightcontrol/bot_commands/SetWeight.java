package takred.weightcontrol.bot_commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.MessageHandler;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.dto.YearAndDayDto;
import takred.weightcontrol.service.WeightService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SetWeight implements MessageHandler {
    private final WeightService weightService;
    private final AddWeight addWeight;
    private final RedactWeight redactWeight;
    private final Double recordBoundary;

    public SetWeight(WeightService weightService,
                     AddWeight addWeight,
                     RedactWeight redactWeight,
                     @Value("${record-boundary}") String recordBoundary) {
        this.weightService = weightService;
        this.addWeight = addWeight;
        this.redactWeight = redactWeight;
        this.recordBoundary = Double.parseDouble(recordBoundary);

    }

    public boolean process(Bot bot, Update update) {
        if (!update.hasCallbackQuery()) {
            if (update.hasMessage()) {
                try {
                    Double.parseDouble(update.getMessage().getText().replace(" ", "."));
                } catch (NumberFormatException e) {
                    System.out.println("Couldn't turn string into number");
                    return false;
                }

                List<WeightDto> weights = weightService.getMyWeight(update.getMessage().getFrom().getId().toString());
                WeightDto obj = weights.get(weights.size() - 1);
                Double newWeight = Double.parseDouble(update.getMessage().getText().replace(" ", "."));

                YearAndDayDto lastRecordingDay = new YearAndDayDto(obj.getDate());
                YearAndDayDto dateTimeNow = new YearAndDayDto(LocalDateTime.now());
                int differenceOfDays = getDifferenceOfDays(lastRecordingDay, dateTimeNow);
                boolean withinTheBorder = Math.abs(obj.getWeight() - newWeight) > 0
                        && Math.abs(obj.getWeight() - newWeight) <= (recordBoundary * differenceOfDays) + recordBoundary;
                if (!withinTheBorder) {
                    bot.sendMessage(update.getMessage(), "Введённый вес больше предыдущего на " + ((recordBoundary * differenceOfDays)
                            + recordBoundary) + "кг. Такого не может быть!");
                    return true;
                }

                LocalDateTime dateTime = LocalDateTime.now();
                LocalTime midday = LocalTime.of(12, 00);
                boolean now = dateTime.toLocalTime().getHour() >= midday.getHour();
                boolean inRecording = obj.getDate().toLocalTime().getHour() >= midday.getHour();

                if (addWeight.addWeight(bot, update.getMessage(), weights)) {
                    return true;
                }

                boolean condition = dateTime.toLocalDate().equals(obj.getDate().toLocalDate())
                        && now == inRecording;

                if (addWeight.addWeight(bot, update.getMessage(), condition)) {
                    return true;
                }

                redactWeight.redactWeight(bot, update.getMessage(), obj);
                return true;
            }
        }
        return false;
    }

    public int getDifferenceOfDays(YearAndDayDto lastRecordingDay, YearAndDayDto dateTimeNow) {
        int differenceOfDays = 0;
        if (!lastRecordingDay.getYear().equals(dateTimeNow.getYear())) {
            LocalDateTime lastDayOfTheYear = LocalDateTime.of(0, 12, 31, 0, 0);
            differenceOfDays = (dateTimeNow.getYear() - lastRecordingDay.getYear()) * lastDayOfTheYear.getDayOfYear() + dateTimeNow.getDay();

            if (differenceOfDays > 20) {
                differenceOfDays = 20;
            }
        } else {
            differenceOfDays = dateTimeNow.getDay() - lastRecordingDay.getDay();
        }
        return differenceOfDays;
    }
}
