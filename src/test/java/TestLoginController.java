import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class TestLoginController {

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

}
