package org.flowerfog.company;

import org.flowerfog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;



//1.配置springboot的包扫描
@SpringBootApplication(scanBasePackages = "org.flowerfog")
//2.配置jpa注解的扫描
@EntityScan(value="org.flowerfog.domain.company")
public class CompanyApplication {

    /**
     * 启动方法
     */
    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class,args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}

