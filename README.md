# 期末总结

## 数据结构

### 一维数据结构

#### 基础

1. 数组
2. 链表：单链表、双向链表、循环链表
3. 跳表

#### 高级

1. 栈(stack)：递归，维护单调栈（接雨水）
2. 队列(queue)
3. 双端队列(deque)：滑动窗口
4. 集合(set)
5. 哈希表(hash map)

### 二维数据结构

#### 基础

1. 树Tree
2. 图Graph

#### 高级

1. 二叉搜索树：包括红黑树、AVL树

2. 堆Heap：大顶堆，小顶堆。优先队列实现

3. 并查集UnionFind：分组问题，有几类。

   ```java
   class UnionFind { 
   	private int count = 0; 
   	private int[] parent; 
   	public UnionFind(int n) { 
   		count = n; 
   		parent = new int[n]; 
   		for (int i = 0; i < n; i++) { 
   			parent[i] = i;
   		}
   	} 
   	public int find(int p) { 
   		while (p != parent[p]) { 
   			parent[p] = parent[parent[p]]; 
   			p = parent[p]; 
   		}
   		return p; 
   	}
   	public void union(int p, int q) { 
   		int rootP = find(p); 
   		int rootQ = find(q); 
   		if (rootP == rootQ) return; 
   		parent[rootP] = rootQ; 
   		count--;
   	}
   }
   ```

4. 字典树Trie：词频统计，单词搜索。

   ```java
   class Trie {
       private boolean isEnd;
       private Trie[] next;
       /** Initialize your data structure here. */
       public Trie() {
           isEnd = false;
           next = new Trie[26];
       }
       
       /** Inserts a word into the trie. */
       public void insert(String word) {
           if (word == null || word.length() == 0) return;
           Trie curr = this;
           char[] words = word.toCharArray();
           for (int i = 0;i < words.length;i++) {
               int n = words[i] - 'a';
               if (curr.next[n] == null) curr.next[n] = new Trie();
               curr = curr.next[n];
           }
           curr.isEnd = true;
       }
       
       /** Returns if the word is in the trie. */
       public boolean search(String word) {
           Trie node = searchPrefix(word);
           return node != null && node.isEnd;
       }
       
       /** Returns if there is any word in the trie that starts with the given prefix. */
       public boolean startsWith(String prefix) {
           Trie node = searchPrefix(prefix);
           return node != null;
       }
   
       private Trie searchPrefix(String word) {
           Trie node = this;
           char[] words = word.toCharArray();
           for (int i = 0;i < words.length;i++) {
               node = node.next[words[i] - 'a'];
               if (node == null) return null;
           }
           return node;
       }
   }
   ```

#### 特殊

1. 位运算

![image-20200829173219794](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200829173219794.png)

![image-20200829173237358](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200829173237358.png)

1. 布隆过滤器：不存储元素本身，当一个元素的二进制索引有0存在，这个元素一定不在存储中，但即使一个元素的二进制索引全为1，也不能确定这个元素就在内存中。
2. LRU Cache

## 时间复杂度

![image-20200829174000729](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200829174000729.png)

## 算法

### 递归

```
public void recur(int level, int param) { 
  // terminator 
  if (level > MAX_LEVEL) { 
    // process result 
    return; 
  }
  // process current logic 
  process(level, param); 
  // drill down 
  recur( level: level + 1, newParam); 
  // restore current status 
}
```

### 分治、回溯、贪心算法

```
private static int divide_conquer(Problem problem, ) {
  
  if (problem == NULL) {
    int res = process_last_result();
    return res;     
  }
  subProblems = split_problem(problem)
  
  res0 = divide_conquer(subProblems[0])
  res1 = divide_conquer(subProblems[1])
  
  result = process_result(res0, res1);
  
  return result;
}
```

### 动态规划

1. 最优子结构
2. 动态方程
3. 动态递归的开始定义

### 搜索

#### 深度优先搜索DFS

```
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

#### 广度优先搜索BFS

```
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

### 二分查找

```
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

## 做题方法：

### 五毒神掌

1. 不要死磕
2. 刻意练习：练习不熟悉的地方，走出舒适区
3. 看别人的好的题解
4. 多次练习，过遍数。

## 面试

### 做题

1. 明确题目意思，确定边界、数据规模
2. 穷尽所有可能的方法：到最好的方法
3. 写代码：简介高效，美感
4. 测试：normal case / corner case / failure case / stress case 

### 调整面试观念

1. 和未来同事的一次合作
2. 共同解决问题：沟通、配合
3. 减少压力

