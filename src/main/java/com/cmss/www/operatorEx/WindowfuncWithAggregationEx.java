package com.cmss.www.operatorEx;

import com.cmss.www.utils.MyWindowFunc;
import com.cmss.www.utils.ThrottledIterator;
import com.cmss.www.utils.WindowJoinSampleData;
import org.apache.flink.api.common.functions.FoldFunction;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * Created by Administrator on 2017/3/2.
 */
public class WindowfuncWithAggregationEx {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);
        env.setParallelism(1);
        DataStream<Tuple3<String, Integer, String>> stream =env.fromCollection(
                new ThrottledIterator<Tuple3<String, Integer, String>>(new WindowJoinSampleData.GradeSource(), 3L),
                TypeInformation.of(new TypeHint<Tuple3<String, Integer, String>>() {
                })).keyBy(0).window(TumblingProcessingTimeWindows
                .of(Time.milliseconds(5000))).apply(new Tuple3<String, Integer, String>("", 0, ""), new MyFoldFunc(),
                new MyWinFunc());
        stream.print();
        env.execute();
    }
}
class MyWinFunc implements WindowFunction<Tuple3<String, Integer, String>, Tuple3<String,Integer, String>, Tuple, TimeWindow>{
    public void apply(Tuple tuple, TimeWindow timeWindow,
                      Iterable<Tuple3<String, Integer, String>> iterable,
                      Collector<Tuple3<String, Integer, String>> collector) throws Exception {
        Integer count = iterable.iterator().next().getField(1);
        System.out.println("cur win start " + timeWindow.getStart() + ", cur win end " +
        timeWindow.getEnd());
        collector.collect(new Tuple3<String, Integer, String>(tuple.getField(0).toString(),count,
                tuple.getField(0).toString()));
    }
}
class MyFoldFunc implements FoldFunction<Tuple3<String, Integer, String>, Tuple3<String, Integer, String>>{
    public Tuple3<String, Integer, String> fold(Tuple3<String, Integer, String> source, Tuple3<String, Integer, String> target) throws Exception {
        return new Tuple3<String, Integer, String>(source.f0 + "_" + target.f0,
                source.f1 + target.f1, source.f0 + "_" + target.f0);
    }
}


