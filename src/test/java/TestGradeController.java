import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TestGradeController {

    private Logger log = LogManager.getLogger();
    private String url = "http://localhost:2333";

    @Test
    public void exeLogin() {
        String type = "99";
        String loginName = "sy";
        String loginPass = "lg";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("type", type);
        params.add("loginName", loginName);
        params.add("loginPass", loginPass);

        url = url + "/Login/exeLogin.do";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForObject(url, params, String.class));
    }

    @Test
    public void getGradeListByNameUser() {
        int page = 1;
        int limit = 1000;
        String searchGradeName = "";
        String token = "15c14518-3842-4f87-94f3-7376e7d66317";

        url = url + "/Grade/getGradeListByNameUser.do" +
                "?page="+page +
                "&limit=" + limit +
                "&token=" + token ;
        log.info(url);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.getForObject(url, String.class));
    }
}
