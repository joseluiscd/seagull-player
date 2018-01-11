package io.github.joseluiscd.seagull.util;

/**
 * Created by joseluis on 30/11/17.
 */

public class Result<Ok> {
    Ok result;
    Exception error;

    public Result(Ok ok){
        result = ok;
    }

    public Result(Exception err){
        error = err;
    }
}
