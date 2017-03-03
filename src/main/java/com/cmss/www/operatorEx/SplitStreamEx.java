package com.cmss.www.operatorEx;

import com.cmss.www.utils.WindowJoinSampleData;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */
public class SplitStreamEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple3<String, Integer, String>> stream1 = WindowJoinSampleData.GradeSource.getSource(env, 3L);

        SplitStream<Tuple3<String, Integer ,String>> intermediate =  stream1.split(new OutputSelector<Tuple3<String, Integer, String>>() {

            public Iterable<String> select(Tuple3<String, Integer, String> tuple) {

                List<String> list = new ArrayList<String>();
                if (tuple.f0.length() == 3) {
                    list.add("_" + 3);
                } else {
                    list.add("_" + 5);
                }
                return list;
            }
        });

        DataStream<Tuple3<String, Integer, String>> res = intermediate.select("_3");
        res.print();
        env.execute();
    }
}
