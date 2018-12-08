package com.frogwallet.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.frogwallet.util.security.Base64;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;

public class OssUtil {

	/***
	 * 头像上传到阿里云oss
	 * @param folder 目录
	 * @param uid
	 * @throws IOException
	 */
	public static void uploadImage(MultipartFile file,String folder, String uid) throws IOException {
		if(!folder.contains("/"))folder+="/";
		InputStream is = file.getInputStream();
		OSSClient ossClient = new OSSClient(PropertiesFile.getValue("aliyun_oss_endpoint"), PropertiesFile.getValue("aliyun_accessKeyId"), PropertiesFile.getValue("aliyun_accessKeySecret"));
		ossClient.putObject(new PutObjectRequest(PropertiesFile.getValue("aliyun_oss_bucketName"), folder+uid+".png", is));
		ossClient.shutdown();
	}
	
	public static String uploadImageBase64(String base64,String folder, String uid) throws IOException {
		if(!folder.contains("/"))folder+="/";
		InputStream is = new ByteArrayInputStream(Base64.decode(base64));
		OSSClient ossClient = new OSSClient(PropertiesFile.getValue("aliyun_oss_endpoint"), PropertiesFile.getValue("aliyun_accessKeyId"), PropertiesFile.getValue("aliyun_accessKeySecret"));
		PutObjectResult result=ossClient.putObject(new PutObjectRequest(PropertiesFile.getValue("aliyun_oss_bucketName"), folder+uid+".png", is));
		ossClient.shutdown();
		return null;
	}
	public static void main(String[] args) throws IOException {
		uploadImageBase64(Base64.encodeString("123"), "123", "123");
	}
}
