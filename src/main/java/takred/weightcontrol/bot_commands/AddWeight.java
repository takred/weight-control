package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;
import takred.weightcontrol.MessageHandler;
import takred.weightcontrol.dto.UserNameAndWeightDto;
import takred.weightcontrol.dto.WeightDto;

import java.util.List;

@Service
public class AddWeight{

    public boolean addWeight(Bot bot, Message message, List<WeightDto> weights) {
        if (weights.isEmpty()) {
            bot.weightService.addWeight(new UserNameAndWeightDto(message.getFrom().getId().toString(), Double.parseDouble(message.getText())));
            bot.sendMessage(message, "Показатель веса добавлен в список.");
            return true;
        }
        return false;
    }

    public boolean addWeight(Bot bot, Message message, boolean condition) {
        if (!condition) {
            bot.weightService.addWeight(new UserNameAndWeightDto(message.getFrom().getId().toString(), Double.parseDouble(message.getText())));
            bot.sendMessage(message, "Показатель веса добавлен в список.");
            return true;
        }
        return false;
    }
}
