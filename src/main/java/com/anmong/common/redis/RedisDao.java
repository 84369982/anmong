package com.anmong.common.redis;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import com.anmong.common.util.Jdk8TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("redisDao")
public class RedisDao {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean saveString(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("新增错误:{}", e.getMessage());
            return false;
        }

    }

    public boolean saveString(String key, String value,Long time) {
        try {
            redisTemplate.opsForValue().set(key, value,time);
            return true;
        } catch (Exception e) {
            logger.error("新增错误:{}", e.getMessage());
            return false;
        }

    }

    public Set<String> getKeysByQuery(String query) {
        return   redisTemplate.keys(query);
    }

    public boolean saveStringWithExpire(String key, String value,LocalDateTime expireTime) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expireAt(key,Jdk8TimeUtils.toUtilDate(expireTime));
            return true;
        } catch (Exception e) {
            logger.error("新增错误:{}", e.getMessage());
            return false;
        }

    }

    /**
     * 批量插入元素到现有集合里
     * @param key
     * @param value
     * @return
     */

    public <T> boolean saveElementsToList(String key, Object... value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            logger.error("新增错误:{}", e.getMessage());
            return false;
        }

    }

    /**
     * 创建一个集合
     * @param key
     * @param list
     * @return
     */
    public <T> boolean saveList(String key, Collection<T> list) {
        try {
            redisTemplate.opsForList().rightPushAll(key, list);
            return true;
        } catch (Exception e) {
            logger.error("新增错误:{}", e.getMessage());
            return false;
        }

    }

    public <K, V> boolean saveMap(String key, Map<K, V> value) {
        try {
            redisTemplate.opsForHash().putAll(key, value);
            return true;
        } catch (Exception e) {
            logger.error("新增错误:{}", e.getMessage());
            return false;
        }

    }

    public boolean saveHash(String key, String sonKey ,Object value) {
        try {
            redisTemplate.opsForHash().put(key, sonKey, value);
            return true;
        } catch (Exception e) {
            logger.error("新增错误:{}", e.getMessage());
            return false;
        }

    }

    public Object get(String key){
        try {
            DataType type = redisTemplate.type(key);
            if(DataType.NONE == type){
                logger.info("key不存在");
                return null;
            }else if(DataType.STRING == type){
                return redisTemplate.opsForValue().get(key);
            }else if(DataType.LIST == type){
                return redisTemplate.opsForList().range(key, 0, -1);
            }else if(DataType.HASH == type){
                return redisTemplate.opsForHash().entries(key);
            }else  {
                return null;
            }

        } catch (Exception e) {
            logger.error("查询错误:{}", e.getMessage());
            return null;
        }
    }

    public boolean delete(List<String> keys){
        try{
            redisTemplate.delete(keys);
            return true;
        }catch(Exception e){
            logger.error("删除失败:{}", e.getMessage());
            return false;
        }
    }

    public boolean delete(Set<String> keys){
        try{
            redisTemplate.delete(keys);
            return true;
        }catch(Exception e){
            logger.error("删除失败:{}", e.getMessage());
            return false;
        }
    }

    public boolean delete(String key){
        try{
            redisTemplate.delete(key);
            return true;
        }catch(Exception e){
            logger.error("删除失败:{}", e.getMessage());
            return false;
        }
    }

    /**
     * 设置一个key的过期时间（单位：秒）
     *
     * @param key
     *            key值
     * @param seconds
     *            多少秒后过期
     * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间
     */
    public boolean expire(String key, int seconds) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            return redisTemplate.expire(key, seconds,TimeUnit.SECONDS);
        } catch (Exception ex) {
            logger.error("EXPIRE error[key=" + key + " seconds=" + seconds + "]" + ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * 设置一个key在某个时间点过期
     *
     * @param key
     *            key值
     *            unix时间戳，从1970-01-01 00:00:00开始到现在的秒数
     * @return 1：设置了过期时间 0：没有设置过期时间/不能设置过期时间
     */
    public boolean expireAt(String key, Date date) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        try {
            return redisTemplate.expireAt(key, date);
        } catch (Exception ex) {
            logger.error("EXPIRE error[key=" + key + " unixTimestamp=" + date + "]" + ex.getMessage(), ex);
        }
        return false;
    }






}
