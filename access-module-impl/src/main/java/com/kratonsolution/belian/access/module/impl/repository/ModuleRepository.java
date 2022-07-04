package com.kratonsolution.belian.access.module.impl.repository;

import com.kratonsolution.belian.access.module.api.ModuleData;
import com.kratonsolution.belian.access.module.impl.entity.R2DBCModuleEntity;

import lombok.NonNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 * @since 2.0
 */
public interface ModuleRepository extends R2dbcRepository<R2DBCModuleEntity, String> {

    public Mono<R2DBCModuleEntity> findOneByCode(@NonNull String code);

    @Query("SELECT * FROM access_module")
    public Flux<ModuleData> getAll();

    @Query("SELECT * FROM access_module mdl ORDER BY mdl.code ASC LIMIT ?2 OFFSET ?1")
    public Flux<ModuleData> getAll(int offset, int limit);

    @Query("SELECT * FROM access_module mdl " +
            "WHERE mdl.code LIKE ?1 OR module.name LIKE ?1 " +
            "ORDER BY mdl.code, mdl.name ASC")
    public Flux<ModuleData> filter(@NonNull String key);

    @Query("SELECT * FROM access_module mdl " +
            "WHERE mdl.code LIKE ?1 OR module.name LIKE ?1 " +
            "ORDER BY module.code ASC LIMIT ?3 OFFSET ?2")
    public Flux<ModuleData> filter(@NonNull String key, int offset, int limit);

    @Query("SELECT COUNT(*) FROM access_module module WHERE module.code LIKE ?1 OR module.name LIKE ?1")
    public Mono<Long> count(@NonNull String key);
}
