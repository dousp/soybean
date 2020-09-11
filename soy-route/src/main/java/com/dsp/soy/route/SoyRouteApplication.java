package com.dsp.soy.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class SoyRouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoyRouteApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @RestController
    @RequestMapping("/fallback")
    public static class FallbackController {

        private final RestTemplate restTemplate;

        @Autowired
        public FallbackController(RestTemplate restTemplate) {this.restTemplate = restTemplate;}

        @RequestMapping(value = "/echo/{serviceName}/{str}", method = RequestMethod.GET)
        public String echo(@PathVariable String serviceName, @PathVariable String str) {
            return restTemplate.getForObject("http://"+serviceName+"/echo/" + str, String.class);
        }

        @GetMapping("/wrong")
        public String info(){
            return "something wrong";
        }
    }
}
