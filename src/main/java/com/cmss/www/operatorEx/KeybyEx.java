package com.cmss.www.operatorEx;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by Administrator on 2017/2/22.
 */
public class KeybyEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> stringDataStream = environment.socketTextStream("localhost",9999).map(
                new MapFunction<String, Tuple2<String, Integer>>() {
                    public Tuple2<String, Integer> map(String s) throws Exception {
                        return new Tuple2<String, Integer>(s, 1);
                    }
                }
        ).keyBy(0).sum("f1");
        stringDataStream.print();
        environment.execute();
    }
}
