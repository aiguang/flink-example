package com.cmss.www.operatorEx;

import com.cmss.www.utils.DataUtil;
import com.cmss.www.utils.ThrottledIterator;
import com.cmss.www.utils.WindowJoinSampleData;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.List;

/**
 *
 */
public class WindowAllEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);

        DataStream<Tuple3<String, Integer, String>> stream = env.fromCollection(
                new ThrottledIterator<Tuple3<String, Integer, String>>(new WindowJoinSampleData.GradeSource(), 3L),
                TypeInformation.of(new TypeHint<Tuple3<String, Integer, String>>() {
                })).windowAll(TumblingProcessingTimeWindows.of(Time.seconds(2))).reduce(new ReduceFunction<Tuple3<String, Integer, String>>() {
            public Tuple3<String, Integer, String> reduce(
                    Tuple3<String, Integer, String> r1,
                    Tuple3<String, Integer, String> t1) throws Exception {
                return new Tuple3<String, Integer, String>(r1.f0+ "_" + t1.f0, r1.f1 + t1.f1,
                        r1.f2 + "_" + t1.f2);
            }
        });
        stream.print();
        env.execute();
    }
}
