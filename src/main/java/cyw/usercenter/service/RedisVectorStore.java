package cyw.usercenter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RedisVectorStore {

    @Value("${spring.redis.host:localhost}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private int port;

    @Value("${spring.redis.password:}")
    private static String password;

    private static JedisPool jedisPool;

    @PostConstruct
    public void init() {
        // 初始化连接池
        this.jedisPool = new JedisPool(host, port);
    }

    /**
     * 将向量存入 Redis
     * @param noteId 帖子ID，作为 Key 的一部分
     * @param vector 1024维特征向量
     */
    public static void saveVector(int noteId, float[] vector) {
        if (vector == null || vector.length == 0) return;

        try (Jedis jedis = jedisPool.getResource()) {
            if (password != null && !password.isEmpty()) {
                jedis.auth(password);
            }

            // 1. 定义 Key
            byte[] key = ("note:" + noteId).getBytes();

            // 2. 构造 Map，注意：Key 和 Value 都要是 byte[]
            Map<byte[], byte[]> data = new HashMap<>();

            // 存储 noteId
            data.put("noteId".getBytes(), String.valueOf(noteId).getBytes());

            // 3. 将 float[] 转换为 byte[]
            ByteBuffer buffer = ByteBuffer.allocate(vector.length * 4).order(ByteOrder.LITTLE_ENDIAN);
            for (float v : vector) {
                buffer.putFloat(v);
            }
            data.put("note_vector".getBytes(), buffer.array());

            // 4. 调用 hset (新版本 Jedis 支持传入 byte[] key 和 Map<byte[], byte[]>)
            jedis.hset(key, data);
            System.out.println("写入完成，当前DB所有Key: " + jedis.keys("*").toString());

            System.out.println("成功存入 Redis 向量，ID: " + noteId);
        } catch (Exception e) {
            log.error("Redis 向量存储失败: {}", e.getMessage());
        }
    }
}