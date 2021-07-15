package com.shy;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

/**
 * Created by shy on 2021/5/29.
 */
public class Solution {
    private static int maxDep = 0;
    private int sum=0;
    private Map<Integer,Integer> preSum2Count=new HashMap<>();
    public int pathSum(TreeNode root, int targetSum) {
        if(root==null){
            return 0;
        }
        preSum2Count.put(0,1);
        return dfs(root,0,targetSum);
    }
    private int dfs(TreeNode root,int curSum,int targetSum){
        if(root==null){
            return 0;
        }
        int sum=curSum+root.val;
        int res=preSum2Count.getOrDefault(sum-targetSum,0);
        preSum2Count.put(sum,preSum2Count.getOrDefault(sum,0)+1);
        res+=dfs(root.left, sum, targetSum);
        res+=dfs(root.right, sum, targetSum);
        preSum2Count.put(sum,preSum2Count.getOrDefault(sum,0)-1);
        return res;
    }

    public int maxArea(int[] height) {
        if(height==null||height.length==0){
            return 0;
        }
        int left=0;
        int right=height.length-1;
        int max=0;
        while (left<right){
            max=Math.max(Math.min(height[right],height[left])*(right-left),max);
            if(height[right]<height[left]){
                right--;
            }else{
                left++;
            }
        }
        return max;
    }
    class LRUCache extends LinkedHashMap<Integer,Integer>{
        private int max;
        public LRUCache(int capacity) {
            super(capacity,0.75f,true);
            max=capacity;
        }

        public int get(int key) {
            Integer temp=super.get(key);
            return null==temp?-1:temp;
        }

        public void put(int key, int value) {
            super.put(key,value);
        }

        @Override
        public boolean removeEldestEntry(Map.Entry<Integer,Integer> entry){
            return size()>max;
        }
    }
    public int maxDepth(TreeNode root) {
        depTrace(root, 0);
        return maxDep;
    }

    private void depTrace(TreeNode root, int dep) {
        if (root != null) {
            dep++;
            maxDep = Math.max(maxDep, dep);
        } else {
            return;
        }
        depTrace(root.left, dep);
        depTrace(root.right, dep);
    }

    public int longestCommonSubsequence2(String text1, String text2) {
        if(text1==null||text1.length()==0||text2==null||text2.length()==0){
            return 0;
        }
        int[] dp=new int[text2.length()+1];

        for(int i=0;i<text1.length();i++){
            char c1=text1.charAt(i);
            int pre;
            int temp=dp[0];
            for(int j=0;j<text2.length();j++){
                pre=temp;
                temp=dp[j+1];
                char c2=text2.charAt(j);
                if(c1==c2){
                    dp[j+1]=pre+1;
                }else{
                    dp[j+1]=Math.max(dp[j+1],dp[j]);
                }
            }
        }
        return dp[text2.length()];
    }

    public int longestCommonSubsequence(String text1, String text2) {
        if(text1==null||text1.length()==0||text2==null||text2.length()==0){
            return 0;
        }
        int[][] dp=new int[text1.length()+1][text2.length()+1];

        for(int i=0;i<text1.length();i++){
            char c1=text1.charAt(i);
            for(int j=0;j<text2.length();j++){
                char c2=text2.charAt(j);
                if(c1==c2){
                    dp[i+1][j+1]=dp[i][j]+1;
                }else{
                    dp[i+1][j+1]=Math.max(dp[i][j+1],dp[i+1][j]);
                }
            }
        }
        return dp[text1.length()][text2.length()];
    }
    private List<List<Integer>> result=new ArrayList<>();
    private int length;
    private int[] nums;
    private int target;


