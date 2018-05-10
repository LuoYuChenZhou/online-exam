package com.lycz.configAndDesign;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JedisUtil {
    private static Integer MAX_TOTAL = null;
    private static Integer MAX_IDLE = null;
    private static Integer MIN_IDLE = null;
    private static Long MAX_WAIT_MILLIS = null;
    private static Integer TIMEOUT = null;
    private static String REDIS_IP = null;
    private static Integer REDIS_PORT = null;

    private static Logger logger = LogManager.getLogger(JedisUtil.class.getName());
    private static String VIRTUAL_COURSE_PRE = null;

    //获取配置文件中的参数
    static {
        Properties prop = new Properties();
        //百度上使用getResourceAsStream会导致配置文件修改后不能重新读取而此方法可以，未实验
        String path = JedisUtil.class.getResource("/config/redisPool.properties").getPath();
        try {
            InputStream inStream = new FileInputStream(path);
            prop.load(inStream);
            MAX_TOTAL = Integer.valueOf(prop.getProperty("MAX_TOTAL"));
            MAX_IDLE = Integer.valueOf(prop.getProperty("MAX_IDLE"));
            MIN_IDLE = Integer.valueOf(prop.getProperty("MIN_IDLE"));
            MAX_WAIT_MILLIS = Long.valueOf(prop.getProperty("MAX_WAIT_MILLIS"));
            TIMEOUT = Integer.valueOf(prop.getProperty("TIMEOUT"));
            REDIS_IP = prop.getProperty("REDIS_IP");
            REDIS_PORT = Integer.valueOf(prop.getProperty("REDIS_PORT"));
            VIRTUAL_COURSE_PRE = prop.getProperty("VIRTUAL_COURSE_PRE");
        } catch (IOException e) {
            logger.error("redisPool配置读取失败！");
            e.printStackTrace();
        }
    }

    private JedisUtil() {
    }

    private static Map<String, JedisPool> maps = new HashMap<>();

    private static JedisPool getPool(String ip, int port) {
        String key = ip + ":" + port;
        JedisPool pool;
        if (!maps.containsKey(key)) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_TOTAL);
            config.setMaxIdle(MAX_IDLE);
            config.setMinIdle(MIN_IDLE);
            config.setMaxWaitMillis(MAX_WAIT_MILLIS);
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            pool = new JedisPool(config, ip, port, TIMEOUT);
            maps.put(key, pool);
        } else {
            pool = maps.get(key);
        }
        return pool;
    }

    //保持public，可能外部会用到
    public static Jedis getJedis(String ip, Integer port) {
        ip = ToolUtil.isEmpty(ip) ? REDIS_IP : ip;
        port = port == null ? REDIS_PORT : port;
        Jedis jedis = null;
        try {
            jedis = getPool(ip, port).getResource();
        } catch (JedisConnectionException e) {
            String message = ToolUtil.trim(e.getMessage());
            if ("Could not get a resource from the pool".equalsIgnoreCase(message)) {
                logger.warn("++++++++++请检查你的redis服务++++++++");
                logger.warn("|①.请检查是否安装redis服务");
                logger.warn("|②.请检查redis 服务是否启动");
                logger.warn("|③.请检查redis启动是否带配置文件启动，也就是是否有密码，是否端口有变化（默认6379）。如果需要配置密码和改变端口，请修改spring-cache.xml配置。|");
                //System.exit(0);//停止项目
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jedis;
    }

    public static Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = getPool(REDIS_IP, REDIS_PORT).getResource();
        } catch (JedisConnectionException e) {
            String message = ToolUtil.trim(e.getMessage());
            if ("Could not get a resource from the pool".equalsIgnoreCase(message)) {
                logger.warn("++++++++++请检查你的redis服务++++++++");
                logger.warn("|①.请检查是否安装redis服务");
                logger.warn("|②.请检查redis 服务是否启动");
                logger.warn("|③.请检查redis启动是否带配置文件启动，也就是是否有密码，是否端口有变化（默认6379）。如果需要配置密码和改变端口，请修改spring-cache.xml配置。|");
                //System.exit(0);//停止项目
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jedis;
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 得到Key
     */
    private static String buildKey(String key) {
        if (ToolUtil.isEmpty(key)) {
            logger.error("key不能为空！");
            throw new RuntimeException("key不能为空！");
        }
        return VIRTUAL_COURSE_PRE + key;
    }

    /**
     * 存入 String
     */
    public static void setString(String key, String value) {
        Jedis jedis = getJedis();
        try {
            value = ToolUtil.isEmpty(value) ? "" : value;
            jedis.set(buildKey(key), value);
        } catch (Exception e) {
            logger.error("缓存存入字符串错误，键：【{}】，值：【{}】，错误详情： {}: ", key, value, e.getMessage());
            throw new RuntimeException("缓存存入字符串错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 存入 过期时间
     *
     * @param seconds 以秒为单位
     */
    public static void setString(String key, int seconds, String value) {
        Jedis jedis = getJedis();
        try {
            value = ToolUtil.isEmpty(value) ? "" : value;
            jedis.setex(buildKey(key), seconds, value);
        } catch (Exception e) {
            logger.error("Set keyex error : " + e);
            throw new RuntimeException("Set keyex error");
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取String值
     *
     * @return value
     */
    public static String getString(String key) {
        Jedis jedis = getJedis();
        try {
            String bKey = buildKey(key);
            if (jedis == null || !jedis.exists(bKey)) {
                return null;
            }
            return jedis.get(bKey);
        } catch (Exception e) {
            logger.error("缓存获取字符串错误，键：【{}】，，错误详情： {}: ", key, e.getMessage());
            throw new RuntimeException("缓存获取字符串错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 存入 list
     */
    public static <T> void setList(String key, List<T> list) {
        Jedis jedis = getJedis();
        try {
            jedis.set(buildKey(key).getBytes(), ObjectTranscoder.serialize(list));
        } catch (Exception e) {
            logger.error("缓存存入列表错误，键：【{}】，值：【{}】，错误详情： {}: ", key, list.toString(), e.getMessage());
            throw new RuntimeException("缓存存入列表错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取list
     *
     * @return list
     */
    public static <T> List<T> getList(String key) {
        Jedis jedis = getJedis();
        try {
            byte[] bKey = buildKey(key).getBytes();
            if (jedis == null || !jedis.exists(bKey)) {
                return null;
            }
            byte[] in = getJedis().get(bKey);
            List<T> list = (List<T>) ObjectTranscoder.deserialize(in);
            return list;
        } catch (Exception e) {
            logger.error("缓存获取列表错误，键：【{}】，错误详情： {}: ", key, e.getMessage());
            throw new RuntimeException("缓存获取列表错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 存入 map
     */
    public static <T> void setMap(String key, Map<String, T> map) {
        Jedis jedis = getJedis();
        try {
            jedis.set(buildKey(key).getBytes(), ObjectTranscoder.serialize(map));
        } catch (Exception e) {
            logger.error("缓存存入map错误，键：【{}】，值：【{}】，错误详情： {}: ", key, map.toString(), e.getMessage());
            throw new RuntimeException("缓存存入map错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * 获取map
     *
     * @return map
     */
    public static <T> Map<String, T> getMap(String key) {
        Jedis jedis = getJedis();
        try {
            byte[] bKey = buildKey(key).getBytes();
            if (jedis == null || !jedis.exists(bKey)) {
                return null;
            }
            byte[] in = getJedis().get(bKey);
            Map<String, T> map = (Map<String, T>) ObjectTranscoder.deserialize(in);
            return map;
        } catch (Exception e) {
            logger.error("缓存获取map错误，键：【{}】，错误详情： {}: ", key, e.getMessage());
            throw new RuntimeException("缓存获取map错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }

    public static void delKey(String key) {
        Jedis jedis = getJedis();
        try {
            jedis.del(VIRTUAL_COURSE_PRE + key);
        } catch (Exception e) {
            logger.error("缓存删除操作发生错误，键：【{}】，错误详情： {}: ", key, e.getMessage());
            throw new RuntimeException("缓存删除操作发生错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }

    public static void setOutTime(String key, Integer time) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(VIRTUAL_COURSE_PRE + key, time);
        } catch (Exception e) {
            logger.error("设置缓存过期时间发生错误，键：【{}】，时间：【{}】，错误详情： {}: ", key, time, e.getMessage());
            throw new RuntimeException("设置缓存过期时间发生错误,详情见日志");
        } finally {
            closeJedis(jedis);
        }
    }
}
