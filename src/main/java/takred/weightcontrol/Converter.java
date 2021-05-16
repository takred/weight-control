package takred.weightcontrol;

import org.springframework.stereotype.Service;
import takred.weightcontrol.dto.WeightDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class Converter {

    public List<String> convert(List<WeightDto> dto) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < dto.size(); i++) {
            WeightDto weightDto = dto.get(i);
            String string = weightDto.getDate().toString() + ";" + weightDto.getWeight().toString();
            list.add(string);
        }
        return list;
    }
}
