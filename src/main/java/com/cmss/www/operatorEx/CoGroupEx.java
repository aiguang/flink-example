package com.cmss.www.operatorEx;

import com.cmss.www.utils.WindowJoinSampleData;
import org.apache.flink.api.common.functions.CoGroupFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * Created by Administrator on 2017/3/3.
 */
public class CoGroupEx {
    public static void main(String[] args){
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Tuple3<String, Integer, String>> stream1 = WindowJoinSampleData.GradeSource.getSource(env, 3L);
        DataStream<Tuple3<String, Integer, String>> stream2 = WindowJoinSampleData.SalarySource.getSource(env, 3L);

        stream1.coGroup(stream2).where(new NameKeySelector()).equalTo(new NameKeySelector()).
                window(TumblingProcessingTimeWindows.of(Time.seconds(5))).
                apply(new CoGroupFunction<Tuple3<String, Integer, String>, Tuple3<String, Integer, String>, Object>() {
                    public void coGroup(Iterable<Tuple3<String, Integer, String>> iterable,
                                        Iterable<Tuple3<String, Integer, String>> iterable1, Collector<Object> collector) throws Exception {
                        String f0 = iterable.iterator().next().f0 + "_" + iterable1.iterator().next().f0;
                        int f1 = iterable.iterator().next().f1 + iterable1.iterator().next().f1;
                        String f2 = iterable.iterator().next().f2 + iterable1.iterator().next().f2;
                        collector.collect(new Tuple3<String, Integer, String>(f0, f1, f2));
            }
        });
    }

}
class NameKeySelector implements KeySelector<Tuple3<String, Integer, String>, String> {
    public String getKey(Tuple3<String, Integer, String> value) {
        return value.f0;
    }
}