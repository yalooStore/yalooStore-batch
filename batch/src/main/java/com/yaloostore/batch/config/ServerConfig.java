package com.yaloostore.batch.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class ServerConfig {


    @Value("${yalooStor.shop}")
    private String shopUrl;

    @Value("${yalooStor.fron}")
    private String frontUrl;
    @Value("${yalooStor.gateway}")
    private String gatewayUrl;

}
