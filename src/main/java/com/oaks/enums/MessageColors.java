package com.oaks.enums;

public enum MessageColors {
    IOS_BLUE("#007aff"),
    IOS_GREEN("#34C759"),
    SUNSET_ORANGE("#FF5E3A"),
    SKY_BLUE("#5AC8FA"),
    LAVENDER("#AF52DE"),
    FLAMINGO_PINK("#FF2D55"),
    MINT_GREEN("#00C7BE"),
    GOLDEN_YELLOW("#FFCC00"),
    TURQUOISE("#40E0D0"),
    CORAL_RED("#FF6B6B"),
    PASTEL_PURPLE("#B28DFF"),
    DEEP_INDIGO("#5856D6"),
    FRESH_LIME("#BFFF00"),
    OCEAN_BLUE("#1E90FF"),
    WARM_BEIGE("#F5DEB3"),
    NIGHT_BLACK("#1C1C1E"),
    CLOUD_GRAY("#C7C7CC"),
    VIBRANT_CYAN("#00FFFF"),
    LEMON_YELLOW("#FFF700"),
    CLASSIC_RED("#FF3B30");


    public String hexCode;

    public boolean assigned = false;

    MessageColors(String hexCode) {
        this.hexCode = hexCode;
    }
}
