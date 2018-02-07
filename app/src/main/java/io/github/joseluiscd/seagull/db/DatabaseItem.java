package io.github.joseluiscd.seagull.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Created by joseluis on 9/12/17.
 */

public interface DatabaseItem {
    void readFromCursor(Cursor c);
    void dumpToCursor(@NonNull ContentValues values);
}
