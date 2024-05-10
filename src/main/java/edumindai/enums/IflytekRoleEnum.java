package edumindai.enums;

public enum IflytekRoleEnum {
    User("user", "用户"),
    System("system", "系统"),
    Assistant("assistant", "AI");

    private final String role;
    private final String message;

    IflytekRoleEnum(String role, String message)
    {
        this.role = role;
        this.message = message;
    }

    public String getRole()
    {
        return role;
    }

    public String getMessage()
    {
        return message;
    }
}
