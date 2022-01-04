package com.kratonsolution.belian.access.module.router;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class ModuleRouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> getAllModules(ModuleHandler moduleHandler) {

        return RouterFunctions
                .route(GET("/api/v2/access-module/all"), moduleHandler::getAllModules)
                .andRoute(GET("/api/v2/access-module/paging/{page}/{size}"), moduleHandler::filter)
                .andRoute(POST("/api/v2/access-module/create"), moduleHandler::create);
    }

    @Bean("reactiveTransactionManager")
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}