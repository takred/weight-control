package takred.weightcontrol;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface MessageHandler {

    boolean process(Bot bot, Update update) throws IOException;
}
