package edumindai.mapper;


import edumindai.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ljz20
* @description 针对表【users】的数据库操作Mapper
* @createDate 2024-04-30 16:14:17
* @Entity generator.domain.Users
*/
@Mapper
public interface UserMapper {

    User findUserByEmail(@Param("email") String email);

    User findUserByPhone(@Param("phone") String phone);

}




