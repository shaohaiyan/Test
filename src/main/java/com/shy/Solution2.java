package com.shy;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created by shy on 2021/6/29.
 */
public class Solution2 {
    private String[][] number2Char = new String[][]{{}, {}, {"a", "b", "c"}, {"d", "e", "f"}, {"g", "h", "i"}, {"j", "k", "l"}, {"m", "n", "o"}, {"p", "q", "r", "s"}, {"t", "u", "v"}, {"w", "x", "y", "z"}};

    //private String[][] number2Char=new String[][]{{},{},{"a","b","c"},{"d","e","f"},{"g","h","i"},{"o","p","q"},{"r","s","t"},{"u","v","w"},{"x","y","z"}};
    private List<String> result = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return Collections.emptyList();
        }
        dfs(digits.toCharArray(), 1, new StringBuilder());
        return result;
    }

    private void dfs(char[] nums, int dep, StringBuilder temp) {
        if (dep == nums.length + 1) {
            result.add(temp.toString());
            return;
        }
        String[] cur = number2Char[Integer.valueOf(String.valueOf(nums[dep - 1]))];
        for (int i = 0; i < cur.length; i++) {
            temp.append(cur[i]);
            dfs(nums, dep + 1, temp);
            temp.deleteCharAt(temp.length() - 1);
        }
    }

    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int index = 1;
        int j = 1;
        int pre = nums[0];
        int cout = 1;
        while (j < nums.length) {
            if (nums[j] != pre) {
                cout = 1;
                pre = nums[j];
            } else {
                cout++;
            }
            if (cout == 1) {
                nums[index] = nums[j];
                index++;
            } else if (cout == 2) {
                nums[index] = nums[j];
                index++;
            }
            j++;
        }
        return index;
    }

    public boolean wordBreak(String s, List<String> wordDict) {
        if (s == null || s.length() == 0) {
            return true;
        }
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 0; i < wordDict.size(); i++) {
            for (int j = 1; j <= s.length(); j++) {
                int size = wordDict.get(i).length();
                if (j - size < 0) {
                    continue;
                }
                if (dp[j - size] && s.substring(j - size, j).equals(wordDict.get(i))) {
                    dp[j] = dp[j] || dp[j - size];
                }
            }
        }
        return dp[s.length()];
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>>[] dp = new List[target + 1];
        dp[0] = new ArrayList<>();
        dp[0].add(Lists.newArrayList());
            for (int i = 0; i < candidates.length; i++) {
                for (int j = 0; j <= target; j++) {

                    if(j<candidates[i]){
                    continue;
                }
                List<List<Integer>> temp = dp[j - candidates[i]];
                List<List<Integer>> cur = dp[j];
                if(cur==null){
                    cur=new ArrayList<>();
                }
                dp[j] = cur;
                if (temp != null&&!temp.isEmpty()) {
                    for (List<Integer> list : temp) {
                        List<Integer> l = new ArrayList(list);
                        l.add(candidates[i]);
                        cur.add(l);
                    }
                }
                //dp[j]=new dp[j]+dp[j-candidates[i]];
            }
        }
        return dp[target];
    }
    public int rob(int[] nums) {
        if(nums==null||nums.length==0){
            return 0;
        }
        int[][] dp=new int[nums.length+1][2];

        for(int i=1;i<=nums.length;i++){
            dp[i][0]=Math.max(dp[i-1][1],dp[i-1][0]);
            dp[i][1]=dp[i-1][0]+nums[i-1];
        }
        return Math.max(dp[nums.length][0],dp[nums.length][1]);

    }
    public List<Integer> findAnagrams(String s, String p) {
        if(s==null||p==null||s.length()==0){
            return Collections.emptyList();
        }
        char[] array=s.toCharArray();
        int start=0;
        List<Integer> result=new ArrayList<>();
        int[] tableP=new int[26];
        char[] pArray=p.toCharArray();
        for(int i=0;i<pArray.length;i++){
            tableP[pArray[i]-'a']+=1;
        }
        int[] tableS=new int[26];
        char[] sArray=s.toCharArray();
        for(int i=0;i<pArray.length;i++){
            tableS[sArray[i]-'a']+=1;
        }
        if(isSame(tableP,tableS)){
            result.add(0);
        }
        for(int i=p.length();i<sArray.length;i++){
            tableS[sArray[i-p.length()]-'a']-=1;
            tableS[sArray[i]-'a']+=1;
            if(isSame(tableP,tableS)){
                result.add(i-p.length()+1);
            }

        }
        return result;
    }
    private boolean isSame(int[] array1,int[] array2){
        for(int i=0;i<array1.length;i++){
            if(array1[i]!=array2[i]){
                return false;
            }
        }
        return true;
    }

    public String decodeString(String s) {
        if(s==null||s.length()==0){
            return s;
        }
        Stack<Integer> countStack=new Stack<>();
        Stack<String> strStack=new Stack<>();
        StringBuilder str=new StringBuilder();
        char[] array=s.toCharArray();
        int count=0;
        for(int i=0;i<array.length;i++){
            if(array[i]>='0'&&array[i]<='9'){
                count=count*10+Integer.parseInt(""+array[i]);
            }else if(array[i]=='['){
                countStack.push(count);
                strStack.push(str.toString());
                str=new StringBuilder();
                count=0;
            }else if(array[i]==']'){
                StringBuilder temp=new StringBuilder();
                Integer max=countStack.pop();
                while(max-->0){
                    temp.append(str);
                }
                str=new StringBuilder(strStack.pop()).append(temp);
            }else {
                str.append(array[i]);
            }
        }
        return str.toString();
    }

    private int getMinK(int[] a,int[] b,int k){
        int i=0;
        int j=0;
        int pre=0;
        int[] preArr=null;
        while(i<a.length&&j<b.length&&k>0){
            if(a[i]<b[j]){
                pre=i;
                preArr=a;
                i++;
            }else{
                pre=j;
                preArr=b;
                j++;
            }
            k--;
        }
        while(i<a.length&&k>0){
            pre=i;
            preArr=a;
            i++;
            k--;
        }
        while(j<b.length&&k>0){
            pre=j;
            preArr=b;
            j++;
            k--;
        }
        return preArr[pre];
    }
    public int[] getLeastNumbers(int[] arr, int k) {
        if(arr.length<=k){
            return arr;
        }
        return quickSort(arr,0,arr.length-1,k);
    }
    private int[] quickSort(int[] arr,int l,int r,int k){
        int index=getMiddle(arr,l,r);
        if(index==k){
            return Arrays.copyOf(arr,k);
        }else if(index>k){
            return quickSort(arr,l,index-1,k);
        }else{
            return quickSort(arr,index+1,r,k);
        }
    }
    private int getMiddle(int[] arr,int l,int r){
        int i=l,j=r;
        int middleValue=arr[l];
        while(i<j){
            while(i<j&&arr[j]>middleValue){
                j--;
            }
            arr[i]=arr[j];
            while(i<j&&arr[i]<=middleValue){
                i++;
            }
            arr[j]=arr[i];
        }
        arr[i]=middleValue;
        return i;
    }
    public double[] medianSlidingWindow(int[] nums, int k) {
        if(nums==null||nums.length==0){
            return null;
        }
        int[] window=Arrays.copyOf(nums,k);
        Arrays.sort(window);
        double[] result=new double[nums.length-k+1];
        result[0]=getMiddle(window);
        for(int i=1;i<=nums.length-k;i++){
            int index=find(window,nums[i-1]);
            window[index]=nums[i+k-1];
            //向后
            while(index<window.length-1&&window[index]>window[index+1]){
                swap(window,index,index+1);
                index++;
            }
            while(index>0&&window[index]<window[index-1]){
                swap(window,index,index-1);
                index--;
            }
            result[i]=getMiddle(window);
        }
        return result;
    }

    private int find(int[] window,int target){
        int s=0,e=window.length-1;
        while(s<=e){
            int mid=(s+e)/2;
            if(window[mid]==target){
                return mid;
            }else if(window[mid]>target){
                e=mid-1;
            }else if(window[mid]<target){
                s=mid+1;
            }
        }
        return -1;
    }

    private void swap(int[] window,int i,int j){
        int temp=window[i];
        window[i]=window[j];
        window[j]=temp;
    }

    private double getMiddle(int[] window){
        if(window.length%2==0){
            return (window[window.length/2-1]+window[window.length/2])/2.0;
        }else{
            return window[window.length/2]*1.0;
        }
    }
    public static void main(String[] args) {
        //new Solution2().buildTree(new int[]{9,3,15,20,7},new int[]{9,15,7,20,3});

        //new Solution2().canPartition(new int[]{100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100});
        //"leetcode"
        //[1,3,-1,-3,5,3,6,7]
        Object res = new Solution2().medianSlidingWindow(new int[]{1,3,-1,-3,5,3,6,7},3);
        System.out.println(res);
        //Integer[] a=new Integer[2];

        List[] b = new List[1];
        int[] a = new int[1];
        //Character.isDigit();
        new StringBuilder().append(new StringBuilder());
        System.out.println((int)Math.sqrt(3));
    }
}
