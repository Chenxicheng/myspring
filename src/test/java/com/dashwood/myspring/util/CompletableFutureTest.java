package com.dashwood.myspring.util;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureTest {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    @Test
    public void test1() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(()->{
            return "hello";
        }, executorService);

        CompletableFuture<Integer> future1 = future.thenApply(s -> {
            System.out.println(s);
            return s.length();
        });

        CompletableFuture<String> future2 = future1.thenApply(s -> {
           return s+"aaa";
        });

        try {
            System.out.println(future2.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test2() {
        List<CompletableFuture<JSONObject>> taskList = Lists.newArrayList();
        JSONObject join = new JSONObject();
        for (int i=0; i<5; i++) {
            taskList.add(CompletableFuture.supplyAsync(new TestRunnable("a"+i), executorService));
        }

//        CompletableFuture[] array = (CompletableFuture<JSONObject>[])taskList.toArray();
        try {
            CompletableFuture.allOf(taskList.toArray(new CompletableFuture[taskList.size()]))
                    .thenApply(v-> taskList.stream().map(future -> future.join()).collect(Collectors.toList()))
                    .thenAccept(list -> list.forEach(jsonObject -> join.putAll(jsonObject)));
            System.out.println(join);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test3() {
        List<String> list1 = Lists.newArrayList();
        List<TestRunnable> taskList = Lists.newArrayList();
        for (int i=0; i<5; i++) {
            taskList.add(new TestRunnable("a"+i));
        }
        CompletableFuture[] cfs = taskList.stream().map(testRunnable -> CompletableFuture.supplyAsync(testRunnable,executorService)
            .whenComplete((v, e) -> {
            list1.add(v.toString());
        })
        ).toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(cfs).join();

        System.out.println(list1);

    }

    @Test
    public void test4() {
        List<CompletableFuture<JSONObject>> taskList = Lists.newArrayList();
        JSONObject join = new JSONObject();
        for (int i=0; i<5; i++) {
                taskList.add(CompletableFuture.supplyAsync(new TestRunnable("a"+i), executorService));

        }

        CompletableFuture<List<JSONObject>> future = CompletableFutureUtils.newInstance().squence(taskList);

        try {
                future.get().stream()
                            .forEach(System.out::println);

            System.out.println(join);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void test5() {
        List<CompletableFuture<JSONObject>> taskList = Lists.newArrayList();
        JSONObject join = new JSONObject();
        for (int i=0; i<5; i++) {
            CompletableFuture<JSONObject> future = CompletableFuture.supplyAsync(new TestRunnable("a"+i), executorService);
            taskList.add(CompletableFutureUtils.newInstance().within(future, Duration.ofSeconds(20L))
                                                                .handle((s,t) -> {
                                                                    System.out.println("******"+s);
                                                                    if (t != null) {
                                                                        JSONObject json = new JSONObject();
                                                                        json.put("a", t.getMessage());
                                                                        s = json;
//                                                                        join.putAll(json);
                                                                    }
                                                                    return s;
                                                                })
            );
        }

        CompletableFuture<List<JSONObject>> future = CompletableFutureUtils.newInstance().squence(taskList);

        try {
            future.get().stream()
                    .forEach(json -> join.putAll(json));

            System.out.println(join);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

}
