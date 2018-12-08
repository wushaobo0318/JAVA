package com.example.demo.util.security;

import java.io.ByteArrayOutputStream;

import org.apache.commons.lang.StringUtils;

public class ImgCodeUtil {
//	public String createPicVerificationCode(String param) {
//        if (StringUtils.isNotBlank(param)){
//            byte[] captchaChallengeAsJpeg;
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//            try {
//                String verificationCodeText = defaultKaptcha.createText();
//                //加入到session方便后面验证
//                request.getSession().setAttribute(param,verificationCodeText);
//                BufferedImage image = defaultKaptcha.createImage(verificationCodeText);
//                ImageIO.write(image,"jpg",outputStream);
//                captchaChallengeAsJpeg = outputStream.toByteArray();
//                return Base64.getEncoder().encodeToString(captchaChallengeAsJpeg);
//            }catch (Exception e){
//                log.error("生成图片验证码异常"+e);
//                e.printStackTrace();
//            }finally {
//                try {
//                    outputStream.close();
//                }catch (IOException e){
//                    log.error("生成图片验证码结束，关闭ByteArrayOutputStream资源失败"+e);
//                    e.printStackTrace();
//                }
//            }
//        }
//        return "验证码生成失败，请重试";
//    }

}
