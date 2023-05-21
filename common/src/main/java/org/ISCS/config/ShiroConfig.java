package org.ISCS.config;

import org.ISCS.shrio.CustomRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {

    //ShiroFilterFactoryBean 3
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //创建安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);

        return bean;
    }
    //defaultWebSecurityManager 2
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(CustomRealm customRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(customRealm);
        return manager;
    }
}

