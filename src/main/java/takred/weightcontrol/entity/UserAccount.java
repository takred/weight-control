package takred.weightcontrol.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class UserAccount {
    @Id
    private Integer telegramUserId;
    private boolean notification;

    public UserAccount() {}

    public UserAccount(Integer telegramUserId) {
        this.telegramUserId = telegramUserId;
        this.notification = false;
    }

    public UserAccount(Integer telegramUserId, boolean notification) {
        this.telegramUserId = telegramUserId;
        this.notification = notification;
    }
}
