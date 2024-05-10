package edumindai.service.impl;


import edumindai.service.FileProcessService;
import edumindai.utils.MinioUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MinioProcessServiceImpl implements FileProcessService {
    @Autowired
    MinioUtil minioUtil;

    @Override
    public String upload(MultipartFile file, String directory, String fileNewName) {
        return minioUtil.upload(file,directory,fileNewName);
    }

    @Override
    public String getUrlByName(@NotNull String fileAllName) {
        return minioUtil.getUrlByName(fileAllName);
    }

    @Override
    public boolean remove(String fileAllName) {
        return minioUtil.remove(fileAllName);
    }
}
