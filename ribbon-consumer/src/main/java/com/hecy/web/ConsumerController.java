package com.hecy.web;

import com.hecy.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/ribbon", method = RequestMethod.GET)
    public String helloConsumer() {
        String s = restTemplate.getForObject("http://HELLOSERVER/hello", String.class);
        System.out.println("server body : " + s);
        return s;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public User getUser() {
        User user = restTemplate.getForObject("http://HELLOSERVER/user?name={1}", User.class, "hecy");
        System.out.println("server body : " + user);

        Map<String, String> param = new HashMap<>();
        param.put("name", "hecymap");
        User user2 = restTemplate.getForObject("http://HELLOSERVER/user?name={name}", User.class, param);
        System.out.println("server body : " + user2);

        return user2;
    }


    @RequestMapping(value = "/puser", method = RequestMethod.GET)
    public User getPUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", "e348bc22-5efa-4299-9142-529f07a18ac9");

        MultiValueMap<String, String> postParameters = new LinkedMultiValueMap<String, String>();
        postParameters.add("owner", "11");
        postParameters.add("subdomain", "aoa");
        postParameters.add("comment", "");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(postParameters, headers);

        Map<String, String> param = new HashMap<>();
        param.put("name", "hecymap");
        ResponseEntity<User> user2 = restTemplate.postForEntity("http://HELLOSERVER/puser/{1}", postParameters,User.class,"hecy");
        System.out.println("server body : " + user2);

        return user2.getBody();
    }


}
