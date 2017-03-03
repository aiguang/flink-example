package com.cmss.www.utils;

import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/1.
 */
public class DataUtil {
    static final String[] NAMES = {"tom"
            , "jerry", "alice", "bob", "john", "grace"};
    static final Random rnd = new Random();
    public static List<Integer> createCollectionList(){
        List<Integer> list = new ArrayList<Integer>();
        int i;
        for(i = 1; i < 10; i++);{
            list.add(i);
        }
        return list;
    }
    public static List<Tuple2<String, Integer>> createTuple2(int num){
        List<Tuple2<String, Integer>> res = new ArrayList<Tuple2<String, Integer>>();
        for(int i = 0; i < num;i++){
            res.add(new Tuple2<String, Integer>(NAMES[rnd.nextInt(NAMES.length)], rnd.nextInt(NAMES.length)));
        }
        return res;
    }
}
