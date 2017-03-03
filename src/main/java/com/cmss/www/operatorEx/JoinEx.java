/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmss.www.operatorEx;

import com.cmss.www.utils.WindowJoinSampleData;
import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple6;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * join是对窗口内每个符合条件的元素pair做笛卡尔积
 */
public class JoinEx {
	static final String[] NAMES = {"tom"
			, "jerry", "alice", "bob", "john", "grace"};

	public static void main(String[] args) throws Exception {
		final long windowSize = 5000;
		final long rate = 3;

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.IngestionTime);

		DataStream<Tuple3<String, Integer, String>> grades = WindowJoinSampleData.GradeSource.getSource(env, rate);
		DataStream<Tuple3<String, Integer, String>> salaries = WindowJoinSampleData.SalarySource.getSource(env, rate);

		DataStream<Tuple6<String, Integer, String, String, Integer, String>> joinedStream = runWindowJoin(grades, salaries, windowSize);

		joinedStream.print().setParallelism(1);

		env.execute();
	}
	
	public static DataStream<Tuple6<String, Integer, String, String, Integer, String>> runWindowJoin(
			DataStream<Tuple3<String, Integer, String>> grades,
			DataStream<Tuple3<String, Integer, String>> salaries,
			long windowSize) {

		return grades.join(salaries)
				.where(new NameKeySelector())
				.equalTo(new NameKeySelector())
				
				.window(TumblingProcessingTimeWindows.of(Time.milliseconds(windowSize)))
				
				.apply(new JoinFunction<Tuple3<String, Integer, String>, Tuple3<String, Integer, String>,
						Tuple6<String, Integer, String, String, Integer, String>>() {
					public Tuple6<String, Integer, String, String, Integer, String> join(
							Tuple3<String, Integer, String> source,
							Tuple3<String, Integer, String> target) throws Exception {
						return new Tuple6<String, Integer, String, String, Integer, String>(
								source.f0, source.f1, source.f2,target.f0, target.f1, target.f2
						);
					}
				});
	}
	
	private static class NameKeySelector implements KeySelector<Tuple3<String, Integer, String>, String> {
		public String getKey(Tuple3<String, Integer, String> value) {
			return value.f0;
		}
	}
}
