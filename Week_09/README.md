# 高级动态规划

## 递归、回溯、 分治

![image-20200819121659862](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819121659862.png)

### 总结

1. 找最近最简问题，最近重复性问题。
2. 数学归纳思维
3. 避免人肉递归

## 动态规划

![image-20200819122111262](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819122111262.png)



### 注意：

1. dp状态的初始化
2. 状态转移方程的定义

![image-20200819122220610](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819122220610.png)

## 常见DP问题和状态转移方程

### 爬楼梯问题

![image-20200819122325999](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819122325999.png)



### 不同路径

![image-20200819122427526](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819122427526.png)

### 打家劫舍

![image-20200819122640893](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819122640893.png)

### 最小路径和

![image-20200819154337805](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819154337805.png)

### 股票买卖

```java
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n=prices.length;
        if(n==0||k==0) return 0;
        if(k > n/2) return maxProfit_k_inif(k , prices);

        int[][][] dp = new int[n][k+1][2];
        for(int i=0;i<n;i++){
            //0次交易，不持有股票，收益为0
            dp[i][0][0] = 0;
            //0次交易，持有股票，不可能
            dp[i][0][1] = Integer.MIN_VALUE;
            for(int j=1;j<=k;j++){
                if(i==0) {
                    //第1天，交易j次，最后不持有股票，收益为0
                    dp[i][j][0] = 0;
                    //第1天，交易j次，最终持有股票，收益为-prices[i]
                    dp[i][j][1] = -prices[i];
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i-1][j][0] , dp[i-1][j][1]+prices[i]);
                dp[i][j][1] = Math.max(dp[i-1][j][1] , dp[i-1][j-1][0]-prices[i]);
            }
        }
        return dp[n-1][k][0];
    }
    //当k>n/2时，不受k的限制，
    public int maxProfit_k_inif(int k , int[] prices){
        int n = prices.length;
        int[][] dp = new int[n][2];
        for(int i=0;i<n;i++){
            if(i==0) {
                dp[i][0] = 0;
                dp[i][1] = -prices[i]; 
            }
            else{
                dp[i][0] = Math.max(dp[i-1][0] , dp[i-1][1]+prices[i]);
                dp[i][1] = Math.max(dp[i-1][1] , dp[i-1][0]-prices[i]);
            }            
        }
        return dp[n-1][0];
    }
}
```

## 高级动态规划

![image-20200819183123514](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819183123514.png)

### 编辑距离

![image-20200819210517903](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200819210517903.png)

# 字符串基础知识

## 字符串转换整数

```
public int myAtoi(String str) {
    int index = 0, sign = 1, total = 0;
    //1. Empty string
    if(str.length() == 0) return 0;
    //2. Remove Spaces
    while(str.charAt(index) == ' ' && index < str.length())
        index ++;
    //3. Handle signs
    if(str.charAt(index) == '+' || str.charAt(index) == '-'){
        sign = str.charAt(index) == '+' ? 1 : -1;
        index ++;
    }
    
    //4. Convert number and avoid overflow
    while(index < str.length()){
        int digit = str.charAt(index) - '0';
        if(digit < 0 || digit > 9) break;
        //check if total will be overflow after 10 times and add digit
        if(Integer.MAX_VALUE/10 < total ||            
        	Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        total = 10 * total + digit;
        index ++;
    }
    return total * sign;
}
```

## 最长公共前缀

![image-20200820165424276](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200820165424276.png)

# 高级字符串算法

最长回文子串：

![image-20200820182938900](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200820182938900.png)

# 字符串匹配算法

## 朴素算法（暴力解法）

```java
public static int forceSearch(String txt, String pat) {
    int M = txt.length();
    int N = pat.length();
    for (int i = 0; i <= M - N; i++) {
        int j;
        for (j = 0; j < N; j++) {
            if (txt.charAt(i + j) != pat.charAt(j))
                break;
        }
        if (j == N) {
            return i;
        }
        // 更加聪明？ 
        // 1. 预先判断 hash(txt.substring(i, M)) == hash(pat)
        // 2. KMP 
    }
    return -1;
}
```

## Boyer-Moore算法

![image-20200820211124032](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200820211124032.png)

![image-20200820211250798](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200820211250798.png)

```java
public final static int D = 256;
public final static int Q = 9997;

static int RabinKarpSerach(String txt, String pat) {
    int M = pat.length();
    int N = txt.length();
    int i, j;
    int patHash = 0, txtHash = 0;

    for (i = 0; i < M; i++) {
        patHash = (D * patHash + pat.charAt(i)) % Q;
        txtHash = (D * txtHash + txt.charAt(i)) % Q;
    }

    int highestPow = 1;  // pow(256, M-1)
    for (i = 0; i < M - 1; i++) 
        highestPow = (highestPow * D) % Q;

    for (i = 0; i <= N - M; i++) { // 枚举起点
        if (patHash == txtHash) {
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j))
                    break;
            }
            if (j == M)
                return i;
        }
        if (i < N - M) {
            //更新txtHash的值
            txtHash = (D * (txtHash - txt.charAt(i) * highestPow) + txt.charAt(i + M)) % Q;
            if (txtHash < 0)
                txtHash += Q;
        }
    }

    return -1;
}
```

## KMP算法

![image-20200820211841766](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200820211841766.png)

