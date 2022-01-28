package com.kratonsolution.belian.access.module.impl.repository;

import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.impl.model.Module;

import lombok.NonNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com 
 */
public interface ModuleRepository extends R2dbcRepository<Module, String> {

    public Mono<Module> findOneByCode(@NonNull String code);

    @Query("SELECT * FROM access_module module WHERE module.code = :code ")
    public Mono<ModuleData> getOne(String code);

    @Query("SELECT * FROM access_module ORDER BY code, name module_group ASC")
    public Flux<ModuleData> loadAllModule();

    @Query("SELECT * FROM access_module ORDER BY code, name ASC LIMIT :limit OFFSET :offset ")
    public Flux<ModuleData> loadAllModule(int limit, int offset);

    @Query("SELECT * FROM access_module WHERE " +
            "code LIKE :key OR name LIKE :key OR module_group LIKE :key " +
            "ORDER BY code, name, module_group ASC")
    public Flux<ModuleData> loadAllModule(@NonNull String key);

    @Query("SELECT * FROM access_module WHERE code LIKE :key " +
            "OR name LIKE :key OR module_group LIKE :key ORDER BY code, name, module_group ASC LIMIT :limit OFFSET :offset ")
    public Flux<ModuleData> loadAllModule(@NonNull String key, int offset, int limit);

    @Query("SELECT COUNT(*) FROM access_module WHERE code LIKE :key OR name LIKE :key OR module_group LIKE :key")
    public Mono<Long> count(@NonNull String key);
}
