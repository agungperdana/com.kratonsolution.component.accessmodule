package com.kratonsolution.belian.access.module.router;

import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.api.application.ModuleCreateCommand;
import com.kratonsolution.belian.access.module.api.application.ModuleFilter;
import com.kratonsolution.belian.access.module.api.application.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class ModuleHandler {

    @Autowired
    private ModuleService moduleService;

    Mono<ServerResponse> create(ServerRequest request) {

//        .onErrorResume(e->{
//            Map<String, Object> map = new HashMap<>();
//            map.put("status", "FAIL");
//            map.put("message", "Module creation failed");
//            return Mono.just(map);
//        })

        return ServerResponse.ok().body(
                    moduleService.create(request.bodyToMono(ModuleCreateCommand.class))
                                    .flatMap(data -> {

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("status", "SUCCESS");
                                        map.put("module", data);
                                        return Mono.just(map);
                                    }), Map.class);
    }
//
//    Mono<ServerResponse> update(ServerRequest request) {
//
//    }
//
//    Mono<ServerResponse> delete(ServerRequest request) {
//
//    }
//
    Mono<ServerResponse> getByCode(ServerRequest request) {

        return ServerResponse.ok().body(moduleService.getByCode(Mono.just(request.pathVariable("code"))), ModuleData.class);
    }

    Mono<ServerResponse> getAllModules(ServerRequest request) {
        return ServerResponse.ok().body(moduleService.getAll().onErrorResume(e-> Flux.empty()), ModuleData.class);
    }

    Mono<ServerResponse> filter(ServerRequest request) {
        return ServerResponse.ok().body(moduleService.filter(request.bodyToMono(ModuleFilter.class)), ModuleData.class);
    }
}
