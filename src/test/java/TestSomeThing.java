import com.lycz.configAndDesign.JedisUtil;
import com.lycz.configAndDesign.ToolUtil;
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
    public void testYu() {
        int a = 1;
        int b = 5;
        String c = "1111";
        log.warn(a&b);
        log.warn(b & (b-1));
        log.warn(Integer.toBinaryString(b));
        log.warn(Integer.toBinaryString(ToolUtil.getWrongOptionsValue(b)));
    }

    @Test
    public void testDestoryEmptyToken() {
        JedisUtil.delKey("123");
    }

    @Test
    public void testTimeOut() throws InterruptedException {
        JedisUtil.setString("111","abc");
        JedisUtil.setOutTime("111",5);
        log.info("_____________________");
        log.info(JedisUtil.getString("111"));
        log.info("_____________________");
        Thread.sleep(6000);
        log.info(JedisUtil.getString("111"));
        log.info("_____________________");
    }

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

    @Test
    public  void testSame() {
        String str1 = "设x=5,x-y=1。求y。后面都是12222222222222222222222222222222222222222";
        String str2 = "设z=5,z-m=1.求m。后面都是12222222222222222222222222222222222222222";
        System.out.println("ld=" + ld(str1, str2));
        System.out.println("sim=" + sim(str1, str2));
    }

    private static int min(int one, int two, int three) {
        int min = one;
        if (two < min) {
            min = two;
        }
        if (three < min) {
            min = three;
        }
        return min;
    }

    private static int ld(String str1, String str2) {
        int d[][]; // 矩阵
        int n = str1.length();
        int m = str2.length();
        int i; // 遍历str1的
        int j; // 遍历str2的
        char ch1; // str1的
        char ch2; // str2的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }
        for (i = 1; i <= n; i++) { // 遍历str1
            ch1 = str1.charAt(i - 1);
            // 去匹配str2
            for (j = 1; j <= m; j++) {
                ch2 = str2.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1]+ temp);
            }
        }
        return d[n][m];
    }
    private static double sim(String str1, String str2) {
        try {
            double ld = (double)ld(str1, str2);
            return (1-ld/(double)Math.max(str1.length(), str2.length()));
        } catch (Exception e) {
            return 0.1;
        }
    }
}
