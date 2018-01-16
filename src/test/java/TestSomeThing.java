import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/1/8 14:55
 */
public class TestSomeThing {
    private Logger log = LogManager.getLogger();
    
    @Test
    public void testStringNull() {
        Object a = null;
        log.info((String) a);
        log.info(a.equals("null"));
    }

    @Test
    public void testSubString() {
        String a = "abc.def.ghi.avi";
        log.info(a.substring(a.lastIndexOf(".") + 1));
    }

    @Test
    public void testEquals() {
        String a = null;
        String b = "";
        log.info(b.equals(a));
    }
}
