package com.ylw.common.web.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * description: Redis工具类
 * create by: YangLinWei
 * create time: 2020/2/20 1:57 下午
 */
@Component
public class RedisUtil {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * description: 如果key存在的话返回fasle 不存在的话返回true
	 * param:
	 * @return
	 */
	public Boolean setNx(String key, String value, Long timeout) {
		Boolean setIfAbsent = stringRedisTemplate.opsForValue().setIfAbsent(key, value);
		if (timeout != null) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
		return setIfAbsent;
	}

	/**
	 * 存放string类型
	 *
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 * @param timeout
	 *            超时间
	 */
	public void setString(String key, String data, Long timeout) {
		try {

			stringRedisTemplate.opsForValue().set(key, data);
			if (timeout != null) {
				stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			}

		} catch (Exception e) {

		}

	}

	/**
	 * 开启Redis 事务
	 *
	 */
	public void begin() {
		// 开启Redis 事务权限
		stringRedisTemplate.setEnableTransactionSupport(true);
		// 开启事务
		stringRedisTemplate.multi();

	}

	/**
	 * 提交事务
	 *
	 */
	public void exec() {
		// 成功提交事务
		stringRedisTemplate.exec();
	}

	/**
	 * 回滚Redis 事务
	 */
	public void discard() {
		stringRedisTemplate.discard();
	}

	/**
	 * 存放string类型
	 *
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 */
	public void setString(String key, String data) {
		setString(key, data, null);
	}

	/**
	 * 根据key查询string类型
	 *
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		return value;
	}

	/**
	 * 根据对应的key删除key
	 *
	 * @param key
	 */
	public Boolean delKey(String key) {
		return stringRedisTemplate.delete(key);

	}
}
