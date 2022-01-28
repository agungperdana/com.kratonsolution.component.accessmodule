package com.kratonsolution.belian.access.module.impl.model;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 * @since 2.0.0
 */

@Data
@Getter
@Builder
@ToString
@AllArgsConstructor
@Table("access_module")
public class Module
{
    @Id
    private String id;

    @Setter
    private String code;
    
    @Setter
    private String name;
    
    @Setter
    private String moduleGroup;
    
    @Setter
    private String note;

    @Setter
    private boolean enabled;

    @Setter
    @CreatedBy
    private String createdBy;

    @Setter
    @CreatedDate
    private Instant createdDate;

    @Setter
    @LastModifiedBy
    private String lastUpdatedBy;

    @Setter
    @LastModifiedDate
    private Instant lastUpdatedDate;

    @Version
    private Long version;

    Module(){
    }

    public Module(@NonNull String code, @NonNull String name, @NonNull String moduleGroup) {
        
        this(code, name, moduleGroup, null, false);
    }
    
    public Module(@NonNull String code, @NonNull String name, @NonNull String moduleGroup, String note, boolean enabled) {
        
        this.code = code;
        this.name = name;
        this.moduleGroup = moduleGroup;
        this.note = note;
        this.enabled = enabled;
    }
}
