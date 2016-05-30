package com.around.around.activitys;

import com.around.around.model.People;

import java.util.ArrayList;

/**
 * Created by IagoBelo on 28/05/2016.
 */
public interface IOnResult {
    void onResult(ArrayList<People> peoples);
    void onFailed(String msg);
}
