public class removeDuplicates {
    //设置两个指针p和q,
    //q指针循环向后移动，判断对应数组元素值是否与p指向的元素值相等
    //若相等则q指针向后移一位，直到找到不相等的元素
    //若不等，则把p指针后移一位，将q指向的元素值赋给p指向的元素值
    //然后q指针后移一位
    public int remove(int[] nums) {
        int p=0,q=1;
        while(q<nums.length) {
            if(nums[p]!=nums[q]) {
                p++;
                //如果此时p等于q，则说明数组中没有重复元素，避免重复的赋值
                if(p!=q) {
                    nums[p]=nums[q];
                }
            }
            q++;
        }
        return p+1;
    }

    public static void main(String[] args) {
        int[] nums=new int[5];
        nums[0]=0;
        nums[1]=0;
        nums[2]=1;
        nums[3]=2;
        nums[4]=2;
        removeDuplicates r = new removeDuplicates();
        int len = r.remove(nums);
        for (int i = 0; i < len; i++) {
            System.out.println(nums[i]);
        }
    }
}
