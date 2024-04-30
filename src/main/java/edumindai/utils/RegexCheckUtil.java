package edumindai.utils;



import edumindai.enums.RegexOrderEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCheckUtil {
    /**
     * 检查字符串的长度
     *
     * @param str 检查的字符串
     * @param begin 开始长度
     * @param end 结束长度
     * @return 是否通过检验
     */
    public static boolean checkStringLength(String str, int begin, int end) {
        int length = str.length();
        //长度检查，如果满足就返回ture
        return length > begin && length < end;

    }

    /**
     * 检查密码安全性
     * @param password 密码
     * @return 安全返回true
     */
    public static boolean isSecurePassword(String password) {
        //判断密码是否符合安全性正则表达式
     return regexCheck(password, RegexOrderEnum.Secure_Password);
    }

    /**
     * 判断是否是邮箱
     * @param email 需要判断的字符串
     * @return true是邮箱
     */
    public static boolean isEmail(String email) {
        //判断是否是邮箱
        return regexCheck(email,RegexOrderEnum.Email_Regex);
    }
    public static boolean isPhone(String phone) {
        return regexCheck(phone,RegexOrderEnum.Phone_Regex);
    }

    /**
     * 判断是否可以作为用户昵称
     * @param nickname 用户昵称
     * @return true 是
     */
    public static boolean isUserNickname(String nickname) {
        //用户名判断
        return regexCheck(nickname,RegexOrderEnum.User_Nickname_Regex);
    }

    /**
     * 自定义正则表达式判断
     * @param str 需要判断的字符串
     * @param regexOrderEnum 正则表达式枚举
     * @return true 是
     */
    public static boolean regexCheck(String str, RegexOrderEnum regexOrderEnum) {
        Pattern pattern = Pattern.compile(regexOrderEnum.getRegexOrder());
        Matcher matcher = pattern.matcher(str);
        //安全性检查如果满足就返回ture
        return matcher.matches();
    }
}
