public class moveZeros {
    public static void main(String[] args) {
        int[] nums = new int[5];
        nums[0] = 1;
        nums[1] = 0;
        nums[2] = 0;
        nums[3] = 2;
        nums[4] = 0;
        moveZeros m = new moveZeros();
        m.moveZeroes(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }

    }

    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) return;

        int j = 0;
        for (int num : nums) {
            if (num != 0) nums[j++] = num;
        }

        while (j < nums.length) {
            nums[j++] = 0;
        }

    }
}
