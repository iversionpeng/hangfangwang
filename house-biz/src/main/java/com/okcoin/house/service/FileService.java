package com.okcoin.house.service;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.google.common.collect.Lists;
import com.okcoin.house.common.autoconfig.FcOssClient;
import com.okcoin.house.common.autoconfig.OssProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: liupeng
 * @date: 2019/4/24 16:19
 * @description(功能描述): 操作文件工具类
 */
@Service
public class FileService {

    @Autowired
    private OSSClient ossClient;

    @Autowired
    private OssProperties ossProperties;

    @Value("${file.path}")
    private String filePath;


    /**
     * 上传图片至OSS
     *
     * @param file   上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     */
    public String uploadObject2OSS(File file, String folder) throws IOException {
        String resultStr;
        //以输入流的形式上传文件
        InputStream is = new FileInputStream(file);
        //文件名
        String fileName = file.getName();
        //文件大小
        Long fileSize = file.length();
        ObjectMetadata metadata = getObjectMetadata(is, fileName, fileSize);
        //上传文件   (上传文件流的形式)
        PutObjectResult putResult = ossClient.putObject(ossProperties.getBucketName(), ossProperties.getAVATAR_FOLDER() + folder + fileName, is, metadata);
        //解析结果
        resultStr = putResult.getETag();
        return resultStr;
    }

    public String uploadObject2OSS(MultipartFile file, String folder) throws IOException {
        String resultStr;
        //以输入流的形式上传文件
        InputStream is = file.getInputStream();
        //文件名
        String fileName = file.getOriginalFilename();
        //文件大小
        Long fileSize = file.getSize();
        ObjectMetadata metadata = getObjectMetadata(is, fileName, fileSize);
        //上传文件   (上传文件流的形式)
        PutObjectResult putResult = ossClient.putObject(ossProperties.getBucketName(), ossProperties.getAVATAR_FOLDER() + folder + fileName, is, metadata);
        //解析结果
        resultStr = putResult.getETag();
        return resultStr;
    }

    public List<String> uploadObject2OSS(List<MultipartFile> files, String folder) throws IOException {
        List<String> result = Lists.newArrayList();
        for (MultipartFile file : files) {
            //以输入流的形式上传文件
            InputStream is = file.getInputStream();
            //文件名
            String fileName = file.getOriginalFilename();
            //文件大小
            Long fileSize = file.getSize();
            ObjectMetadata metadata = getObjectMetadata(is, fileName, fileSize);
            //上传文件   (上传文件流的形式)
            PutObjectResult putResult = ossClient.putObject(ossProperties.getBucketName(), ossProperties.getAVATAR_FOLDER() + folder + fileName, is, metadata);
            //解析结果
            String resultStr = putResult.getETag();
            result.add(resultStr);
        }

        return result;
    }

    private ObjectMetadata getObjectMetadata(InputStream is, String fileName, Long fileSize) throws IOException {
        //创建上传Object的Metadata
        ObjectMetadata metadata = new ObjectMetadata();
        //上传的文件的长度
        metadata.setContentLength(is.available());
        //指定该Object被下载时的网页的缓存行为
        metadata.setCacheControl("no-cache");
        //指定该Object下设置Header
        metadata.setHeader("Pragma", "no-cache");
        //指定该Object被下载时的内容编码格式
        metadata.setContentEncoding("utf-8");
        //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
        //如果没有扩展名则填默认值application/octet-stream
        metadata.setContentType(getContentType(fileName));
        //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
        metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
        return metadata;
    }

    /**
     * 创建存储空间
     *
     * @param bucketName 存储空间
     * @return
     */
    public String createBucketName(String bucketName) {
        //存储空间
        final String bucketNames = bucketName;
        if (!ossClient.doesBucketExist(bucketName)) {
            //创建存储空间
            Bucket bucket = ossClient.createBucket(bucketName);
            return bucket.getName();
        }
        return bucketNames;
    }

    /**
     * 删除存储空间buckName
     *
     * @param bucketName 存储空间
     */
    public void deleteBucket(String bucketName) {
        ossClient.deleteBucket(bucketName);
    }


    /**
     * 创建模拟文件夹
     *
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名如"qj_nanjing/"
     * @return 文件夹名
     */
    public String createFolder(String bucketName, String folder) {
        //文件夹名
        final String keySuffixWithSlash = folder;
        //判断文件夹是否存在，不存在则创建
        if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
            //创建文件夹
            ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
            //得到文件夹名
            OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
            String fileDir = object.getKey();
            return fileDir;
        }
        return keySuffixWithSlash;
    }

    /**
     * 根据key删除OSS服务器上的文件
     *
     * @param bucketName 存储空间
     * @param folder     模拟文件夹名 如"qj_nanjing/"
     * @param key        Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public void deleteFile(String bucketName, String folder, String key) {
        ossClient.deleteObject(bucketName, folder + key);
    }


    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     *
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public String getContentType(String fileName) {
        //文件的后缀名
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        if (".bmp".equalsIgnoreCase(fileExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(fileExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(fileExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(fileExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.visio";
        }
        if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(fileExtension)) {
            return "text/xml";
        }
        //默认返回类型
        return "image/jpeg";
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(ossProperties.getBucketName(), key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    private List<String> getFilePath(List<MultipartFile> files) {
        List<String> paths = Lists.newArrayList();
        final File[] localFile = new File[1];
        files.stream().filter(x -> Objects.nonNull(x)).forEach(file -> {
            //将文件保存在本地
            try {
                localFile[0] = saveToLocal(file, filePath);
                //获取文件路径
                String path = StringUtils.substringAfterLast(localFile[0].getAbsolutePath(), filePath);
                paths.add(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return paths;
    }

    private File saveToLocal(MultipartFile file, String filePath) throws IOException {
        File newFile = new File(filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        if (!newFile.exists()) {
            newFile.getParentFile().mkdirs();
            newFile.createNewFile();
        }
        com.google.common.io.Files.write(file.getBytes(), newFile);
        return newFile;
    }
}
