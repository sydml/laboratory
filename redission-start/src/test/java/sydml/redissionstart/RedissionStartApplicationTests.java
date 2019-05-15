package sydml.redissionstart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissionStartApplicationTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void contextLoads() {
        RLock anyLock = redissonClient.getLock("anyLock");
        anyLock.lock();
        RLock faiLock = redissonClient.getFairLock("faiLock");
        faiLock.lock();

    }

}
