package com.good.cache.redisUtil;

import com.iotekclass.components.service.cache.redisUtil.obj.CacheObject;
import com.iotekclass.components.service.cache.redisUtil.obj.RedisObject;

/*********************************************************************************
//* Copyright (C) 2014 mo-win. All Rights Reserved.
//*
//* Filename:      CacheManager.java
//* Revision:      1.0
//* Author:        lxu
//* Created On:    2014-8-13
//* Modified by:   
//* Modified On:   
//*
//* Description:   <description>
/********************************************************************************/

public class CacheManager {

	private static CacheObject<Object> cacheHolder = new CacheObject<Object>();

	private static RedisObject<Object> cacheHolderRedis = new RedisObject<Object>();

	public static CacheObject<Object> getCache() {
		return cacheHolder;
	}

	public static RedisObject<Object> getRedisCache() {
		return cacheHolderRedis;
	}

	static {
		//TODO
		//线程管理类 回收缓存
	}

}
