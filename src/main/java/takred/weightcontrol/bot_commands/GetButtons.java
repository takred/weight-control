package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;

@Service
public class GetButtons {

    public boolean process(Bot bot, Update update) {
        if (update.getMessage().getText().equals("b")) {
            bot.sendInlineKeyboardButton(update.getMessage());
            return true;
        }
        return false;
    }
}
