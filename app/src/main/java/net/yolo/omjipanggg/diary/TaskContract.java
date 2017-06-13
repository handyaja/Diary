package net.yolo.omjipanggg.diary;

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "net.yolo.omjipanggg.diary";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "diary";

    public class Columns {
        public static final String DIARY = "diary";
        public static final String _ID = BaseColumns._ID;
    }
}
