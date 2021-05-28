package takred.weightcontrol.bot_commands;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.ChartCreator;
import takred.weightcontrol.MessageHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class GetChartByButton implements MessageHandler {

    public boolean process(Bot bot, Update update) throws IOException {
        if (update.getMessage() == null) {
            if (update.hasCallbackQuery()) {
                if (update.getCallbackQuery().getData().equals("/gwc")) {
                    ChartCreator chartCreator = new ChartCreator();
                    CategoryDataset categoryDataset = chartCreator.createDataset(bot.weightService.getMyWeight(update.getCallbackQuery().getFrom().getId().toString()));
                    JFreeChart chart = chartCreator.createChart(categoryDataset);
                    BufferedImage bufferedImage = chart.createBufferedImage(1000, 1000);
                    File outputfile = new File("chart.png");
                    ImageIO.write(bufferedImage, "png", outputfile);
                    bot.sendPhoto(update.getCallbackQuery(), outputfile);
                    return true;
                }
            }
        }
        return false;
    }
}
