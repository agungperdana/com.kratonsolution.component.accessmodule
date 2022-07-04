package com.kratonsolution.belian.access.module.impl.entity;

import com.kratonsolution.belian.access.module.api.ModuleEntity;
import com.kratonsolution.belian.access.module.api.ModuleGroup;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 * @since 2.0.0
 */

@Data
@ToString
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Table("access_module")
public class R2DBCModuleEntity implements ModuleEntity
{
    @Id
    private String id;

    @NonNull
    private String code;

    @NonNull
    private String name;

    @NonNull
    @Column("module_group")
    @Builder.Default
    private ModuleGroup group = ModuleGroup.SECURITY;

    @Setter
    private String note;

    @Builder.Default()
    private boolean enabled = true;

    @Version
    private Long version;

    public void changeActiveStatus(boolean newStatus) {

        if(isEnabled() && !newStatus) {
            disable();
        }
        else if(!isEnabled() && newStatus) {
            enable();
        }
    }

    public void enable() {
        if(!this.enabled) {
            this.enabled = true;
        }
    }

    public void disable() {
        if(this.enabled) {
            this.enabled = false;
        }
    }
}
