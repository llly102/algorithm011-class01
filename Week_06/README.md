### 递归、分治和动态规划                          
#### 递归                             
递归代码模板                                 

```java
// Java
public void recur(int level, int param) { 
  // terminator 终止条件
  if (level > MAX_LEVEL) { 
    // process result 
    return; 
  }
  // process current logic 当前层
  process(level, param); 
  // drill down 下一层
  recur( level: level + 1, newParam); 
  // restore current status 恢复当前层状态
}
```
把大问题分解成子问题                                              ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200731191704728.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
#### 分治                     
代码模板                         

```java
//Java
private static int divide_conquer(Problem problem,param1,param2,…… ) {
//递归终止条件
  if (problem == NULL) {
    int res = process_last_result();
    return res;     
  }
  //拆分子问题
  subProblems = split_problem(problem)
  //递归求每个子问题的结果
  res0 = divide_conquer(subProblems[0])
  res1 = divide_conquer(subProblems[1])
  //结果合并
  result = process_result(res0, res1);
  
  return result;
  // restore current status 恢复当前层状态
}

```
#### 注意                                                                 
1. 避免人肉递归。                                                       
2. 找到最近最简方法，将其拆解成可重复解决的问题。                                                                 
3. 数学归纳法思维。                                                        
4. 寻找重复性。                   
5. 当遇到不熟悉的递归问题时，可以画一下递归树便于理解。                                                 

#### 动态规划（Dynamic Programming）                       
动态规划：分治+最优子结构                                        
在每一步中只存储最优的结果，淘汰次优解。                      
##### 关键点                                  
1. 动态规划和递归、分治没有根本上的区别（关键看有没有最优子结构）。                      
2. 共性：找到重复子问题。                               
3. 差异性：最优子结构、中途可以淘汰次优解。                        
寻找动态规划方程。  
##### 动态规划：状态转移方程                           
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200801123917554.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
                                                           
#### 例题                       
##### Fibonacci数列                      
不进行缓存的递归时间复杂度是指数级的（每次用到都要去递归算一次）。O(2^n)                              

```java
int fib(int n){
    if(n<=1){
        return n;
    }
    else return fib(n-1) + fib(n-2);
}
```

进行记忆化搜索。O(n)                            

```java
int fib(int n){
    if(n<=1){
        return n;
    }
    if(mem[n] == 0){
        memo[n]=fib(n-1) + fib(n-2);
    }
    return memo[n];
}
```
使用循环                              

```java
int fib(int n){
    if(n<=1){
        return n;
    }
    int[] fib = new int[n];
    fib[0]=0;
    fib[1]=1;
    for(int i=2;i<=n;i++){
        fib[i]=fib[i-1]+fib[i-2];
    }
    return fib[n];
}

```
##### 路径计数                            
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020073122224543.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
 划分成子问题                              
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020073122242268.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
动态规划：状态转移方程：dp[i,j]=dp[i+1,j]+dp[i,j+1]                            

##### 最长公共子序列（力扣1143）

![](C:\Users\LY\Documents\360截图\360截图20200801172857755.jpg)

![](C:\Users\LY\Documents\360截图\360截图20200801175215875.jpg)

动态规划方程：

![360截图20200801175312479](C:\Users\LY\Documents\360截图\360截图20200801175312479.jpg)



##### 三角形最小路径和（力扣120）

a. 找重复性（分治）。problem(i,j)=sub(i,j)+min(sub(i+1,j) , sub(i,j+1))

b. 定义状态数组。f[i,j]

c. DP方程。f[i,j] = min(f[i+1 , j] , f[i+1 , j+1]) + a[i , j]

```java
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int row=triangle.size();
        //定义minS的长度为row+1，是为了下面第一次循环中的j+1不越界
        int[] minS = new int[row+1];
        for(int i=row-1;i>=0;i--){
            //第i层有i+1个元素，所以j<i+1
            for(int j=0;j<i+1;j++){
                //使用minS来存储每一行中元素向下的最小路径
                minS[j] = Math.min(minS[j],minS[j+1]) + triangle.get(i).get(j);
            }
        }
        return minS[0];
    }
}
```

