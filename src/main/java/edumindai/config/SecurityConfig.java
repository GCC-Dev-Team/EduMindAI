package edumindai.config;


import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration  //标记此类为一个springboot的配置类
@EnableWebSecurity  //开启security基于web开发的安全机制
public class SecurityConfig {
    @Resource
    private UserDetailsService UserDetails;

//    @Resource
//    private JwtRequestFilter JwtRequestFilter;

    /*
    密码加密器，
    	用户表中的用户密码等敏感信息都需要加密存储(舍弃这个配置,不需要在这个加密器单独注册为bean)
     */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }


    //2.配置认证管理器，security框架默认不提供,这个配置了加密器不需要将加密器注册为bean,
    // 这个已经注册为bean,所有的权限以及密码配置都是这个配置管理的
    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //设置securityUserDetailService，告知security框架，按照指定的类进行身份校验
        daoAuthenticationProvider.setUserDetailsService(UserDetails);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return new ProviderManager(daoAuthenticationProvider);

    }

    //3.配置springsecurity的放行路径等信息
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        //对所有请求按照以下约定进行拦截和放行
        http.authorizeHttpRequests(
            		//requestMatchers 指定匹配路径
            		//permitAll 让security跳过之前通过requestMatchers匹配到的路径，
                auth -> auth.requestMatchers("/user/login","user/signup","/user/verify-face","/dashboard/heat-map","/dashboard/years","/dashboard/location").permitAll()
            		//anyRequest 指定除requestMatchers匹配路径之外的其他路径
            		//authenticated 让anyRequest匹配到的所有路径都通过security校验
                        .anyRequest().authenticated()
        );

        //关闭 防止客户端的 csrf（跨站伪造） 攻击行为 的能力
        // 从security过滤器链中撤出 CsrfFilter 
        http.csrf(AbstractHttpConfigurer::disable);

        
        //将自定义的token认证过滤器加入到security-filterChian中，并指定其位置
      //  http.addFilterBefore(JwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
