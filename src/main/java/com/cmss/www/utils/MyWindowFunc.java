package com.cmss.www.utils;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Created by Administrator on 2017/3/1.
 */
public class MyWindowFunc implements WindowFunction<Tuple3<String, Integer, String>, Tuple3<String, Integer, String>, Tuple, TimeWindow> {
    public void apply(Tuple tuple,
                      TimeWindow timeWindow,
                      Iterable<Tuple3<String, Integer, String>> iterable,
                      Collector<Tuple3<String, Integer, String>> collector) throws Exception {
        Integer count = 1;
        for(Tuple3<String, Integer, String> tup : iterable){
            count = count + Integer.parseInt(tup.getField(1).toString());
        }
        System.out.println("current filed is " + tuple.getField(0));
        collector.collect(new Tuple3<String, Integer, String>(tuple.getField(0).toString(), count, "window Func"));
    }
}
