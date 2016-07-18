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
    public static List<SegmentPojo> toSegmentList(String json){
        Map<String, SegmentPojo> map = new Gson().fromJson(json, new TypeToken<Map<String, SegmentPojo>>() {}.getType());
        ArrayList<SegmentPojo> result = new ArrayList<>();
        for (Map.Entry<String, SegmentPojo> stringSegmentPojoEntry : map.entrySet()) {
            result.add(stringSegmentPojoEntry.getValue());
        }
        return result;
    }
}
