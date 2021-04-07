package com.example.member.Utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class encrption {


    /**
     * md5方法加密
     * @param password
     * @return
     */
    public static String md5(String password){
        return new Md5Hash(password).toString();
    }

    /**
     *加盐 + 多次加密
     * 需要把这个随机数盐也保存进数据库中
     */
    public static String multiAndSalt(String password){
        //String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String salt = "maple";
        int times = 1024;   //加密次数
        String alogrithmName  = "md5";  //加密算法

        String encodePassword = new SimpleHash(alogrithmName,password,salt,times).toString();
        String s = new SimpleHash(alogrithmName, password, salt, times).toHex();   //结果相同
        return encodePassword;
    }


}
