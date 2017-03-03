package com.cmss.www.operatorEx;

import com.cmss.www.utils.DataUtil;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;

import java.util.List;

/**
 * Õª×Ô¹Ù·½ÎÄµµ
 * This windowing scheme is only useful if you also specify a custom trigger. Otherwise,
 * no computation is ever going to be performed, as the global window does not have a natural end at which we could process the aggregated elements.
 *
 */
public class GlobalWindowEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        List<Tuple2<String, Integer>> list = DataUtil.createTuple2(10);
        for (Tuple2<String, Integer> t : list) {
            System.out.println("key:" + t.f0 + ",value:" + t.f1);
        }
        DataStream<Integer> stream =  env.fromCollection(list).windowAll(GlobalWindows.create()).fold(0, new FoldFunction<Tuple2<String, Integer>, Integer>() {
            public Integer fold(Integer integer, Tuple2<String, Integer> o) throws Exception {
                return integer + o.f1;
            }
        });
        stream.print();
        env.execute();
    }
}
