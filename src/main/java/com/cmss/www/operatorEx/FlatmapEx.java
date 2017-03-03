package com.cmss.www.operatorEx;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * Created by Administrator on 2017/2/22.
 */
public class FlatmapEx {
    public static void main(String[] args){
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> dataStream = environment.socketTextStream("localhost",9999).flatMap(
                new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                        for(String sub : s.split(","))
                        collector.collect(new Tuple2<String, Integer>(sub, 1));
                    }
                }
        );
        dataStream.print();
        try {
            environment.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
