package edumindai.enums.db;

public enum UserTypeEnum {

    ADMIN("管理员"),
    ORDINARY("普通用户"),
    VIP("会员");

    private String message;

    UserTypeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
