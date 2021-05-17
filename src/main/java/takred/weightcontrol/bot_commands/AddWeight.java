package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;
import takred.weightcontrol.dto.UserNameAndWeightDto;

@Service
public class AddWeight {

    public void addWeight(Bot bot, Message message) {
        bot.weightService.addWeight(new UserNameAndWeightDto(message.getFrom().getId().toString(), Double.parseDouble(message.getText())));
        bot.sendMessage(message, "Показатель веса добавлен в список.");
    }
}
