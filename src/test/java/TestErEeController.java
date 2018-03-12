import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author lizhenqing
 * @version 1.0
 * @data 2018/3/12 15:28
 */
public class TestErEeController {

    private Logger log = LogManager.getLogger();
    private String url = "http://localhost:2333";

    @Test
    public void requestEeEr() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("operate", "eeRequest");
        params.add("targetId", "123");
        params.add("token", "5691c266-33e6-4cb3-8ed4-3deca6e6d3ee");

        url = url + "/ErEe/requestEeEr.do";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForObject(url, params, String.class));
    }

    @Test
    public void removeEeEr() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("operate", "eeRemove");
        params.add("erEeId", "db80ddde-9284-43b5-b01d-8f5c6444df10");
        params.add("token", "5691c266-33e6-4cb3-8ed4-3deca6e6d3ee");

        url = url + "/ErEe/removeEeEr.do";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForObject(url, params, String.class));
    }

}
