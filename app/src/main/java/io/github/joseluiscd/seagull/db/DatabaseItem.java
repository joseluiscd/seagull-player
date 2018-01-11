package io.github.joseluiscd.seagull.db;

import android.database.Cursor;

/**
 * Created by joseluis on 9/12/17.
 */

public interface DatabaseItem {
    void readFromCursor(Cursor c);
}
