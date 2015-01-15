package net.shopxx.homawechat.tool;
/**
 *微信通用接口凭证  
 * @author xiaolai
 * @version 创建时间：Mar 7, 2014 4:09:14 PM
 * 类说明 
 */
public class AccessToken {   
    // 获取到的凭证   
    private String token;   
    // 凭证有效时间，单位：秒   
    private int expiresIn;   
         
    public String getToken() {   
        return token;   
    }   
    public void setToken(String token) {   
        this.token = token;   
    }   
         
    public int getExpiresIn() {   
        return expiresIn;   
    }   
         
    public void setExpiresIn(int expiresIn) {   
        this.expiresIn = expiresIn;   
    }   
}
