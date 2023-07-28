package com.yaloostore.batch.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
public class ServerConfig {


    @Value("${yalooStore.shop}")
    private String shopUrl;

    @Value("${yalooStore.front}")
    private String frontUrl;
    @Value("${yalooStore.gateway}")
    private String gatewayUrl;

}
