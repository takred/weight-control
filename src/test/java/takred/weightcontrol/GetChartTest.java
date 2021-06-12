package takred.weightcontrol;

import org.junit.jupiter.api.Test;
import takred.weightcontrol.bot_commands.GetChartByCommand;
import takred.weightcontrol.dto.WeightDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetChartTest {

    @Test
    void getLowConfinesLessThanTen() {
        GetChartByCommand getChartByCommand = new GetChartByCommand(null);
        List<WeightDto> dtos = new ArrayList<>();
        List<WeightDto> dtos2 = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            dtos.add(new WeightDto("", (double) i, null, null));
        }

        assertEquals(0, getChartByCommand.getLowConfines(dtos));
    }

    @Test
    void getLowConfinesMoreThanTen() {
        GetChartByCommand getChartByCommand = new GetChartByCommand(null);
        List<WeightDto> dtos = new ArrayList<>();

        for (int i = 5; i < 11; i++) {
            dtos.add(new WeightDto("", (double) i * 2 + 2, null, null));
        }

        assertEquals(2, getChartByCommand.getLowConfines(dtos));
    }
}


