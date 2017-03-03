package com.cmss.www
        ;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by Administrator on 2017/2/21.
 */
public class Main {
        public static void main(String[] args){
                final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
                DataStream<Tuple2<String, Integer>> dataStream = env.socketTextStream("localhost",9999).map(
                        new MapFunction<String, Tuple2<String, Integer>>() {
                                public Tuple2<String, Integer> map(String s) throws Exception {
                                        return new Tuple2<String, Integer>(s, 1);
                                }
                        }
                ).keyBy(0).sum(1);
                dataStream.print();
                try {
                        env.execute();
                } catch (Exception e) {
                        e.printStackTrace();
                }


        }
}
