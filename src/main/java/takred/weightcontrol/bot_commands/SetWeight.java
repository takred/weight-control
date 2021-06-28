package takred.weightcontrol.bot_commands;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.MessageHandler;
import takred.weightcontrol.dto.WeightDto;
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
                boolean withinTheBorder = Math.abs(obj.getWeight() - newWeight) > 0
                        && Math.abs(obj.getWeight() - newWeight) <= recordBoundary;
                if (!withinTheBorder) {
                    bot.sendMessage(update.getMessage(), "Введённый вес больше предыдущего на 5кг. Такого не может быть!");
                    return true;
                }

                LocalDateTime dateTime = LocalDateTime.now();
                LocalTime midday = LocalTime.of(12, 00);

                if (addWeight.addWeight(bot, update.getMessage(), weights)) {
                    return true;
                }

                boolean now = dateTime.toLocalTime().getHour() >= midday.getHour();
                boolean inRecording = obj.getDate().toLocalTime().getHour() >= midday.getHour();
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
}
