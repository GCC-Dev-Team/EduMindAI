package edumindai.enums.db;

public enum UserStatusEnum {
    NORMAL("正常"),
    FROZEN("冻结"),
    DELETE("删除");
    final String message;

    UserStatusEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
