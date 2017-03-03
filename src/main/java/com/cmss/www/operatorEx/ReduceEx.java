package com.cmss.www.operatorEx;

import com.cmss.www.utils.DataUtil;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public class ReduceEx {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        List<Tuple2<String, Integer>> list = DataUtil.createTuple2(10);
        for(Tuple2<String, Integer> t : list){
            System.out.println("key:" + t.f0 + ",value:" + t.f1);
        }
        DataStream<Tuple2<String, Integer>> stream = env.fromCollection(list).keyBy(0)
                .reduce(new ReduceFunction<Tuple2<String, Integer>>() {
                    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> source,
                                                          Tuple2<String, Integer> target) throws Exception {
                        return new Tuple2<String, Integer>(source.f0, source.f1 + target.f1);
                    }
                });
        stream.print();
        env.execute();
    }
}
