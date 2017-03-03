/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cmss.www.utils;

import com.cmss.www.operatorEx.JoinEx;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;

/**
 * Sample data for the {@link JoinEx} example.
 */
@SuppressWarnings("serial")
public class WindowJoinSampleData {

//	static final String[] NAMES = {"tom"
//			, "jerry", "alice", "bob", "john", "grace"};

	static final String[] NAMES = {"tom"
			, "jerry", "alice"};
	static final int GRADE_COUNT = 5;
	static final int SALARY_MAX = 10000;

	/**
	 * Continuously generates (name, grade).
	 */
	public static class GradeSource implements Iterator<Tuple3<String, Integer, String>>, Serializable {

		private final Random rnd = new Random(hashCode());


		public boolean hasNext() {
			return true;
		}

		public Tuple3<String, Integer, String> next() {
			return new Tuple3<String, Integer, String>(NAMES[rnd.nextInt(NAMES.length)], rnd.nextInt(GRADE_COUNT) + 1, "gradeSource");
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public static DataStream<Tuple3<String, Integer, String>> getSource(StreamExecutionEnvironment env, long rate) {
			return env.fromCollection(new ThrottledIterator<Tuple3<String, Integer, String>>(new GradeSource(), rate),
					TypeInformation.of(new TypeHint<Tuple3<String, Integer, String>>() {
					}));
		}
	}

	/**
	 * Continuously generates (name, salary).
	 */
	public static class SalarySource implements Iterator<Tuple3<String, Integer, String>>, Serializable {

		private final Random rnd = new Random(hashCode());

		public boolean hasNext() {
			return true;
		}

		public Tuple3<String, Integer, String> next() {
			return new Tuple3<String, Integer, String>(NAMES[rnd.nextInt(NAMES.length)], rnd.nextInt(SALARY_MAX) + 1, "salarySource");
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public static DataStream<Tuple3<String, Integer, String>> getSource(StreamExecutionEnvironment env, long rate) {
			return env.fromCollection(new ThrottledIterator<Tuple3<String, Integer, String>>(new SalarySource(), rate),
					TypeInformation.of(new TypeHint<Tuple3<String, Integer, String>>() {
					}));
		}
	}
}
