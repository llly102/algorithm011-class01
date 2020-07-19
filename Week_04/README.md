### 深度优先搜索和广度优先搜索                             
#### 遍历搜索                      
在树、图或者状态集中寻找特定结点：                              
结点定义：    

```java
public class TreeNode {
    public int val;
    public TreeNode left,right;
    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}
```

遍历搜索：每个结点都要访问一次且只能访问一次。                         
结点的访问顺序不限： 分为深度优先（DFS）和广度优先（BFS），优先级优先（启发式搜索，更适用于现实中应用。）                                                 
##### 深度优先搜索
保证要访问的结点没有被访问过。                   
递归示例代码：                             
```java
public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> allResults = new ArrayList<>();
        if(root==null){
            return allResults;
        }
        travel(root,0,allResults);
        return allResults;
    }

    private void travel(TreeNode root,int level,List<List<Integer>> results){
        if(results.size()==level){
            results.add(new ArrayList<>());
        }
        results.get(level).add(root.val);
        if(root.left!=null){
            travel(root.left,level+1,results);
        }
        if(root.right!=null){
            travel(root.right,level+1,results);
        }
    }     
```
非递归深度优先搜索：使用一个栈来存储结点，将已访问过的结点出栈，记录其值，再将它的孩子结点依次入栈，进行循环，直到栈空。                                             
  
##### 广度优先遍历                            
使用队列。                                    
![深度优先和广度优先](https://img-blog.csdnimg.cn/2020071822035924.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)

```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> allResults = new ArrayList<>();
    if (root == null) {
        return allResults;
    }
    Queue<TreeNode> nodes = new LinkedList<>();
    nodes.add(root);
    while (!nodes.isEmpty()) {
        int size = nodes.size();
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = nodes.poll();
            results.add(node.val);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right != null) {
                nodes.add(node.right);
            }
        }
        allResults.add(results);
    }
    return allResults;
}
```

### 贪心算法
在每一次选择中都采取在当前状态下最好或最优的选择，从而希望得到结果时全局最好或最优的算法。                       
贪心算法与动态规划的不同在于它对每个子问题的解决方案都做出选择，不能回退。而动态规划则会保存以前的运算结果，并根据以前的结果对挡墙进行选择，可以回退。                         
即：                       
贪心：当下做局部最优判断                             
回溯：可以回退                          
动态规划：最优判断+回退    
                                         
贪心算法可以解决一些最优化问题，如图中的最小生成树，求哈夫曼编码等。但是对工程和生活中的问题，贪心法一般得不到最优解。
                                                             
如果一个问题可以使用贪心法解决，那么贪心法一般是解决这个问题的最好办法。
                                                           
由于贪心法的高效性和其所求得的答案比较接近最优结果，贪心法可以作为辅助算法或直接解决一些要求结果不特别精确的问题。  
                    
##### 使用贪心算法的场景
问题能够分解成子问题来解决，子问题的最优解能地推到最终问题的最优解，这种子问题最优解称为最优子结构。
                           
注意：                        
证明问题可以使用贪心法；贪心从哪里开始？                          

### 二分查找
#### 二分查找的前提
1. 目标函数单调性（单调递减或者单调递增）。                              
2.  存在上下界。                    
3.  能够通过索引访问。                                      
                                            
代码模板：                                                               
```java
public int binarySearch(int[] array, int target) {
    int left = 0, right = array.length - 1, mid;
    while (left <= right) {
        mid = (right - left) / 2 + left;

        if (array[mid] == target) {
            return mid;
        } else if (array[mid] > target) {
            right = mid - 1;
        } else {
            left = mid + 1;
        }
    }
    return -1;
}
```
                                          
##### 牛顿迭代法求平方根
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200719134330314.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)

```java
class Solution {
    public int mySqrt(int x) {
        long r = (long)x;
        while(r*r>x){
        //不断迭代更新r
            r = (r + x/r)/2;
        }
        //当r*r<=x时，说明找到了x的平方根的整数部分
        return (int)r;
    }
}
```

##### 使用二分查找，寻找一个半有序数组 [4, 5, 6, 7, 0, 1, 2] 中间无序的地方                     
此题同寻找旋转排序数组中的最小值，通过二分查找找到这个旋转排序数组中的无序位置。   
 
```java
class Solution {
    public int findMin(int[] nums) {
        int left=0,right=nums.length-1;
        while(left < right){
            int mid = (left + right) / 2;
            //说明，mid之前的是有序递增的，最小值在右半部分
            if(nums[mid]>nums[right]){
                left=mid+1;
            }
            //说明右半部分是有序递增的，最小值在左半部分，也可能是mid位置上的
            else if(nums[mid] < nums[right]) {
                right = mid;
            }
            else right--;
        }
        return nums[left];

    }
}
```

                                                                                                                      
                                                                                                  
