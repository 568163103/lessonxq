package com.beyeon.common.cache.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.BitPosParams;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.params.geo.GeoRadiusParam;
import redis.clients.jedis.params.sortedset.ZAddParams;
import redis.clients.jedis.params.sortedset.ZIncrByParams;

import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.config.ResourceUtil;

public class JedisSupport implements JedisCommands,CacheSupport {
	private JedisPool pool;

	private String getPrefix(){
		return ResourceUtil.getAppId() + ".";
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}
	
	public Jedis getJedis() {
		return this.pool.getResource();
	}
	
	public void returnJedis(Jedis jedis) {
		jedis.close();
	}
	
	protected <T> T execute(JedisCallback<T> action) {
		Jedis jedis = null;
		try {
			jedis = this.getJedis();
			T result = action.doInJedis(jedis);
			return result;
		} finally {
			if (null != jedis) {
				this.returnJedis(jedis);
			}
		}
	}
	
	public interface JedisCallback<T> {
		T doInJedis(Jedis jedis);
	}

	public String set(final String key, final String value) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.set(getPrefix() + key, value);
			}
		});
	}

	@Override
	public String set(final String key, final String value, final String nxxx, final String expx, final long time) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {
				return jedis.set(getPrefix() + key, value,nxxx,expx,time);
			}
		});
	}

	@Override
	public String set(final String key, final String value, final String nxxx) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {
				return jedis.set(getPrefix() + key, value,nxxx);
			}
		});
	}

	public String get(final String key) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.get(getPrefix() + key);
			}
		});
	}

	public Boolean exists(final String key) {
		return execute(new JedisCallback<Boolean>() {
			public Boolean doInJedis(Jedis jedis) {				
				return jedis.exists(getPrefix() + key);
			}
		});
	}

	public Long persist(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.persist(getPrefix() + key);
			}
		});
	}

	public String type(final String key) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {
				return jedis.type(getPrefix() + key);
			}
		});
	}

	public Long expire(final String key, final int seconds) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.expire(getPrefix() + key, seconds);
			}
		});
	}

	public Long expireAt(final String key, final long unixTime) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.expireAt(getPrefix() + key, unixTime);
			}
		});
	}

	@Override
	public Long pexpire(final String key, final long milliseconds) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.pexpire(getPrefix() + key, milliseconds);
			}
		});
	}

	@Override
	public Long pexpireAt(final String key, final long millisecondsTimestamp) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.pexpireAt(getPrefix() + key, millisecondsTimestamp);
			}
		});
	}

	public Long ttl(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.ttl(getPrefix() + key);
			}
		});
	}

	@Override
	public Long pttl(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.pttl(getPrefix() + key);
			}
		});
	}

	public Boolean setbit(final String key, final long offset, final boolean value) {
		return execute(new JedisCallback<Boolean>() {
			public Boolean doInJedis(Jedis jedis) {				
				return jedis.setbit(getPrefix() + key,offset, value);
			}
		});
	}

	public Boolean setbit(final String key, final long offset, final String value) {
		return execute(new JedisCallback<Boolean>() {
			public Boolean doInJedis(Jedis jedis) {
				return jedis.setbit(getPrefix() + key,offset, value);
			}
		});
	}

	public Boolean getbit(final String key, final long offset) {
		return execute(new JedisCallback<Boolean>() {
			public Boolean doInJedis(Jedis jedis) {				
				return jedis.getbit(getPrefix() + key, offset);
			}
		});
	}

	public Long setrange(final String key, final long offset, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.setrange(getPrefix() + key,offset, value);
			}
		});
	}

	public String getrange(final String key, final long startOffset, final long endOffset) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.getrange(getPrefix() + key,startOffset, endOffset);
			}
		});
	}

	public String getSet(final String key, final String value) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.getSet(getPrefix() + key, value);
			}
		});
	}

	public Long setnx(final String key, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.setnx(getPrefix() + key, value);
			}
		});
	}

	public String setex(final String key, final int seconds, final String value) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.setex(getPrefix() + key,seconds, value);
			}
		});
	}

	@Override
	public String psetex(final String key, final long milliseconds, final String value) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {
				return jedis.psetex(getPrefix() + key, milliseconds, value);
			}
		});
	}

	public Long decrBy(final String key, final long integer) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.decrBy(getPrefix() + key, integer);
			}
		});
	}

	public Long decr(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.decr(getPrefix() + key);
			}
		});
	}

	public Long incrBy(final String key, final long integer) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.incrBy(getPrefix() + key, integer);
			}
		});
	}

	@Override
	public Double incrByFloat(final String key, final double value) {
		return execute(new JedisCallback<Double>() {
			public Double doInJedis(Jedis jedis) {
				return jedis.incrByFloat(getPrefix() + key, value);
			}
		});
	}

	public Long incr(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.incr(getPrefix() + key);
			}
		});
	}

	public Long append(final String key, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.append(getPrefix() + key, value);
			}
		});
	}

	public String substr(final String key, final int start, final int end) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.substr(getPrefix() + key, start, end);
			}
		});
	}

	public Long hset(final String key, final String field, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.hset(getPrefix() + key, field, value);
			}
		});
	}

	public String hget(final String key, final String field) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.hget(getPrefix() + key, field);
			}
		});
	}

	public Long hsetnx(final String key, final String field, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.hsetnx(getPrefix() + key,field, value);
			}
		});
	}

	public String hmset(final String key, final Map<String, String> hash) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.hmset(getPrefix() + key, hash);
			}
		});
	}

	public List<String> hmget(final String key, final String... fields) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {				
				return jedis.hmget(getPrefix() + key, fields);
			}
		});
	}

	public Long hincrBy(final String key, final String field, final long value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.hincrBy(getPrefix() + key, field, value);
			}
		});
	}

	@Override
	public Double hincrByFloat(final String key, final String field, final double value) {
		return execute(new JedisCallback<Double>() {
			public Double doInJedis(Jedis jedis) {
				return jedis.hincrByFloat(getPrefix() + key, field, value);
			}
		});
	}

	public Boolean hexists(final String key, final String field) {
		return execute(new JedisCallback<Boolean>() {
			public Boolean doInJedis(Jedis jedis) {				
				return jedis.hexists(getPrefix() + key, field);
			}
		});
	}

	public Long hdel(final String key, final String... field) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.hdel(getPrefix() + key, field);
			}
		});
	}

	public Long hlen(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.hlen(getPrefix() + key);
			}
		});
	}

	public Set<String> hkeys(final String key) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.hkeys(getPrefix() + key);
			}
		});
	}

	public List<String> hvals(final String key) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {				
				return jedis.hvals(getPrefix() + key);
			}
		});
	}

	public Map<String, String> hgetAll(final String key) {
		return execute(new JedisCallback<Map<String, String>>() {
			public Map<String, String> doInJedis(Jedis jedis) {				
				return jedis.hgetAll(getPrefix() + key);
			}
		});
	}

	public Long rpush(final String key, final String... value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.rpush(getPrefix() + key, value);
			}
		});
	}

	public Long rpushx(final String key, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.rpushx(getPrefix() + key, value);
			}
		});
	}

	public Long lpush(final String key, final String... value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.lpush(getPrefix() + key, value);
			}
		});
	}

	public Long lpushx(final String key, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.lpushx(getPrefix() + key, value);
			}
		});
	}

	public Long llen(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.llen(getPrefix() + key);
			}
		});
	}

	public List<String> lrange(final String key, final long start, final long end) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {				
				return jedis.lrange(getPrefix() + key, start, end);
			}
		});
	}

	public String ltrim(final String key, final long start, final long end) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.ltrim(getPrefix() + key, start, end);
			}
		});
	}

	public String lindex(final String key, final long index) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.lindex(getPrefix() + key, index);
			}
		});
	}

	public String lset(final String key, final long index, final String value) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.lset(getPrefix() + key, index, value);
			}
		});
	}

	public Long lrem(final String key, final long count, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.lrem(getPrefix() + key, count, value);
			}
		});
	}

	public String lpop(final String key) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.lpop(getPrefix() + key);
			}
		});
	}

	public String rpop(final String key) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.rpop(getPrefix() + key);
			}
		});
	}

	public Long sadd(final String key, final String... member) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.sadd(getPrefix() + key, member);
			}
		});
	}

	public Set<String> smembers(final String key) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.smembers(getPrefix() + key);
			}
		});
	}

	public Long srem(final String key, final String... member) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.srem(getPrefix() + key, member);
			}
		});
	}

	public String spop(final String key) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.spop(getPrefix() + key);
			}
		});
	}

	@Override
	public Set<String> spop(final String key, final long count) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {
				return jedis.spop(getPrefix() + key,count);
			}
		});
	}

	public Long scard(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.scard(getPrefix() + key);
			}
		});
	}

	public Boolean sismember(final String key, final String member) {
		return execute(new JedisCallback<Boolean>() {
			public Boolean doInJedis(Jedis jedis) {				
				return jedis.sismember(getPrefix() + key, member);
			}
		});
	}

	public String srandmember(final String key) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {				
				return jedis.srandmember(getPrefix() + key);
			}
		});
	}

	@Override
	public List<String> srandmember(final String key, final int count) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {
				return jedis.srandmember(getPrefix() + key,count);
			}
		});
	}

	public Long strlen(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.strlen(getPrefix() + key);
			}
		});
	}

	public Long zadd(final String key, final double score, final String member) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zadd(getPrefix() + key, score, member);
			}
		});
	}

	@Override
	public Long zadd(final String key, final double score, final String member, final ZAddParams params) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.zadd(getPrefix() + key, score, member,params);
			}
		});
	}

	@Override
	public Long zadd(final String key, final Map<String, Double> scoreMembers, final ZAddParams params) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.zadd(getPrefix() + key, scoreMembers, params);
			}
		});
	}

	public Long zadd(final String key, final Map<String, Double> scoreMembers) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.zadd(getPrefix() + key, scoreMembers);
			}
		});
	}

	public Set<String> zrange(final String key, final long start, final long end) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrange(getPrefix() + key, start, end);
			}
		});
	}

	public Long zrem(final String key, final String... member) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zrem(getPrefix() + key, member);
			}
		});
	}

	public Double zincrby(final String key, final double score, final String member) {
		return execute(new JedisCallback<Double>() {
			public Double doInJedis(Jedis jedis) {				
				return jedis.zincrby(getPrefix() + key, score, member);
			}
		});
	}

	@Override
	public Double zincrby(final String key, final double score, final String member, final ZIncrByParams params) {
		return execute(new JedisCallback<Double>() {
			public Double doInJedis(Jedis jedis) {
				return jedis.zincrby(getPrefix() + key, score, member,params);
			}
		});
	}

	public Long zrank(final String key, final String member) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zrank(getPrefix() + key, member);
			}
		});
	}

	public Long zrevrank(final String key, final String member) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zrevrank(getPrefix() + key, member);
			}
		});
	}

	public Set<String> zrevrange(final String key, final long start, final long end) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrevrange(getPrefix() + key, start, end);
			}
		});
	}

	public Set<Tuple> zrangeWithScores(final String key, final long start, final long end) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrangeWithScores(getPrefix() + key, start, end);
			}
		});
	}

	public Set<Tuple> zrevrangeWithScores(final String key, final long start, final long end) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeWithScores(getPrefix() + key, start, end);
			}
		});
	}

	public Long zcard(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zcard(getPrefix() + key);
			}
		});
	}

	public Double zscore(final String key, final String member) {
		return execute(new JedisCallback<Double>() {
			public Double doInJedis(Jedis jedis) {				
				return jedis.zscore(getPrefix() + key, member);
			}
		});
	}

	public List<String> sort(final String key) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {				
				return jedis.sort(getPrefix() + key);
			}
		});
	}

	public List<String> sort(final String key, final SortingParams sortingParameters) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {				
				return jedis.sort(getPrefix() + key, sortingParameters);
			}
		});
	}

	public Long zcount(final String key, final double min, final double max) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zcount(getPrefix() + key, min, max);
			}
		});
	}

	public Long zcount(final String key, final String min, final String max) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zcount(getPrefix() + key, min, max);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final double min, final double max) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScore(getPrefix() + key, min, max);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final String min, final String max) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScore(getPrefix() + key, min, max);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScore(getPrefix() + key, max, min);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final String max, final String min) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScore(getPrefix() + key, max, min);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset,
			final int count) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScore(getPrefix() + key, max, min, offset, count);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final double min, final double max, final int offset, final int count) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScore(getPrefix() + key, min, max, offset, count);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset, final int count) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScore(getPrefix() + key, min, max, offset, count);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final double max, final double min, final int offset, final int count) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScore(getPrefix() + key, max, min, offset, count);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScoreWithScores(getPrefix() + key, min, max);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScoreWithScores(getPrefix() + key, min, max);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max,
			final int offset, final int count) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScoreWithScores(getPrefix() + key, min, max, offset, count);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScoreWithScores(getPrefix() + key, max, min);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScoreWithScores(getPrefix() + key, max, min);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max, final int offset, final int count) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrangeByScoreWithScores(getPrefix() + key, min, max, offset, count);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min, final int offset, final int count) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScoreWithScores(getPrefix() + key, max, min, offset, count);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count) {
		return execute(new JedisCallback<Set<Tuple>>() {
			public Set<Tuple> doInJedis(Jedis jedis) {				
				return jedis.zrevrangeByScoreWithScores(getPrefix() + key, max, min, offset, count);
			}
		});
	}

	public Long zremrangeByRank(final String key, final long start, final long end) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zremrangeByRank(getPrefix() + key, start, end);
			}
		});
	}

	public Long zremrangeByScore(final String key, final double start, final double end) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zremrangeByScore(getPrefix() + key, start, end);
			}
		});
	}

	public Long zremrangeByScore(final String key, final String start, final String end) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.zremrangeByScore(getPrefix() + key, start, end);
			}
		});
	}

	@Override
	public Long zlexcount(final String key, final String min, final String max) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.zlexcount(getPrefix() + key, min, max);
			}
		});
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min, final String max) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {
				return jedis.zrangeByLex(getPrefix() + key, min, max);
			}
		});
	}

	@Override
	public Set<String> zrangeByLex(final String key, final String min, final String max, final int offset, final int count) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {
				return jedis.zrangeByLex(getPrefix() + key, min, max,offset,count);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByLex(final String key, final String max, final String min) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {
				return jedis.zrangeByLex(getPrefix() + key, max, min);
			}
		});
	}

	@Override
	public Set<String> zrevrangeByLex(final String key, final String max, final String min, final int offset, final int count) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {
				return jedis.zrevrangeByLex(getPrefix() + key, max, min, offset,count);
			}
		});
	}

	@Override
	public Long zremrangeByLex(final String key, final String min, final String max) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.zremrangeByLex(getPrefix() + key, min, max);
			}
		});
	}

	public Long linsert(final String key, final LIST_POSITION where, final String pivot, final String value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {				
				return jedis.linsert(getPrefix() + key, where, pivot, value);
			}
		});
	}

	public Long lpushx(final String key, final String... strings) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.lpushx(getPrefix() + key, strings);
			}
		});
	}

	public Long rpushx(final String key, final String... strings) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.rpushx(getPrefix() + key, strings);
			}
		});
	}

	public List<String> blpop(final String key) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {
				return jedis.blpop(getPrefix() + key);
			}
		});
	}

	@Override
	public List<String> blpop(final int timeout, final String key) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {
				return jedis.blpop(timeout,getPrefix() + key);
			}
		});
	}

	public List<String> brpop(final String key) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {
				return jedis.brpop(getPrefix() + key);
			}
		});
	}

	@Override
	public List<String> brpop(final int timeout, final String key) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {
				return jedis.brpop(timeout,getPrefix() + key);
			}
		});
	}

	public Long del(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.del(getPrefix() + key);
			}
		});
	}

	public String echo(final String key) {
		return execute(new JedisCallback<String>() {
			public String doInJedis(Jedis jedis) {
				return jedis.echo(getPrefix() + key);
			}
		});
	}

	public Long move(final String key, final int dbIndex) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.move(getPrefix() + key, dbIndex);
			}
		});
	}

	public Long bitcount(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.bitcount(getPrefix() + key);
			}
		});
	}

	public Long bitcount(final String key, final long start, final long end) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.bitcount(getPrefix() + key, start, end);
			}
		});
	}

	@Override
	public Long bitpos(final String key, final boolean value) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.bitpos(getPrefix() + key, value);
			}
		});
	}

	@Override
	public Long bitpos(final String key, final boolean value, final BitPosParams params) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.bitpos(getPrefix() + key, value,params);
			}
		});
	}

	@Override
	public ScanResult<Map.Entry<String, String>> hscan(final String key, final int cursor) {
		return execute(new JedisCallback<ScanResult<Map.Entry<String, String>>>() {
			public ScanResult<Map.Entry<String, String>> doInJedis(Jedis jedis) {
				return jedis.hscan(getPrefix() + key, cursor);
			}
		});
	}

	@Override
	public ScanResult<String> sscan(final String key, final int cursor) {
		return execute(new JedisCallback<ScanResult<String>>() {
			public ScanResult<String> doInJedis(Jedis jedis) {
				return jedis.sscan(getPrefix() + key, cursor);
			}
		});
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final int cursor) {
		return execute(new JedisCallback<ScanResult<Tuple>>() {
			public ScanResult<Tuple> doInJedis(Jedis jedis) {
				return jedis.zscan(getPrefix() + key, cursor);
			}
		});
	}

	@Override
	public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor) {
		return execute(new JedisCallback<ScanResult<Map.Entry<String, String>>>() {
			public ScanResult<Map.Entry<String, String>> doInJedis(Jedis jedis) {
				return jedis.hscan(getPrefix() + key, cursor);
			}
		});
	}

	@Override
	public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor, final ScanParams params) {
		return execute(new JedisCallback<ScanResult<Map.Entry<String, String>>>() {
			public ScanResult<Map.Entry<String, String>> doInJedis(Jedis jedis) {
				return jedis.hscan(getPrefix() + key, cursor,params);
			}
		});
	}

	@Override
	public ScanResult<String> sscan(final String key, final String cursor) {
		return execute(new JedisCallback<ScanResult<String>>() {
			public ScanResult<String> doInJedis(Jedis jedis) {
				return jedis.sscan(getPrefix() + key, cursor);
			}
		});
	}

	@Override
	public ScanResult<String> sscan(final String key, final String cursor, final ScanParams params) {
		return execute(new JedisCallback<ScanResult<String>>() {
			public ScanResult<String> doInJedis(Jedis jedis) {
				return jedis.sscan(getPrefix() + key, cursor,params);
			}
		});
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor) {
		return execute(new JedisCallback<ScanResult<Tuple>>() {
			public ScanResult<Tuple> doInJedis(Jedis jedis) {
				return jedis.zscan(getPrefix() + key, cursor);
			}
		});
	}

	@Override
	public ScanResult<Tuple> zscan(final String key, final String cursor, final ScanParams params) {
		return execute(new JedisCallback<ScanResult<Tuple>>() {
			public ScanResult<Tuple> doInJedis(Jedis jedis) {
				return jedis.zscan(getPrefix() + key, cursor,params);
			}
		});
	}

	@Override
	public Long pfadd(final String key, final String... elements) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.pfadd(getPrefix() + key, elements);
			}
		});
	}

	@Override
	public long pfcount(final String key) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.pfcount(getPrefix() + key);
			}
		});
	}

	@Override
	public Long geoadd(final String key, final double longitude, final double latitude, final String member) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.geoadd(getPrefix() + key, longitude,latitude,member);
			}
		});
	}

	@Override
	public Long geoadd(final String key, final Map<String, GeoCoordinate> memberCoordinateMap) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				return jedis.geoadd(getPrefix() + key, memberCoordinateMap);
			}
		});
	}

	@Override
	public Double geodist(final String key, final String member1, final String member2) {
		return execute(new JedisCallback<Double>() {
			public Double doInJedis(Jedis jedis) {
				return jedis.geodist(getPrefix() + key, member1,member2);
			}
		});
	}

	@Override
	public Double geodist(final String key, final String member1, final String member2, final GeoUnit unit) {
		return execute(new JedisCallback<Double>() {
			public Double doInJedis(Jedis jedis) {
				return jedis.geodist(getPrefix() + key, member1,member2,unit);
			}
		});
	}

	@Override
	public List<String> geohash(final String key, final String... members) {
		return execute(new JedisCallback<List<String>>() {
			public List<String> doInJedis(Jedis jedis) {
				return jedis.geohash(getPrefix() + key, members);
			}
		});
	}

	@Override
	public List<GeoCoordinate> geopos(final String key, final String... members) {
		return execute(new JedisCallback<List<GeoCoordinate>>() {
			public List<GeoCoordinate> doInJedis(Jedis jedis) {
				return jedis.geopos(getPrefix() + key, members);
			}
		});
	}

	@Override
	public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit) {
		return execute(new JedisCallback<List<GeoRadiusResponse>>() {
			public List<GeoRadiusResponse> doInJedis(Jedis jedis) {
				return jedis.georadius(getPrefix() + key, longitude,latitude,radius,unit);
			}
		});
	}

	@Override
	public List<GeoRadiusResponse> georadius(final String key, final double longitude, final double latitude, final double radius, final GeoUnit unit, final GeoRadiusParam param) {
		return execute(new JedisCallback<List<GeoRadiusResponse>>() {
			public List<GeoRadiusResponse> doInJedis(Jedis jedis) {
				return jedis.georadius(getPrefix() + key, longitude,latitude,radius,unit,param);
			}
		});
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius, final GeoUnit unit) {
		return execute(new JedisCallback<List<GeoRadiusResponse>>() {
			public List<GeoRadiusResponse> doInJedis(Jedis jedis) {
				return jedis.georadiusByMember(getPrefix() + key, member,radius,unit);
			}
		});
	}

	@Override
	public List<GeoRadiusResponse> georadiusByMember(final String key, final String member, final double radius, final GeoUnit unit, final GeoRadiusParam param) {
		return execute(new JedisCallback<List<GeoRadiusResponse>>() {
			public List<GeoRadiusResponse> doInJedis(Jedis jedis) {
				return jedis.georadiusByMember(getPrefix() + key, member,radius,unit,param);
			}
		});
	}

	public Long del(final String... keys) {
		return execute(new JedisCallback<Long>() {
			public Long doInJedis(Jedis jedis) {
				String[] prefixKeys = new String[keys.length];
				for(int i = 0;i < keys.length ;i++){
					prefixKeys[i] = getPrefix() + keys[i];
				}
				return jedis.del(prefixKeys);
			}
		});
	}
	
	public Set<String> keys(final String pattern) {
		return execute(new JedisCallback<Set<String>>() {
			public Set<String> doInJedis(Jedis jedis) {
				return jedis.keys(getPrefix() + pattern);
			}
		});
	}
}
