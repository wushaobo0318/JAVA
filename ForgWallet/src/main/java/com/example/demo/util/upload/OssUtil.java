package com.example.demo.util.upload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.example.demo.util.PropertiesFile;
import com.example.demo.util.security.Base64;

public class OssUtil {

	/***
	 * 头像上传到阿里云oss
	 * @param request 内含字节流
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
	
	public static void uploadImageBase64(String base64,String folder, String uid) throws IOException {
		if(!folder.contains("/"))folder+="/";
		InputStream is = new ByteArrayInputStream(Base64.decode(base64)); 
		OSSClient ossClient = new OSSClient(PropertiesFile.getValue("aliyun_oss_endpoint"), PropertiesFile.getValue("aliyun_accessKeyId"), PropertiesFile.getValue("aliyun_accessKeySecret"));
		ossClient.putObject(new PutObjectRequest(PropertiesFile.getValue("aliyun_oss_bucketName"), folder+uid+".png", is));
		ossClient.shutdown();
	}
	/*public static void delete(String bucketName) throws IOException {
		OSSClient ossClient = new OSSClient(PropertiesFile.getValue("aliyun_oss_endpoint"), PropertiesFile.getValue("aliyun_accessKeyId"), PropertiesFile.getValue("aliyun_accessKeySecret"));
		ossClient.deleteObject("ghqkl",bucketName);
		ossClient.shutdown();
	}
	*/
}
