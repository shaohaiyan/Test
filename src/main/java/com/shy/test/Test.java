package com.shy.test;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by shy on 2021/5/28.
 */
public class Test {
    private static AtomicInteger num = new AtomicInteger();

    public int reversePairs(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        return mergeSort(nums, 0, nums.length - 1);

    }

    public int mergeSort(int[] nums, int left, int right) {
        if (left >= right) {
            return 0;
        }
        int mid = (left + right) / 2;
        int leftCount = mergeSort(nums, left, mid);
        int rightCount = mergeSort(nums, mid + 1, right);
        int count = mergeAndCount(nums, left, mid, right);
        return leftCount + count + rightCount;
    }

    public int mergeAndCount(int[] nums, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int[] temp = new int[right - left + 1];
        int k = 0;
        int count = 0;
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
                count += mid - i + 1;
            }
        }
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        while (j <= right) {
            temp[k++] = nums[j++];
        }

        System.arraycopy(temp, 0, nums, left, temp.length);
        return count;
    }

    public static void main(String[] args) {
        int[] nums=new int[]{7,5,6,4};
        new Test().reversePairs(nums);
    }


    private Map<Integer, Integer> n2Count = new HashMap<Integer, Integer>();

    public int climbStairs(int n) {
        if (n < 2) {
            return 1;
        }
        return climb(n);
    }

    public int climb(int n) {
        if (n <= 1) {
            return 1;
        }
        Integer pre1 = n2Count.get(n - 1);
        if (pre1 == null) {
            pre1 = climb(n - 1);
            n2Count.put(n - 1, pre1);
        }
        Integer pre2 = n2Count.get(n - 2);
        if (pre2 == null) {
            pre2 = climb(n - 2);
            n2Count.put(n - 2, climb(n - 2));
        }
        return pre2 + pre2;
    }

    public static void findAllSubArray(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                for (int k = i; k <= j; k++) {
                    System.out.print(nums[k] + ",");
                }
                System.out.println();
            }
        }
    }

    public static int findNumberOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] lengthDp = new int[nums.length];
        int[] countsDp = new int[nums.length];
        Arrays.fill(lengthDp, 1);
        Arrays.fill(countsDp, 1);
        int maxLength = 0;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (lengthDp[j] + 1 > lengthDp[i]) {
                        lengthDp[i] = lengthDp[j] + 1;
                        countsDp[i] = countsDp[j];
                    } else if (lengthDp[j] + 1 == lengthDp[i]) {
                        countsDp[i] += countsDp[j];
                    }
                }
            }
            maxLength = Math.max(lengthDp[i], maxLength);
        }
        int result = 0;
        for (int i = 0; i < lengthDp.length; i++) {
            if (lengthDp[i] == maxLength) {
                result += countsDp[i];
            }
        }
        return result;

    }

    public static void printA(int[] nums) {
        int eMask = 1 << nums.length;
        for (int mask = 0; mask < eMask; mask++) {
            for (int j = 0; j < nums.length; j++) {
                if (((1 << j) & mask) != 0) {
                    System.out.print(nums[j] + ",");
                }
            }
            System.out.println();
        }
    }

    public static void printAllSequence(int[] nums) {

        int mEnd = 1 << nums.length;

        for (int mask = 0; mask < mEnd; mask++) {
            List<Integer> temp = Lists.newArrayList();
            for (int j = 0; j < nums.length; j++) {
                int a = (1 << j);
                System.out.println("a:" + Integer.toBinaryString(a));
                System.out.println("mask:" + Integer.toBinaryString(mask));
                if (((1 << j) & mask) != 0) {
                    temp.add(nums[j]);
                }
            }
            System.out.println(temp.toString());
            System.out.println("--------");
        }
    }
}
