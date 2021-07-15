package com.shy;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * Created by shy on 2021/6/9.
 */
public class DepartmentTest {
    public int[][] merge(int[][] intervals) {
        if(intervals==null||intervals.length==0||intervals[0].length==0){
            return null;
        }
        Arrays.sort(intervals,new Comparator<int[]>(){
            public int compare(int[] nums1,int[] nums2){
                return nums1[0]-nums2[0];
            }
        });
        List<int[]> result=new ArrayList<>();
        for(int i=0;i<intervals.length;i++){
            if(result.isEmpty()){
                result.add(intervals[i]);
            }else{
                int[] pre=result.get(result.size());
                if(intervals[i][1]>pre[1]){
                    result.add(intervals[i]);
                }else{
                    pre[1]=intervals[i][1];
                    result.add(result.size(),pre);
                }
            }
        }
        return result.toArray(new int[result.size()][2]);
    }

    private int findLengthFromFixedIndex(int[] nums1,int[] nums2,int index1,int index2,int sublength){
        int max=0;
        int k=0;
        for(int i=0;i<sublength;i++){
            if(nums1[index1+i]==nums2[index2+i]){
                k++;
            }else{
                k=0;
            }
            max=Math.max(max,k);
        }
        return max;
    }
    public static void main(String[] args) {
        List<Department> allDepartment = new ArrayList<>();
        Department dep1 = new Department(1, 0, "北京总部");
        Department dep3 = new Department(3, 1, "研发中心");
        Department dep4 = new Department(4, 3, "后端研发组");
        Department dep6 = new Department(6, 4, "后端实习生组");
        Department dep7 = new Department(7, 3, "前端研发组");
        Department dep8 = new Department(8, 1, "产品部");

        allDepartment.add(dep6);
        allDepartment.add(dep7);
        allDepartment.add(dep8);
        allDepartment.add(dep1);
        allDepartment.add(dep3);
        allDepartment.add(dep4);


        List<Department> subDepartments = DepartmentTest.getSub(3, allDepartment);
        for (Department subDepartment : subDepartments) {
            System.out.println(subDepartment);
        }
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            //System.out.println(list.size());
            if ("1".equals(list.get(i))){
                list.add("4");
                list.add("5");
                list.remove("1");
            }
        }
    }
    /**
     * 根据id，获取所有子部门列表(包括隔代子部门)
     * @param id
     * @return
     */
    public static List<Department> getSub(int id, List<Department> allDepartment) {
        if(CollectionUtils.isEmpty(allDepartment)){
            return Collections.emptyList();
        }
        List<Department> result=Lists.newArrayList();
        Queue<Integer> queue=new LinkedList<>();
        queue.offer(id);
        while(!queue.isEmpty()){
            for(Department department:allDepartment){
                Integer pid=queue.peek();
                if(department.getPid()==pid){
                    result.add(department);
                    queue.offer(department.getId());
                }
            }
            queue.poll();
        }
        return result;
    }
}
class Department {
    /** id */
    private int id;
    /** parent id */
    private int pid;
    /** 名称 */
    private String name;
    public Department(int id, int pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPid() {
        return pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                '}';
    }
}
