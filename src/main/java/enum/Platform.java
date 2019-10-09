package enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Platform {

    ANDROID("android"),
    IOS("ios");

    private String platformName;


    private Platform(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformName() {
        return platformName;
    }

    private static final Map<String, Platform> lookup = new HashMap<>();
    static {
        for(Platform p: EnumSet.allOf(Platform.class)) {
            lookup.put(p.platformName, p);
        }
    }

    public static Platform get(final String platformName) {
        if(!lookup.containsKey(platformName)) {
            throw new IllegalArgumentException(String.format("Platform  '%s' undefined", platformName));
        }
        return lookup.get(platformName);
    }

}