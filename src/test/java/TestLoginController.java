import com.lycz.controller.common.JedisUtil;
import com.lycz.model.Examiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class TestLoginController {

    private Logger log = LogManager.getLogger();
    private String url = "http://localhost:2333";
    private String token = "";

    @Test
    public void exeLogin() {
        String type = "2";
        String loginName = "testl";
        String loginPass = "testp";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", type);
        params.add("loginName", loginName);
        params.add("loginPass", loginPass);

        url = url + "/Login/exeLogin";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForObject(url, params, String.class));
    }

    @Test
    public void tempTest() {
        String loginPass = "testp";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("registerType", loginPass);

        url = url + "/Login/tempTest";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForObject(url, params, String.class));
    }

    @Test
    public void testLog4j2() {
        Logger logger = LogManager.getLogger();
        logger.info("abc{}", 12);
        logger.info("abc{}:{}", 12, 23);
    }

    @Test
    public void testJedis() {
        String value1 = "value1";
        Map<String, String> value2 = new HashMap<>();
        List<String> value3 = new ArrayList<>();
        Set<String> value4 = new HashSet<>();
        value2.put("11", "11");
        value2.put("22", "22");
        value2.put("33", "33");
        value2.put("44", "44");


        JedisUtil.setString("key12", value1);
        JedisUtil.setMap("key12", value2);
        log.info("*******************************");
        log.info(JedisUtil.getMap("key12").get("11"));
        log.info("*******************************");
        JedisUtil.delKey("key12");
    }

    @Test
    public void testSubString() {
        String ooo = "abcdcef";
        log.trace(ooo.substring(ooo.lastIndexOf("c") + 1));
        log.info(ooo.substring(ooo.lastIndexOf("c") + 1));
        log.debug(ooo.substring(ooo.lastIndexOf("c") + 1));
        log.warn(ooo.substring(ooo.lastIndexOf("c") + 1));
        log.error(ooo.substring(ooo.lastIndexOf("c") + 1));
        log.fatal(ooo.substring(ooo.lastIndexOf("c") + 1));
    }

    @Test
    public void testGetName() {
        Examiner examiner = new Examiner();
        log.info(examiner.getClass().getName());
    }

    @Test
    public void eeRegister() {
        String registerType = "2";
        String loginName = "testl";
        String loginPass = "testp";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("registerType", registerType);
        params.add("loginName", loginName);
        params.add("loginPass", loginPass);

        url = url + "/Login/eeRegister";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForObject(url, params, String.class));
    }

    @Test
    public void userNameIsExist() {
        String regType = "2";
        String userName = "lll";

        url = url + "/Login/userNameIsExist" +
                "?regType="+regType +
                "&userName=" + userName;
        log.info(url);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.getForObject(url, String.class));
    }
}
