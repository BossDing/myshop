package net.shopxx.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShiroTest {

    private static Logger logger = LoggerFactory.getLogger(ShiroTest.class);

    public static void main(String[] args) {
        // Using the IniSecurityManagerFactory, which will use the an INI file
        // as the security file.
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory("C:\\auth.ini");

        // Setting up the SecurityManager...
        org.apache.shiro.mgt.SecurityManager securityManager   = factory.getInstance();
        //SecurityUtils 对象是一个 singleton，这意味着不同的对象可以使用它来获得对当前用户的访问
        //一旦成功地设置了这个 SecurityManager，就可以在应用程序不同部分调用 SecurityUtils.getSubject() 来获得当前用户的信息
        SecurityUtils.setSecurityManager(securityManager);
        
        //获得当前用户的信息
        Subject user = SecurityUtils.getSubject();

        logger.info("User is authenticated:  " + user.isAuthenticated()); //false
        
        
        UsernamePasswordToken token = new UsernamePasswordToken("bjangles11", "dance");
        
        //如果 token 的验证密码不正确, login() 方法会抛出一个 IncorrectCredentialsException
        //在生产代码内这个异常应被明确捕获以便应用程序在用户提供了不正确的代码时能够进行恰当的响应。
        
        //如果用户不正确，login() 方法就会抛出一个 UnknownAccountException。我们既要考虑如何处理这个异常，但又不应向用户提供太多信息。
        //一种常见的做法是不要向用户提示用户名有效、只有密码不正确。这是因为如果有人试图通过猜测获得访问，那么您绝对不会想要暗示此人他所猜测的用户名是正确的
        user.login(token);
        
        logger.info("User is authenticated:  " + user.isAuthenticated());//true
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}
