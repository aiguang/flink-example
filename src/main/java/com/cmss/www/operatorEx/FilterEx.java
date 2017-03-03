package com.cmss.www.operatorEx;

import com.cmss.www.type.CompositeType;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by Administrator on 2017/2/22.
 */
public class FilterEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple2<String, Integer>> compositeTypeDataStream = environment.socketTextStream("localhost",9999).filter(
                new FilterFunction<String>() {
                    public boolean filter(String s) throws Exception {
                        return !"aiguang".equals(s);
                    }
                }
        ).map(new MapFunction<String, Tuple2<String, Integer>>() {
            public Tuple2<String, Integer> map(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        });
        compositeTypeDataStream.print();
        environment.execute();
    }

}
