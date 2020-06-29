package container.collections;

import java.util.*;


public class SortCollections {

    private static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if(nums.length < 3){
            return result;
        }
        Arrays.sort(nums); //排序, 为双指针方法做铺垫
        Set<Integer> dpiset = new HashSet<Integer>(); //去重i
        for(int i = 0; i < nums.length - 2; i++){
            Set<Integer> dpjset = new HashSet<Integer>(); //去重j
            Set<Integer> dpnset = new HashSet<Integer>(); //去重n
            if(nums[i] > 0) break;    //此时三个数都为大于0的数，和不可能为0
            if(dpiset.contains(nums[i])) continue;
            dpiset.add(nums[i]);
            for(int j = i+1, n = nums.length -1; j < n; ){
                // 双指针移动， 如果有重复，则直接移动，否则逐个进行比较
                if(dpjset.contains(nums[j])){
                    j = j+1;
                    continue;
                }
                if(dpnset.contains(nums[n])){
                    n = n-1;
                    continue;
                }
                if(nums[i]+nums[j]+nums[n] == 0 ){
                    List<Integer> childResult = new ArrayList<>();
                    childResult.add(nums[i]);
                    childResult.add(nums[j]);
                    childResult.add(nums[n]);
                    result.add(childResult);
                    dpjset.add(nums[j]);
                    dpnset.add(nums[n]);
                    j = j + 1;
                    n = n -1;

                }else if(nums[i]+nums[j]+nums[n] < 0){
                    dpjset.add(nums[j]);
                    j = j + 1;
                }else if(nums[i]+nums[j]+nums[n] > 0){
                    dpnset.add(nums[n]);
                    n = n-1;
                }

            }
        }


        return result;
    }


    public static void main(String[] args) {
        int[] test = {1, -1, -1, 0};
        Arrays.sort(test);
        for(int i=0; i< test.length; i++){
            System.out.println(test[i]);
        }

        SortCollections.threeSum(test);
    }
}
