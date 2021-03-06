package com.cmss.www.operatorEx;

import com.cmss.www.utils.DataUtil;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
public class FoldEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        List<Tuple2<String, Integer>> list = DataUtil.createTuple2(10);
        for(Tuple2<String, Integer> t : list){
            System.out.println("key:" + t.f0 + ",value:" + t.f1);
        }
        DataStream<Integer> stream = env.fromCollection(list).keyBy(0).fold(
                10, new FoldFunction<Tuple2<String, Integer>, Integer>() {
                    public Integer fold(Integer initial, Tuple2<String, Integer> o) throws Exception {
                        return initial + o.f1;
                    }
                }
        );
        stream.print();
        env.execute();
    }
}
