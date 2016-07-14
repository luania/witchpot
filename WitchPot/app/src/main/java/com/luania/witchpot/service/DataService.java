package com.luania.witchpot.service;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.luania.witchpot.listeners.DataCompletionListener;
import com.luania.witchpot.listeners.DataListener;
import com.luania.witchpot.pojo.SegmentPojo;
import com.luania.witchpot.util.ACache;
import com.wilddog.client.ValueEventListener;
import com.wilddog.client.Wilddog;

import java.util.List;
import java.util.Map;

/**
 * Created by luania on 16/6/24.
 */
public class DataService {
    public static final String WILDDOG = "https://witchpot.wilddogio.com/";
    public static final String WILDDOG_SEGMENT = "https://witchpot.wilddogio.com/segments";

    /*
    清空数据
     */
    public static void clearRoot(Wilddog.CompletionListener completionListener) {
        new Wilddog(WILDDOG).removeValue(completionListener);
    }


    public static void getAllSegmentList(ValueEventListener valueEventListener) {
        new Wilddog(WILDDOG_SEGMENT).addListenerForSingleValueEvent(valueEventListener);
    }

    /*
    获取子片段列表
     */
    public static void getChildList(Context context, String pid, final DataListener dataListener) {
        final ACache aCache = ACache.get(context);
        final Gson gson = new Gson();
        new Wilddog(WILDDOG_SEGMENT)
                .orderByChild("pid")
                .equalTo(pid)
                .addListenerForSingleValueEvent(new DataListener(context) {
                    @Override
                    public void onJsonGetted(String json) {
                        dataListener.onJsonGetted(json);
                        final List<Map.Entry<String, SegmentPojo>> segmentPojos = DataParser.toXsList(json);
                        for (Map.Entry<String, SegmentPojo> segmentPojo : segmentPojos) {
                            aCache.put(segmentPojo.getKey(),gson.toJson(segmentPojo.getValue()));
                        }
                    }

                    @Override
                    public void onFinally() {
                        dataListener.onFinally();
                    }
                });
    }

    /*
    根据id获取片段
     */
    public static void getSegment(final Context context, final String id, final DataListener dataListener) {
        if(getDataFromCache(id,context,dataListener)){
            return ;
        }
        new Wilddog(WILDDOG_SEGMENT).child(id).addListenerForSingleValueEvent(new DataListener(context) {
            @Override
            public void onJsonGetted(String json) {
                final ACache aCache = ACache.get(context);
                aCache.put(id,json);
            }

            @Override
            public void onFinally() {
                dataListener.onFinally();
            }
        });
    }

    /*
    新建一个片段
     */
    public static void createSegment(final Context context, final SegmentPojo segmentPojo, final DataCompletionListener dataCompletionListener) {
        final Wilddog pushWilddog = new Wilddog(WILDDOG_SEGMENT).push();
        segmentPojo.setId(pushWilddog.getKey());
        pushWilddog.setValue(segmentPojo, new DataCompletionListener(context) {
            @Override
            public void onSuccess(Wilddog wilddog) {
                dataCompletionListener.onSuccess(wilddog);
                ACache.get(context).put(segmentPojo.getId(),new Gson().toJson(segmentPojo));
            }

            @Override
            public void onFinally() {
                dataCompletionListener.onFinally();
            }
        });
    }

    /*
    根据key从缓存获取数据
     */
    private static boolean getDataFromCache(String key,Context context,final DataListener dataListener){
        final ACache aCache = ACache.get(context);
        String json = aCache.getAsString(key);
        if (!TextUtils.isEmpty(json)) {
            dataListener.onJsonGetted(json);
            dataListener.onFinally();
            return true;
        }
        return false;
    }
}
