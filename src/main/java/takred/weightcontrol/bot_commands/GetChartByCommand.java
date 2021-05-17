package takred.weightcontrol.bot_commands;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;
import takred.weightcontrol.ChartCreator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class GetChartByCommand {

    public void getChartByCommand(Bot bot, Message message) throws IOException {
        ChartCreator chartCreator = new ChartCreator();
        CategoryDataset categoryDataset = chartCreator.createDataset(bot.weightService.getMyWeight(message.getFrom().getId().toString()));
        JFreeChart chart = chartCreator.createChart(categoryDataset);
        BufferedImage bufferedImage = chart.createBufferedImage(1000, 1000);
        File outputfile = new File("chart.png");
        ImageIO.write(bufferedImage, "png", outputfile);
        bot.sendPhoto(message, outputfile);
    }
}
