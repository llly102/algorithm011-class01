public class twoSum {
        public int[] twoSum(int[] nums, int target) {
            int[] result = new int[2];
            for(int i=0;i<nums.length-1;i++){
                for(int j=i+1;j<nums.length;j++){
                    if(nums[i]+nums[j]==target){
                        result[0]=i;
                        result[1]=j;
                        return result;
                    }
                }
            }
            return new int[0];
        }


    public static void main(String[] args) {
        int[] nums=new int[5];
        int[] result;
        int target=5;
        nums[0]=1;
        nums[1]=5;
        nums[2]=3;
        nums[3]=2;
        nums[4]=1;
        twoSum s = new twoSum();
        result = s.twoSum(nums,target);
        System.out.println("下标为"+result[0]+"的值："+nums[result[0]]+"和下标为"+result[1]+"的值："+nums[result[1]]+"相加为target："+target);
    }
}
