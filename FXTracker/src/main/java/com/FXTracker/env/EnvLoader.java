package com.FXTracker.env;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {

    private static final Dotenv DOT_ENV = Dotenv.configure().ignoreIfMissing().load();

    public static String get(String key) {

        return DOT_ENV.get(key);
    }
}
