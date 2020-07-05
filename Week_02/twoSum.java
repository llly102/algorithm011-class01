import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class twoSum {
    public static void main(String[] args) {
        twoSum ts = new twoSum();
        Scanner in = new Scanner(System.in);
        System.out.println("输入数组长度：");
        int len = in.nextInt();
        int[] nums = new int[len];
        System.out.println("输入数组元素：");
        for (int i = 0; i < len; i++) {
            nums[i] = in.nextInt();

        }
        System.out.println("输入目标值：");
        int target = in.nextInt();

        System.out.println("数组中是否有两数之和为目标值？");
        int[] res = new int[2];
        res =ts.twoSum(nums, target);
        for (int i:res) {
            System.out.println(i);
        }

    }

    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[0];

    }
}
