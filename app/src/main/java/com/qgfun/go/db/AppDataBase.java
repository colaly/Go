package com.qgfun.go.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author LLY
 */
@Database(name = AppDataBase.NAME, version = AppDataBase.VERSION)
public class AppDataBase {
    public static final String NAME = "GoPlay";
    public static final int VERSION = 1;
}
