package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;

@Service
public class GetButtons {

    public void getButtons(Bot bot, Message message) {
        bot.sendInlineKeyboardButton(message);
    }
}
