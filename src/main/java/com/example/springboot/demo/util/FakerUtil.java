package com.example.springboot.demo.util;

import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.mapper.LearnSqlMapper;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class FakerUtil {

    public static final Faker FAKER = new Faker();

    public static String getRandomName() {
        return FAKER.name().fullName();
    }

    public static String getRandomId() {
        return FAKER.idNumber().valid();
    }

    public static void testRandomNames() {
        int numberBetween = FAKER.number().numberBetween(10, 20);
        for (int i = 0; i < numberBetween; i++) {
            System.out.println((i + 1) + " " + getRandomName());
        }
    }

    public static void main(String[] args) {
        // testRandomNames();
        // System.out.println("FAKER.gameOfThrones().quote() = " + FAKER.gameOfThrones().quote());

    }

    public static void insert(int maxLine, int numOfThreads, int commitSize, LearnSqlMapper mapper) {
        int[] nums = avgSplit(maxLine, numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            int threadMaxSize = nums[i];

            Thread insertThread = new Thread(() -> {
                List<UserInfo> commitBatch = new ArrayList<>(commitSize);
                for (int j = 0; j < threadMaxSize; j++) {
                    commitBatch.add(new UserInfo(getRandomId(), getRandomName()));

                    if (commitBatch.size() == commitSize || j == (threadMaxSize - 1)) {
                        mapper.insertToUserInfo(commitBatch);
                        System.out.println(Thread.currentThread().getName() + " --- commitBatch size: " + commitBatch.size());
                        commitBatch.clear();
                    }
                }
            }, "InsertThread " + i);

            insertThread.start();
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int[] avgSplit(int max, int num) {
        int i = max / num;
        int[] result = new int[num];
        int remain = max - num * i;
        for (int j = 0; j < result.length; j++) {
            if (remain > 0) {
                result[j] = i + 1;
                remain--;
            } else {
                result[j] = i;
            }
        }
        return result;
    }


}
