package com.kang.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
* @author ：kangjinling
* 邮箱 ：401205099@qq.com
* 功能描述 ：
*
*/

public class Md5Utils {
 /**
  * 作者 : kangjinling
  * 邮箱 ：401205099@qq.com
  * 功能描述 ： 小写
  */
    public static String encode(String text){

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb =new StringBuilder();
            for(byte b:result){
                int number = b&0xff;
                String hex = Integer.toHexString(number);
                if(hex.length() == 1){
                    sb.append("0"+hex);
                }else{
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "" ;
    }


    /**
     * 作者 : kangjinling
     * 邮箱 ：401205099@qq.com
     * 功能描述 ： 大写
     */
    public static String UpperCase(String text){

        try {

            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb =new StringBuilder();
            for(byte b:result){
                int number = b&0xff;
                String hex = Integer.toHexString(number);
                if(hex.length() == 1){
                    sb.append("0"+hex);
                }else{
                    sb.append(hex);
                }
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "" ;
    }

}
