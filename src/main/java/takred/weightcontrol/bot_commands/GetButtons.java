package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;

@Service
public class GetButtons {

    public boolean getButtons(Bot bot, Message message) {
        if (message.getText().equals("b")) {
            bot.sendInlineKeyboardButton(message);
            return true;
        }
        return false;
    }
}
