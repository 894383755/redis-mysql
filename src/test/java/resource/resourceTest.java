package resource;

import com.kd.xxhyf.RedisMysqlApplication;
import com.kd.xxhyf.entity.ompse.SysTableinfo;
import com.kd.xxhyf.mapper.SysTableInfoMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = RedisMysqlApplication.class)
@RunWith(SpringRunner.class)
public class resourceTest {

    @Resource
    private SysTableInfoMapper sysTableInfoMapper;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<SysTableinfo> userList = sysTableInfoMapper.selectList(null);
        Assert.assertNotEquals(0, userList.size());
    }
}
