package takred.weightcontrol;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import takred.weightcontrol.dto.WeightDto;

import java.util.List;

public class Dataset {
    public static CategoryDataset createDataset(List<WeightDto> dtos) {
        String string = "История веса.";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < dtos.size(); i++) {
            WeightDto weightDto = dtos.get(i);
            dataset.addValue(weightDto.getWeight(), string, weightDto.getDate());
        }
        return dataset;
    }
}
