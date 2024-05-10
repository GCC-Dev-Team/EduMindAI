package edumindai.utils;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
 
 
@Slf4j
public class RSAUtils {
 
    /**
     * 公钥文件名称
     */
    private final static String PUBLIC_KEY_FILE_NAME = "public_key.txt";
    /**
     * 私钥文件名称
     */
    private final static String PRIVATE_KEY_FILE_NAME = "private_key.txt";
 
    /**
     * 类型
     */
    public static final String ENCRYPT_TYPE = "RSA";
 
    //获取公钥(Base64编码)
    public static String getPublicKey() throws Exception {
        String publicKeyPath =
                RSAUtils.class.getClassLoader().getResource(PUBLIC_KEY_FILE_NAME).getFile();//路径中不能有中文
        return readFile(publicKeyPath);
    }
 
    //获取私钥(Base64编码)
    public static String getPrivateKey() throws Exception {

        String privateKeyPath =
                RSAUtils.class.getClassLoader().getResource(PRIVATE_KEY_FILE_NAME).getFile();//路径中不能有中文
        return readFile(privateKeyPath);
    }
 
    /**
     * 公钥加密
     *
     * @param content 内容
     * @return {@link String }
     * @author qs.zhang
     * @date 2023-07-26 16:09:28
     */
    public static String encrypt(String content) throws Exception {
        try {
            RSA rsa = new RSA(null, getPublicKey());
            return rsa.encryptBase64(content, KeyType.PublicKey);
        } catch (Exception e) {
            throw new Exception("加密失败") ;
        }
    }
 
    /**
     * 私钥解密
     *
     * @param content 内容
     * @return {@link String }
     * @author qs.zhang
     * @date 2023-07-26 16:09:12
     */
    public static String decrypt(String content) throws Exception {
        try {
            RSA rsa = new RSA(getPrivateKey(), null);
            return rsa.decryptStr(content, KeyType.PrivateKey);
        } catch (Exception e) {
            throw new Exception("解密失败") ;
        }
    }
 
    /**
     * 根据文件路径读取文件内容
     *
     * @param fileInPath
     * @throws IOException
     */
    public static String readFile(Object fileInPath) throws IOException {
        BufferedReader br = null;
        if (fileInPath == null) {
            return null;
        }
        if (fileInPath instanceof String) {
            br = new BufferedReader(new FileReader(new File((String) fileInPath)));
        } else if (fileInPath instanceof InputStream) {
            br = new BufferedReader(new InputStreamReader((InputStream) fileInPath));
        }
        StringBuffer buffer = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        return buffer.toString();
    }
 
 
 
    /**
     * 获取公私钥-请获取一次后保存公私钥使用
     *
     * @param publicKeyFilename  公钥生成的路径
     * @param privateKeyFilename 私钥生成的路径
     */
    public static void generateKeyPair(String publicKeyFilename, String privateKeyFilename) {
        try {
            String path = RSAUtils.class.getClassLoader().getResource("").getPath();
 
            KeyPair pair = SecureUtil.generateKeyPair(ENCRYPT_TYPE);
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            // 获取 公钥和私钥 的 编码格式（通过该 编码格式 可以反过来 生成公钥和私钥对象）
            byte[] pubEncBytes = publicKey.getEncoded();
            byte[] priEncBytes = privateKey.getEncoded();
 
            // 把 公钥和私钥 的 编码格式 转换为 Base64文本 方便保存
            String pubEncBase64 = new Base64Encoder().encode(pubEncBytes);
            String priEncBase64 = new  Base64Encoder().encode(priEncBytes);
 
            FileWriter pub = new FileWriter(path + "/" + publicKeyFilename);
            FileWriter pri = new FileWriter(path + "/" + privateKeyFilename);
 
            pub.write(pubEncBase64);
            pri.write(priEncBase64);
 
            pub.flush();
            pub.close();
 
            pri.flush();
            pri.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}