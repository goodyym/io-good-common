package com.good.base;
import com.alibaba.dubbo.config.annotation.Service;
import com.iotekclass.components.service.cache.redisUtil.redis.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * redis缓存实现类
 * Created by wfb on 2015-11-20.
 */
@Service(version = "1.0.0")
public class RedisServiceImpl implements RedisService {


    @Autowired
    RedisClientTemplate redisClientTemplate;

    /**
     * 设置单个值
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public String set(String key, String value) {
        return redisClientTemplate.set(key,value);
    }

    /**
     * 获取单个值
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return redisClientTemplate.get(key);
    }

    /**
     * 查询元素是否存在
     *
     * @param key
     * @return
     */
    @Override
    public Boolean exists(String key) {
        return redisClientTemplate.exists(key);
    }

    /**
     * 查询元素类型
     *
     * @param key
     * @return
     */
    @Override
    public String type(String key) {
        return redisClientTemplate.type(key);
    }

    @Override
    public Long del(String key){
        return redisClientTemplate.del(key);
    }
    /**
     * 在某段时间后失效
     *
     * @param key
     * @param seconds
     * @return
     */
    @Override
    public Long expire(String key, int seconds) {
        return redisClientTemplate.expire(key,seconds);
    }

    /**
     * 在某个时间点失效
     *
     * @param key
     * @param unixTime （时间戳）
     * @return
     */
    @Override
    public Long expireAt(String key, long unixTime) {
        return redisClientTemplate.expireAt(key,unixTime);
    }

    /**
     * 设置带有有效期的元素
     *
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    @Override
    public String setex(String key, int seconds, String value) {
        return redisClientTemplate.setex(key,seconds,value);
    }

    /**
     * 设置一个hash元素
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    @Override
    public Long hset(String key, String field, String value) {
        return redisClientTemplate.hset(key,field,value);
    }

    /**
     * 获取一个hash元素
     *
     * @param key
     * @param field
     * @return
     */
    @Override
    public String hget(String key, String field) {
        return redisClientTemplate.hget(key,field);
    }
}
