package com.stillcoolme.mall.tiny.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置类，用于配置需要动态生成的mapper接口的路径
 * Created by stillcoolme on 2019/4/8.
 */
@Configuration
@MapperScan("com.stillcoolme.mall.tiny.mbg.mapper")
public class MyBatisConfig {
}
