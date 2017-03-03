package com.cmss.www.operatorEx;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
public class UnionEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        List<String> list = new ArrayList<String>();
        list.add("aiguang");
        list.add("uu");
        list.add("xdr");
        DataStream<String> stream1 = env.socketTextStream("localhost", 9999);
        DataStream<String> stream2 = env.fromCollection(list);
        DataStream<String> unionStream = stream1.union(stream2);
        unionStream.print();
        env.execute();

    }
}
