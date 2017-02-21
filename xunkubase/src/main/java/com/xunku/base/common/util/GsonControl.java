package com.xunku.base.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2016/6/15.
 */
public class GsonControl {

    public static <T> T getPerson(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<ArrayList<T>>() {
            }.getType());
        } catch (Exception e) {
        }
        return list;
    }

//    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        try {
//            Gson gson = new Gson();
//            list = gson.fromJson(jsonString,
//                    new TypeToken<List<Map<String, Object>>>() {
//                    }.getType());
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//        return list;
//    }
}
