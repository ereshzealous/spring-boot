package com.eresh.spring.boot;

import com.eresh.spring.boot.config.BaseJacksonObjectMapper;
import com.eresh.spring.boot.config.BaseRetryListnerSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

/**
 * @author Gorantla, Eresh
 * @created 06-02-2019
 */
@Configuration
public class SpringCommonsConfiguration implements ServletContextInitializer, WebMvcConfigurer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

    }

    @Bean(name = "jsonMapper")
    @Primary
    public ObjectMapper jsonMapper() {
        return new BaseJacksonObjectMapper();
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClients.createDefault();
    }

    @Bean(name = "customRestTemplate")
    @Primary
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());

        //Find the index of Jackson2HttpMessageConverter index.
        OptionalInt index = IntStream.range(0,  restTemplate.getMessageConverters().size())
                .filter(i ->  restTemplate.getMessageConverters().get(i) instanceof MappingJackson2HttpMessageConverter)
                .findFirst();

        //Replace the HttpMessageConverter with custom one to use CtsJacksonObjectMapper.
        if(index.isPresent()) {
            restTemplate.getMessageConverters().set(index.getAsInt(), new MappingJackson2HttpMessageConverter(jsonMapper()));
        }
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        //restTemplate.setErrorHandler(new OutboundResponseErrorHandler());
        return restTemplate;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(jsonMapper()));
    }

    @Bean(name = "baseRetryTemplate")
    @Primary
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.registerListener(new BaseRetryListnerSupport());
        return retryTemplate;
    }
}
