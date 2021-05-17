package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;

@Service
public class GetWeightList {

    public void getWeightList(Bot bot, Message message) {
        bot.sendMessage(message, bot.weightService.getMyWeight(message.getFrom().getId().toString()).toString());
    }
}
