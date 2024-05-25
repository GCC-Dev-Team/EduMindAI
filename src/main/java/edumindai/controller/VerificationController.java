package edumindai.controller;

import edumindai.anno.NoNeedLogin;
import edumindai.common.Response;
import edumindai.service.VerificationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class VerificationController {

    @Resource
    private VerificationService verificationService;
    @GetMapping("/verificationCode")
    @NoNeedLogin
    public Response verificationCode(String phoneNumber,String email)
    {
        return verificationService.verificationCode(phoneNumber,email);
    }

    @GetMapping("/test")
    public Response test()
    {
        return Response.success(200,"测试成功");
    }
}
