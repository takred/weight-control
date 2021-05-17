package takred.weightcontrol.dto;

public class UserNameAndWeightDto {
    private final String userName;
    private final Double weight;

    public UserNameAndWeightDto(String userName, Double weight) {
        this.userName = userName;
        this.weight = weight;
    }

    public String getUserName() {
        return userName;
    }

    public Double getWeight() {
        return weight;
    }
}
