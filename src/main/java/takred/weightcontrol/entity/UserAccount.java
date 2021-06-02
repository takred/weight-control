package takred.weightcontrol.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserAccount {
    @Id
    private Integer telegramUserId;
    private boolean sendNotifications;

    public UserAccount() {}

    public UserAccount(Integer telegramUserId) {
        this.telegramUserId = telegramUserId;
        this.sendNotifications = false;
    }

    public UserAccount(Integer telegramUserId, boolean sendNotifications) {
        this.telegramUserId = telegramUserId;
        this.sendNotifications = sendNotifications;
    }
}
