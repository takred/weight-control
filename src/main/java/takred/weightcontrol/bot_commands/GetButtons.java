package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.MessageHandler;

@Service
public class GetButtons implements MessageHandler {

    public boolean process(Bot bot, Update update) {
        if (!update.hasCallbackQuery()) {
            if (update.hasMessage()) {
                if (update.getMessage().getText().equals("b")) {
                    bot.sendInlineKeyboardButton(update.getMessage());
                    return true;
                }
            }
        }
        return false;
    }
}
