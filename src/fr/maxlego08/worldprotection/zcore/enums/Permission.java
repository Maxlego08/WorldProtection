package fr.maxlego08.worldprotection.zcore.enums;

public enum Permission {

    WORLDPROTECTION_USE,
    WORLDPROTECTION_CREATE,
    WORLDPROTECTION_RELOAD,

    ;

    private final String permission;

    Permission() {
        this.permission = this.name().toLowerCase().replace("_", ".");
    }

    public String getPermission() {
        return permission;
    }

}