##### 最大子序列和（力扣53）

![](C:\Users\LY\Documents\360截图\360截图20200802155206347.jpg)



第一种：暴力求解，枚举所有起点和终点。O(n^2)

第二种：动态规划DP：

a. 分治（子问题）：max_sum(i) = Max(max_sum(i-1),0) + a[i] 

b. 状态数组定义：f[i]

c. DP方程：f[i] = Max(f[i-1] , 0) + a[i]

最大子序列和：当前元素自身最大，或者包含之前的最大，再加上自己之后最大。舍弃所有的负值，即之前的子序列和如果小于0，就不要它，从自己开始重新计算。

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int[] dp = nums;
        int res=dp[0];
        for(int i=1;i<nums.length;i++){
            dp[i] = Math.max(dp[i-1],0) + nums[i];
            //找dp中的最大值即为结果
            if(dp[i]>res){
                res=dp[i];
            }
        }
        return res;
    }
}
```

##### 零钱兑换（力扣322）

![](C:\Users\LY\Documents\360截图\360截图20200802161957759.jpg)

第一种：暴力递归：相当于爬楼梯问题

第二种：广度优先遍历

![](C:\Users\LY\Documents\360截图\360截图20200802162549235.jpg)

第三种：DP

a. 子问题：

b. DP数组：f(n) = min (f(n-k))  +1, for k in [1,2,5] 

c. DP方程

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        //max=amount+1表示兑换的最大值，包含不可兑换的情况。
        int max = amount+1;
        int[] dp = new int[amount+1];
        Arrays.fill(dp,max);
        //dp[0]=0为初始化
        dp[0]=0;
        for(int i=1;i<=amount;i++){
            //k表示coins中可以兑换的硬币面额
            for(int k : coins){
                //如果k>i,那么硬币面额大于要兑换的总额，说明不可兑换
                if(k <= i) {
                    dp[i] = Math.min(dp[i],dp[i-k]+1); 
                }
            }
        }
        return dp[amount]>amount ? -1 : dp[amount];

    }
}
```

##### 打家劫舍（力扣198）

![](C:\Users\LY\Documents\360截图\360截图20200802173609527.jpg)

DP：

a. 子问题

b. 状态数组：a[i]表示0~i之间能偷到的最大值。

c. DP方程：

```java
第一种：增加一个维度，表示第i个房子偷不偷
a[i][0 / 1]  : 0表示偷i，1表示不偷i

a[i][0] = max(a[i-1][0], a[i-1][1])

a[i][1] = a[i-1][0] + nums[i]
第二种：不增加维度
a[i] = max(a[i-1] , a[i-2]+nums[i])。分为i-1偷和i-1不偷。
```

```java
class Solution {
    public int rob(int[] nums) {
        int len = nums.length;
        if(len==0) return 0;
        if(len==1) return nums[0];
        int[][] dp = new int[len][2];
        dp[0][0]=0;
        dp[0][1]=nums[0];
       
        for(int i=1;i<len;i++){
            dp[i][0] = Math.max(dp[i-1][0],dp[i-1][1]);
            dp[i][1] = dp[i-1][0] + nums[i];
           
        }
        return Math.max(dp[len-1][0],dp[len-1][1]);

    }
}
```

```java
class Solution {
    public int rob(int[] nums) {
        int len = nums.length;
        if(len==0) return 0;
        if(len==1) return nums[0];
        int[] dp = new int[len];
        dp[0]=nums[0];
        dp[1]=Math.max(nums[0],nums[1]);
         
        int res = Math.max(dp[1],dp[0]);
       
        for(int i=2;i<len;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2]+nums[i]);
            res = Math.max(dp[i],res);           
        }
        return res;

    }
}
```



#### 小结

1. 打破思维惯性，形成机器思维：找重复性。

2. 理解复杂逻辑的关键。

3. 职业进阶的要点。

   #### 动态规划四步：

   1. 定义分治，找子问题。
   2. 猜递推方程。
   3. 合并子问题的解。
   4. 递归和记忆化或建立动态规划状态表，自底向上进行递推。