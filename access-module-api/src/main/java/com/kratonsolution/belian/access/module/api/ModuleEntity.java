package com.kratonsolution.belian.access.module.api;

import lombok.NonNull;

public interface ModuleEntity {

    void setId(@NonNull String id);
    String getId();
    void setCode(@NonNull String code);
    String getCode();
    void setName(@NonNull String name);
    String getName();
    void setGroup(@NonNull ModuleGroup group);
    ModuleGroup getGroup();
    void setNote(@NonNull String note);
    String getNote();
    void setEnabled(boolean enabled);
    boolean isEnabled();
}