    private void dfs(List<Integer> curList,int sum){
        if((sum>=target)){
            if(sum==target){
                result.add(new ArrayList<>(curList));
            }
            return;
        }
        Set<Integer> set=new HashSet<>();
        for(int i=0;i<nums.length;i++){
            Integer temp=nums[i];
            if(set.contains(temp)){
                continue;
            }
            set.add(temp);
            curList.add(temp);
            sum+=temp;
            dfs(curList,sum);
            sum-=temp;
            curList.remove(temp);
        }
    }
    public List<List<Integer>> combinationSum(int[] array, int target1) {
        if(array==null||array.length==0){
            return Collections.emptyList();
        }
        length=array.length;
        nums=array;
        target=target1;
        //注意这里需要用linkedhashMap，不然得到的values返回的结果是无序的
        dfs(new ArrayList<>(),0);
        return result;
    }
    public static TreeNode arrayToTreeNode(Integer[] array){
        if(array.length == 0){
            return null;
        }
        TreeNode root = new TreeNode(array[0]);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean isLeft = true;
        for(int i = 1; i < array.length; i++){
            TreeNode node = queue.peek();
            if(isLeft){
                if(array[i] != null){
                    node.left = new TreeNode(array[i]);
                    queue.offer(node.left);
                }
                isLeft = false;
            }else {
                if(array[i] != null){
                    node.right = new TreeNode(array[i]);
                    queue.offer(node.right);
                }
                queue.poll();
                isLeft = true;
            }
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(1);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5=new TreeNode(4);
        TreeNode node6=new TreeNode(6);
        node1.right = node2;
        //node1.right = node3;
        //node3.left = node4;
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(3);
        ListNode listNode3 = new ListNode(2);
        ListNode listNode4 = new ListNode(0);
        ListNode listNode5 = new ListNode(-4);
        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        //listNode5.next=listNode2;
        //int max = new Solution().maxDepth(node1);
        //System.out.println(temp);
        //LinkedList list = new LinkedList(new ArrayList());
        //new Solution().longestCommonSubsequence2("abcde","ace");
        //new Solution().findLength(new int[]{5,14,80},new int[]{4,80});
        //System.out.println(a);
        Integer[] input=new Integer[]{5,4,8,11,null,13,4,7,2,null,null,5,1};

        //System.out.println(new Solution().pathSum(arrayToTreeNode(input),22));
        //new LinkedList().re
        String[] a=StringUtils.split(",1,2",",");
        System.out.println(a.length);
        System.out.println(NumberUtils.isNumber(null));

    }
    public boolean wordBreak(String s, List<String> wordDict) {
        if(s==null||s.length()==0){
            return true;
        }
        boolean[] dp=new boolean[s.length()+1];
        dp[0]=true;
        for(int j=1;j<s.length();j++){
            for(int i=0;i<wordDict.size();i++){
                int size=wordDict.get(i).length();
                if(j-size<0){
                    continue;
                }
                if(s.substring(j-size,j).equals(wordDict.get(i))){
                    dp[j]=dp[j]||dp[j-size];
                }
            }
        }
        return dp[s.length()];
    }
    public List<Integer> findDisappearedNumbers(int[] nums) {
        if(nums==null||nums.length==0){
            return Collections.emptyList();
        }
        List<Integer> res=new ArrayList<>();
        for(int i=0;i<nums.length;i++){
            int time=0;
            while(nums[i]!=i+1&&time<nums.length){
                swap(nums,nums[i]-1,i);
                time++;
            }
            if(time==nums.length){
                res.add(i);
            }
        }

        return res;

    }
    private void swap(int[] nums,int i,int j){
        int temp=nums[i];
        nums[i]=nums[j];
        nums[j]=temp;
    }
    public int mySqrt(int x) {
        double max=x;
        double min=0;
        double temp;
        double mid;
        do {
            mid=(max+min)/2;
            temp=mid*mid;
            if(temp>x){
                max=mid-1;
            }else if(temp<x){
                min=mid+1;
            }
        }while (Math.abs(x-temp)>1e-5);

        return (int) mid;
    }
    public boolean isValid(String s) {
        if(s==null||s.length()==0){
            return false;
        }
        char[] array=s.toCharArray();
        Stack<Character> stack=new Stack<>();
        for(int i=0;i<array.length;i++){
            char c=array[i];
            Character pre=stack.isEmpty()?null:stack.peek();
            if(pre!=null){
                if(c==')'&&pre=='('){
                    stack.pop();
                    continue;
                }
                if(c==']'&&pre=='['){
                    stack.pop();
                    continue;
                }
                if(c=='}'&&pre=='{'){
                    stack.pop();
                    continue;
                }
            }
            stack.push(c);
        }
        return stack.isEmpty();
    }

    public int findNumberOfLIS(int[] nums) {
        //两个dp数组，一个用来表示以nums[i]结尾的最长递增子序列的长度，另一个来存以nums[i]结尾的最长递增子序列的个数
        if(nums==null||nums.length==0){
            return 0;
        }
        int[] lengthDp=new int[nums.length];
        int[] countDp=new int[nums.length];
        Arrays.fill(lengthDp,1);
        Arrays.fill(countDp,1);
        int maxLengthIndex=0;
        int maxLength=1;
        for(int i=1;i<nums.length;i++){
            for(int j=0;j<i;j++){
                if(nums[i]>nums[j]&&lengthDp[j]+1>lengthDp[i]){
                    lengthDp[i]=lengthDp[j]+1;
                    countDp[i]=countDp[i-1];
                }else if(nums[i]==nums[j]){
                    if(lengthDp[i]<lengthDp[j]){
                        lengthDp[i]=lengthDp[j];
                    }
                    //这里只记录以num[i]结尾的最大子序列的长度，不记num[j],所以在返回的时候需要遍历一遍
                    countDp[i]=countDp[j];
                }
            }

            if(maxLength>lengthDp[i]){
                maxLength=lengthDp[i];
            }
        }
        int sum=0;
        for(int i=0;i<lengthDp.length;i++){
            if(lengthDp[i]==maxLength){
                sum+=countDp[i];
            }
        }
        return sum;
    }
    public int findLength(int[] A, int[] B) {
        int n = A.length, m = B.length;
        int ret = 0;
        for (int i = 0; i < n; i++) {
            int len = Math.min(m, n - i);
            int maxlen = maxLength(A, B, i, 0, len);
            ret = Math.max(ret, maxlen);
        }
        for (int i = 0; i < m; i++) {
            int len = Math.min(n, m - i);
            int maxlen = maxLength(A, B, 0, i, len);
            ret = Math.max(ret, maxlen);
        }
        return ret;
    }

    public int maxLength(int[] A, int[] B, int addA, int addB, int len) {
        int ret = 0, k = 0;
        for (int i = 0; i < len; i++) {
            if (A[addA + i] == B[addB + i]) {
                k++;
            } else {
                k = 0;
            }
            ret = Math.max(ret, k);
        }
        return ret;
    }

    public boolean checkSubarraySum(int[] nums, int k) {
        if(nums==null||nums.length<2){
            return false;
        }
        int[] prefixSum=new int[nums.length];
        prefixSum[0]=nums[0];
        //求出前缀和
        for(int i=1;i<nums.length;i++){
            prefixSum[i]=prefixSum[i-1]+nums[i];
        }
        Map<Integer,Integer> value2Index=new HashMap<>();
        for(int i=0;i<prefixSum.length;i++){
            Integer temp=prefixSum[i]%k;
            Integer preIndex=value2Index.get(temp);
            if(preIndex!=null&&i-preIndex>=1){
                return true;
            }else if(preIndex==null){
                value2Index.put(temp,i);
            }

        }
        return false;

    }
    private int maxLength=1;
    private  int mid=0;
    public String longestPalindrome(String s) {
        if(s.length()<2){
            return s;
        }
        //boolean[][] dp=new boolean[s.length()][s.length()];
        char[] array=s.toCharArray();
        for(int i=0;i<array.length;i++){
            int l1=export(array,i,i)-1;
            int l2=export(array,i,i+1);
            if(maxLength<Math.max(l1,l2)){
                maxLength=Math.max(l1,l2);
                mid=i;
            }
        }
        return s.substring(mid-((maxLength-1)/2),maxLength);
    }

    private int export(char[] array,int a,int b){
        int length=0;
        while(a>=0&&b<array.length&&array[a]==array[b]){
            length++;
            a--;
            b++;
        }
        return length*2;
    }
    public int[] sortArray(int[] nums) {
        if(nums==null||nums.length==0){
            return nums;
        }
        quickSort(nums,0,nums.length-1);
        return nums;
    }
    private void quickSort(int[] nums,int start,int end){
        if(start>=end){
            return;
        }
        int middle=getMiddle(nums,start,end);
        quickSort(nums,start,middle-1);
        quickSort(nums,middle+1,end);
    }

    private int getMiddle(int[] nums,int start,int end){
        int middleValue=nums[start];
        while(start<=end){
            while(start<=end&&nums[end]>middleValue){
                end--;
            }
            nums[start]=nums[end];
            while (start<end&&nums[start]<=middleValue){
                start++;
            }
            nums[end]=nums[start];
        }
        nums[start]=middleValue;
        return start;
    }


    public ListNode sortList(ListNode head) {
        if (head == null) {
            return head;
        }

        int length = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            length++;
        }
        ListNode temp = new ListNode(-1);
        temp.next = head;
        ListNode pre = temp;
        ListNode newhead = null;
        cur = head;

        for (int size = 1; size < length; size *= 2) {
            cur = temp.next;
            pre = temp;
            while (cur != null) {
                int i = 1;
                ListNode head1 = cur;
                cur = head1;
                while (i < size && cur.next != null) {
                    cur = cur.next;
                    i++;
                }
                ListNode head2 = cur.next;
                cur.next = null;
                i = 1;
                cur = head2;
                while (i < size && cur != null && cur.next != null) {
                    cur = cur.next;
                    i++;
                }
                ListNode next = null;
                if (cur != null) {
                    next = cur.next;
                    cur.next = null;
                }
                newhead = merge(head1, head2);
                pre.next = newhead;
                cur = newhead;
                while (cur != null) {
                    pre = cur;
                    cur = cur.next;
                }
                cur = next;
            }
        }
        return temp.next;
    }

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode newHead = new ListNode(-1);
        ListNode cur = newHead;
        while (head1 != null && head2 != null) {
            if (head1.val < head2.val) {
                cur.next = head1;
                head1 = head1.next;
            } else {
                cur.next = head2;
                head2 = head2.next;
            }
            cur = cur.next;
        }
        if (head1 != null || head2 != null) {
            cur.next = head1 == null ? head2 : head1;
        }
        return newHead.next;
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        HashMap<Character, Integer> char2Index = new HashMap<>();
        int maxLength = 0;
        int start = 0;
        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);
            Integer preIndex = char2Index.get(c);
            if (preIndex == null) {
                char2Index.put(c, i);
            } else {
                start = preIndex;
            }
            char2Index.put(c, i);
            maxLength = Math.max(maxLength, i - start + 1);

        }
        return maxLength;
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null && fast != slow) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    public void flatten(TreeNode root) {
        unflod(root);

    }

    public TreeNode unflod(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        TreeNode end = null;
        if (left != null) {
            TreeNode endLeft = unflod(left);
            if (endLeft == null) {
                end = root;
            } else {
                root.left = null;
                root.right = left;
                endLeft.right = right;
                if (right == null) {
                    end = endLeft;
                } else {
                    end = right;
                }

            }
        }
        if (right != null) {
            end = unflod(right);
        }
        if (right == null && left == null) {
            end = root;
        }
        return end;
    }

    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head, fast = head;
        ListNode current = head, pre = null;
        while (fast != null && fast.next != null) {
            current = slow;
            slow = slow.next;
            fast = fast.next.next;
            current.next = pre;
            pre = current;
        }
        ListNode mid = slow;
        current = pre;
        if (fast != null) {
            slow = slow.next;
        }
        while (pre != null && slow != null) {
            if (pre.val != slow.val) {
                return false;
            }
            pre = pre.next;
            slow = slow.next;
        }
        //恢复链表
        pre = mid;
        while (current != null) {
            ListNode temp = current.next;
            current.next = pre;
            pre = current;
            current = temp;
        }
        return true;

    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
