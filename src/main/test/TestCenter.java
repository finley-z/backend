
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zyf
 * @date 2017/7/7
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:config/application-context.xml",
        "classpath:config/mybatis-config.xml"
})
@ActiveProfiles(value="dev")
public class TestCenter {


    @Test
    public void test(){

        int i=1;
    }

    @Test
    public void orderService(){

    }

}
