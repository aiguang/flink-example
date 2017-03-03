package com.cmss.www.sourceEx;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/2/22.
 */
public class CollectionEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        List<String> list = new ArrayList<String>();
        list.add(1 + "");
        list.add(2 + "");
        DataStream<String> dataStream = environment.fromCollection(list).map(
                new MapFunction<String, String>() {
                    public String map(String s) throws Exception {
                        return s;
                    }
                }
        );
        dataStream.print();
        environment.execute();
    }
}
