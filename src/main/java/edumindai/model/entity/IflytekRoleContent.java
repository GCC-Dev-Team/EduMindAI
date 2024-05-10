package edumindai.model.entity;

import com.alibaba.fastjson.JSONObject;
import edumindai.enums.IflytekRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 讯飞提问回答的请求组成text的部分,message.text
 * 需要的是
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IflytekRoleContent {

    /**
     * 角色
     */
    private IflytekRoleEnum role;

    /**
     * 内容
     */
    private String content;

    /**
     * 将对象转成json格式
     * @return Json
     */
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("role", role.getRole());
        jsonObject.put("content", content);
        return jsonObject;
    }
}
