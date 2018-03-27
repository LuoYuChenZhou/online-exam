import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TestGradeController {

    private Logger log = LogManager.getLogger();
    private String url = "http://localhost:2333";
    private String token = "17c98a2c-c869-4d6e-9903-fe08a82fb23c";

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
    public void addGrade() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("gradeName", "星辰q");
//        params.add("sortNo", "2");
        params.add("token", token);

        url = url + "/Grade/addGrade.do";
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.postForObject(url, params, String.class));
    }

    @Test
    public void getGradeListByNameUser() {
        int page = 1;
        int limit = 1;
        String searchGradeName = "";

        url = url + "/Grade/getGradeListByNameUser.do" +
                "?page="+page +
                "&limit=" + limit +
                "&token=" + token ;
        log.info(url);
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(restTemplate.getForObject(url, String.class));
    }
}
