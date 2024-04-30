package edumindai.enums;

public enum RegexOrderEnum {
    User_Nickname_Regex("^[a-zA-Z0-9]*$", "用户昵称判断的正则"),

    Phone_Regex("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", "手机号判断"),

    Email_Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", "邮箱判断"),

    Secure_Password("^(?=.*[a-zA-Z])(?=.*\\d).{9,}$", "安全密码的正则表达式，包含至少一个字母、至少一个数字，并且总长度大于8位的字符串");

    RegexOrderEnum(String regexOrder, String message) {

        this.regexOrder = regexOrder;

        this.message = message;
    }

    public String getRegexOrder() {
        return regexOrder;
    }

    public String getMessage() {
        return message;
    }

    private final String regexOrder;

    private final String message;
}
