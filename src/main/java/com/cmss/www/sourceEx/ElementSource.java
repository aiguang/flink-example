package com.cmss.www.sourceEx;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Created by Administrator on 2017/2/22.
 */
public class ElementSource {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Integer> dataStream = environment.fromElements(1,2,3,4,5).filter(
                new FilterFunction<Integer>() {
                    public boolean filter(Integer integer) throws Exception {
                        return integer > 4;
                    }
                }
        );
        dataStream.print();
        environment.execute();
    }

}
