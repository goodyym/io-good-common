package com.good.cache;

import com.alibaba.dubbo.config.annotation.Service;
import com.iotekclass.common.util.memcache.MemCachedManager;

import java.util.Date;
import java.util.Map;

/**
 * memcache缓存实现
 * Created by wfb on 2015-11-20.
 */
@Service(version = "1.0.0")
public class MemCacheServiceImpl implements MemCacheService {

    /**
     * 从缓存池中获取缓存实例
     */
    private MemCachedManager memcache = MemCachedManager.getInstance();

    /**
     * 根据指定一批Key批量获取缓存内容。
     *
     * @param sKeys 指定的一批Key。
     * @return Map<sKey, oValue>
     */
    @Override
    public Map<String, Object> getMulti(String[] sKeys) {
        return memcache.getMulti(sKeys);
    }

    /**
     * 检测Cache中当前Key是否存在
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        return memcache.exists(key);
    }

    /**
     * 根据key删除缓存中对象
     *
     * @param key
     * @return
     */
    @Override
    public boolean delete(String key) {
        return memcache.delete(key);
    }

    /**
     * 替换存储对象，含有有效期
     *
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    @Override
    public boolean replace(String key, Object value, Date expiry) {
        return memcache.replace(key,value,expiry);
    }

    /**
     * 替换存储对象
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean replace(String key, Object value) {
        return memcache.replace(key,value);
    }

    /**
     * 保存一个带有效期的元素
     *
     * @param key
     * @param value
     * @param expiry
     * @return
     */
    @Override
    public boolean add(String key, Object value, Date expiry) {
        return memcache.add(key,value,expiry);
    }

    /**
     * @param key
     * @param value
     * @param time
     * @return
     * @throws
     * @Description: 添加指定的值到缓存中，并指定过期时间
     */
    @Override
    public boolean add(String key, Object value, Integer time) {
        return memcache.add(key,value,time);
    }

    /**
     * 添加一个指定的值到缓存中.
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public boolean add(String key, Object value) {
        return memcache.add(key,value);
    }

    /**
     * 根据指定的关键字获取对象.
     *
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        return memcache.get(key);
    }
}
