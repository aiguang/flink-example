package com.cmss.www.operatorEx;

import com.cmss.www.utils.DataUtil;
import com.cmss.www.utils.MyWindowFunc;
import com.cmss.www.utils.ThrottledIterator;
import com.cmss.www.utils.WindowJoinSampleData;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.List;

/**
 * 尽量避免window func，需要buffer element，低效
 */
public class WindowFunctionEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        env.setParallelism(1);
        DataStream<Tuple3<String, Integer, String>> stream =env.fromCollection(
                new ThrottledIterator<Tuple3<String, Integer, String>>(new WindowJoinSampleData.GradeSource(), 3L),
                TypeInformation.of(new TypeHint<Tuple3<String, Integer, String>>() {
                })).keyBy(0).window(TumblingProcessingTimeWindows
                .of(Time.milliseconds(1000))).apply(new MyWindowFunc());
        stream.print();
        env.execute();
    }
}
