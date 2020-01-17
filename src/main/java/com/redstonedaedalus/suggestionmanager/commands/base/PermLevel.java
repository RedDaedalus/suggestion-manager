package com.redstonedaedalus.suggestionmanager.commands.base;

public enum PermLevel {
    EVERYONE("471106238923538454", "Server Member", 0, 0),
    DF_ADMIN("531253247323799612", "DiamondFire Administrator", 1, 0xFF0049),
    MOD("528943220235960321", "Moderation", 2, 0x4C92FF),
    DEV("526182228229619713", "Developer", 3, 0xDD00FF);

    private final String role;
    private final String name;
    private final int level;
    private final int color;

    PermLevel(String role, String name, int level, int color) {
        this.role = role;
        this.name = name;
        this.level = level;
        this.color = color;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getColor() {
        return color;
    }
}
