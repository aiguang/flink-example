package com.cmss.www.operatorEx;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**

 */
public class MapEx {
    public static void main(String[] args){
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> dataStream = environment.socketTextStream("localhost",9999).map(
                new MapFunction<String, Tuple2<String, Integer>>() {
                    public Tuple2<String, Integer> map(String s) throws Exception {
                        return new Tuple2<String, Integer>(s, 1);
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
