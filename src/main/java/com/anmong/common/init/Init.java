package com.anmong.common.init;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.anmong.common.config.MyConfig;
import com.anmong.common.fastsql.SQLFactory;
import com.anmong.common.fastsql.config.DataSourceType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class Init {
	
    @Bean
    public SQLFactory sqlFactory(@Autowired DataSource dataSource) {
        SQLFactory sqlFactory = new SQLFactory();
        sqlFactory.setDataSource(dataSource);
        if (MyConfig.getConfig("system.databaseType").equals("mysql")) {
            sqlFactory.setDataSourceType(DataSourceType.MY_SQL);
        }
        return sqlFactory;
    }

}
