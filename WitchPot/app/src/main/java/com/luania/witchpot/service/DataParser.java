package com.luania.witchpot.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luania.witchpot.pojo.SegmentPojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luania on 16/6/28.
 */
public class DataParser {
    public static List<Map.Entry<String, SegmentPojo>> toXsList(String json){
        Map<String, SegmentPojo> map = new Gson().fromJson(json, new TypeToken<Map<String, SegmentPojo>>() {}.getType());
        return new ArrayList<>(map.entrySet());
    }
}
