package takred.weightcontrol.web;

import org.springframework.web.bind.annotation.*;
import takred.weightcontrol.dto.UserNameAndWeightDto;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.service.WeightService;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class WeightController {

    private final WeightService weightService;

    public WeightController(WeightService weightService) {
        this.weightService = weightService;
    }

    @PostMapping(value = "add_weight")
    public void addWeight(@RequestBody UserNameAndWeightDto dto) {
        weightService.addWeight(dto);
    }

    @RequestMapping(value = "get_my_weight/{user}")
    public List<WeightDto> getMyWeight(@PathVariable("user") String userName) {
        return weightService.getMyWeight(userName);
    }
}
