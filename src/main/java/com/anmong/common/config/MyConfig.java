package com.anmong.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Desc:properties文件获取工具类
 * Created by hafiz.zhang on 2016/9/15.
 */
public class MyConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyConfig.class);
    private static Properties props;
    static{
        loadProps();
    }

    synchronized static private void loadProps(){
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
        	/*第一种，通过类加载器进行获取properties文件流*/
            in = MyConfig.class.getClassLoader().getResourceAsStream("config.properties");
            /*第二种，通过类进行获取properties文件流*/
            //in = PropertyUtil.class.getResourceAsStream("/jdbc.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("jdbc.properties文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("jdbc.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }

    public static String getConfig(String key){
        if(null == props) {
        	logger.info("未获取到"+key+"的参数信息,重新读取配置文件");
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if(null == props) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
