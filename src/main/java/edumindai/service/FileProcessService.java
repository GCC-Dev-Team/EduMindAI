package edumindai.service;


import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
/**
 * 文件处理,后续可以使用策略模式,当对象存储数量多时
 */
public interface FileProcessService {

    /**
     * 文件上传
     * @param file 文件
     * @param directory 目录
     * @param fileNewName 文件名字
     * @return fileAllName 文件的名字(包括目录)(这个是保存数据库的)
     * example: 上传头像新名字:xiaoli 图片,目录在user
     * file 文件 directory user fileNewName xiaoli
     * return user/xiaoli.png
     */
    String upload(MultipartFile file, String directory, String fileNewName);

    /**
     * 查看文件网址
     * @param fileAllName (包括目录的文件名)
     * @return 返回文件的网址
     *
     * example:user/xiaoli.png
     * return : http:xxxxxxxx
     */
    String getUrlByName(@NotNull String fileAllName);

    /**
     * 移除文件
     * @param fileAllName (包括目录的文件名)
     * @return 是否成功移除
     * example:user/xiaoli.png
     * user目录的xiaoli.png图片
     * fileAllName user/xiaoli.png
     */
    boolean remove(String fileAllName);
}
