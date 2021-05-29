package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;
import takred.weightcontrol.dto.WeightDto;

@Service
public class RedactWeight {

    public void redactWeight(Bot bot, Message message, WeightDto obj) {
        bot.weightService.setWeight(obj, Double.parseDouble(message.getText().replace(" ", ".")));
        bot.sendMessage(message, "Показатель веса отредактирован.");
    }
}
