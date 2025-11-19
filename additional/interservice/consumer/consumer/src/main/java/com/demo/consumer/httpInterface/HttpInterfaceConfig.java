package com.demo.consumer.httpInterface;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class HttpInterfaceConfig {
    //The below lines are not required because this bean is already defined in restTemplate package in RestTEmplateConfig so spring takes the bean from there and does not create a duplicate bean.But if you are performing it for first time then you should define a bean for RestTEmplate
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate()
//    {
//        return new RestTemplate();
//    }

//    @Bean
//    public ProviderHttpInterface webClientHttpInterface(RestTemplate restTemplate)
//    {
//        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://provider"));
//        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
//        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
//
//        ProviderHttpInterface service = factory.createClient(ProviderHttpInterface.class);
//        return service;
//    }

    //The below lines are not required because this bean is already defined in restClient package in RestTEmplateConfig so spring takes the bean from there and does not create a duplicate bean.But if you are performing it for first time then you should define a bean for RestClient
//
//    @Bean
//    @LoadBalanced
//    public RestClient.Builder restClientBuilder()
//    {
//        return RestClient.builder();
//    }

//    @Bean
//    public ProviderHttpInterface restClientHttpInterface(RestClient.Builder restClientBuilder)
//    {
//        RestClient restClient=restClientBuilder.baseUrl("http://provider").build();
//        RestClientAdapter adapter=RestClientAdapter.create(restClient);
//        HttpServiceProxyFactory factory=HttpServiceProxyFactory.builderFor(adapter).build();
//        ProviderHttpInterface service=factory.createClient(ProviderHttpInterface.class);
//        return service;
//    }

    //The below lines are not required because this bean is already defined in webclient package in RestTEmplateConfig so spring takes the bean from there and does not create a duplicate bean.But if you are performing it for first time then you should define a bean for WebClient

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder webClientBuilder()
//    {
//        return WebClient.builder();
//    }

    @Bean
    public ProviderHttpInterface webClientHttpInterface(WebClient.Builder webClientBuilder)
    {
        WebClient webClient=webClientBuilder.baseUrl("http://provider").build();
        WebClientAdapter adapter=WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory=HttpServiceProxyFactory.builderFor(adapter).build();
        ProviderHttpInterface service=factory.createClient(ProviderHttpInterface.class);
        return service;
    }
}
