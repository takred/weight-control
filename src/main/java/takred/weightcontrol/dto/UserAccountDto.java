package takred.weightcontrol.dto;

import lombok.Value;

@Value
public class UserAccountDto {
    private final Integer telegramUserId;
    private final boolean sendNotifications;

    public UserAccountDto(Integer telegramUserId, boolean sendNotifications) {
        this.telegramUserId = telegramUserId;
        this.sendNotifications = sendNotifications;
    }
}
