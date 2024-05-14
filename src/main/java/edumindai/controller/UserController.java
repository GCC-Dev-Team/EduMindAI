package edumindai.controller;

import edumindai.common.Response;
import edumindai.model.dto.LoginRequest;
import edumindai.model.dto.RegisterRequest;
import edumindai.model.vo.LoginVO;
import edumindai.model.vo.RegisterVO;
import edumindai.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @PostMapping("/login")
    public Response<LoginVO> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public Response<RegisterVO> register(@RequestBody RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    @GetMapping("/topics")
    public Response getTopics() {
        return userService.getTopics();
    }


}
