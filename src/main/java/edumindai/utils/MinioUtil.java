package edumindai.utils;



import edumindai.config.MinIoClientConfig;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class MinioUtil {
    //必须使用注入的方式否则会出现空指针
    @Autowired
    MinioClient minioClient;
    @Autowired
    MinIoClientConfig minIoClientConfig;


    /**
     * 文件上传
     * @param file 上传的文件
     * @param directory 目录
     * @param fileNewName 存储文件的文件名
     * @return
     */
    public String upload(MultipartFile file, String directory, String fileNewName) {
        try {
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || originalFilename.isEmpty()) {
                file.getName();

                originalFilename=file.getName();
            }

            // 生成随机文件名
            String fileName = directory + "/" + fileNewName + originalFilename.substring(originalFilename.lastIndexOf("."));

            // 获取文件输入流
            try (InputStream inputStream = file.getInputStream()) {
                // 执行文件上传
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minIoClientConfig.getBucket())
                                .object(fileName)
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            return  fileName;

        } catch (Exception e) {

            e.printStackTrace();
            // 在实际应用中，您可以选择记录异常或者抛出自定义异常以进行更好的错误处理
            return null;
        }

    }


    /**
     * 文件上传(无需文件名字)
     *
     * @param file 文件
     * directory 目录
     * @return
     */
    public String upload(MultipartFile file, String directory) {
        return upload(file, directory, UUID.randomUUID().toString().substring(0,10));
    }

    /**
     * 查看文件完整的网址(可以访问的,无加密和时间限定)
     * @param fileAllName
     * @return
     */
    public String getUrlByName(@NotNull String fileAllName) {

        return minIoClientConfig.getPrefix() + "/" + fileAllName;
    }

    /**
     * 删除
     *
     * @param fileAllName(包括目录一起的)
     * @return
     * @throws Exception
     */
    public boolean remove(String fileAllName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minIoClientConfig.getBucket()).object(fileAllName).build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * 批量删除
     * @param fileNameList
     * @return
     */
    public String removes(String[] fileNameList) {


        int temple = 0;
        List<String> errorNameList = new ArrayList<>();
        for (String s : fileNameList) {

            String s1 = s.substring(minIoClientConfig.getPrefix().length());

            boolean remove = remove(s1);

            if (!remove) {
                temple = temple + 1;

                errorNameList.add(s);
            }
        }
        if (temple > 0) {
            return errorNameList.toString();
        }

        return "成功删除!";
    }

    /**
     * 查看文件的网址(是带时间长字符串,有加密的)
     * @param fileAllName 需要带目录
     * @return
     */
    public String preview(String fileAllName) {
        // 查看文件地址
        GetPresignedObjectUrlArgs build = GetPresignedObjectUrlArgs.builder().bucket(minIoClientConfig.getBucket()).object(fileAllName).method(Method.GET).build();
        try {
            return minioClient.getPresignedObjectUrl(build);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
