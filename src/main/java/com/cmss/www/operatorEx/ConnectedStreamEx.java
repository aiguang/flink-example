package com.cmss.www.operatorEx;

import com.cmss.www.utils.WindowJoinSampleData;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

/**
 * Created by Administrator on 2017/3/3.
 */
public class ConnectedStreamEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple3<String, Integer, String>> stream1 = WindowJoinSampleData.GradeSource.getSource(env, 3L);
        DataStream<Tuple3<String, Integer, String>> stream2 = WindowJoinSampleData.SalarySource.getSource(env, 3L);

        DataStream<Boolean> res =  stream1.connect(stream2).map(new CoMapFunction<Tuple3<String,Integer,String>, Tuple3<String,Integer,String>,
                Boolean>() {
            public Boolean map1(Tuple3<String, Integer, String> tuple) throws Exception {
                return tuple.f0.length() != 5;
            }

            public Boolean map2(Tuple3<String, Integer, String> tuple) throws Exception {
                return tuple.f0.length() != 3;
            }
        });
        res.print();
        env.execute();
    }
}
