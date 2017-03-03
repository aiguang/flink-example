package com.cmss.www.operatorEx;

import com.cmss.www.utils.DataUtil;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public class MaxMinEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        List<Tuple2<String, Integer>> list = DataUtil.createTuple2(10);
        for (Tuple2<String, Integer> t : list) {
            System.out.println("key:" + t.f0 + ",value:" + t.f1);
        }
        DataStream<Tuple2<String, Integer>> maxStream = env.fromCollection(list).keyBy(0).max(1);
        DataStream<Tuple2<String, Integer>> minStream = env.fromCollection(list).keyBy(0).min(1);

        maxStream.print();
        minStream.print();
        env.execute("max/min example");

    }

}
