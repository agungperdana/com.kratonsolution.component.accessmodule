package com.kratonsolution.belian.access.module.impl.domain;

import com.kratonsolution.belian.access.module.api.ModuleEntity;
import com.kratonsolution.belian.access.module.api.ModuleGroup;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Agung Dodi Perdana
 * @email agung.dodi.perdana@gmail.com
 * @since 2.0.0
 */
public class AccessModule
{
    private ModuleEntity entity;

    private AccessModule(){}

    private AccessModule(@NonNull ModuleEntity entity){
        this.entity = entity;
    }

    public static final AccessModule withEntity(@NonNull ModuleEntity entity) {

        return new AccessModule(entity);
    }

    public AccessModule changeNote(String note) {
        this.entity.setNote(note);
        return this;
    }

    public AccessModule changeActiveStatus(boolean newStatus) {

        if(entity.isEnabled() && !newStatus) {
            disable();
        }
        else if(!entity.isEnabled() && newStatus) {
            enable();
        }

        return this;
    }

    public void enable() {
        if(!this.entity.isEnabled()) {
            this.entity.setEnabled(true);
        }
    }

    public void disable() {
        if(this.entity.isEnabled()) {
            this.entity.setEnabled(false);
        }
    }
}
