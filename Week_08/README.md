# 位运算

## 位运算符

![image-20200811183046615](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200811183046615.png)

![image-20200811183203695](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200811183203695.png)

![image-20200811183300916](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200811183300916.png)

![](C:\Users\LY\Documents\360截图\360截图20200811190841965.jpg)

 ![image-20200811190844575](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200811190844575.png

![image-20200811192457511](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200811192457511.png)

### 例题：

#### 位1的个数

```
public class Solution {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int count=0;
        while(n!=0){
            count++;
            //n&(n-1)会消去最低位的1
            n &= (n-1);
        }
        return count;        
    }
}
```

#### 2的幂

一个数是2的幂，说明这个数的二进制表示中有且仅有一个1。

```
class Solution {
    public boolean isPowerOfTwo(int n) {
        //2的幂都大于0，n&(n-1)消去n中最低位的1，如果等于0了，说明是2的幂。
        return n>0 && (n&(n-1))==0;
    }
}
```

#### 颠倒二进制位（力扣190）

```
public class Solution {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int res=0;
        for(int i=0;i<32;i++){
            //res每次左移1位，再加上n的最低位，就会把n的低位变成res的高位
            res= (res<<1) + (n&1);
            n >>= 1;
        }
        return res ;       
    }
}
```

#### N皇后

![image-20200814111817202](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200814111817202.png)



# 布隆过滤器和LRU缓存

## 布隆过滤器(Bloom Filter)

![image-20200813164747726](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200813164747726.png)

布隆过滤器使用二进制表示，是模糊查询。

![image-20200813164931317](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200813164931317.png)

一个元素的二进制索引只要有一位不为1，那么它肯定不在布隆过滤器中；

但是即使一个元素的二进制索引全为1，这个元素也不一定在布隆过滤器中。

![image-20200813165128684](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200813165128684.png)

布隆过滤器只是在访问数据库之前的一个快速访问的缓存。

![image-20200813165401304](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200813165401304.png)

```java
package com.github.lovasoa.bloomfilter;

import java.util.BitSet;
import java.util.Random;
import java.util.Iterator;

public class BloomFilter implements Cloneable {
  private BitSet hashes;
  private RandomInRange prng;
  private int k; // Number of hash functions
  private static final double LN2 = 0.6931471805599453; // ln(2)

  /**
   * Create a new bloom filter.
   * @param n Expected number of elements
   * @param m Desired size of the container in bits
   **/
  public BloomFilter(int n, int m) {
    k = (int) Math.round(LN2 * m / n);
    if (k <= 0) k = 1;
    this.hashes = new BitSet(m);
    this.prng = new RandomInRange(m, k);
  }

  /**
   * Create a bloom filter of 1Mib.
   * @param n Expected number of elements
   **/
  public BloomFilter(int n) {
    this(n, 1024*1024*8);
  }

  /**
  * Add an element to the container
  **/
  public void add(Object o) {
    prng.init(o);
    for (RandomInRange r : prng) hashes.set(r.value);
  }
  /** 
  * If the element is in the container, returns true.
  * If the element is not in the container, returns true with a probability ≈ e^(-ln(2)² * m/n), otherwise false.
  * So, when m is large enough, the return value can be interpreted as:
  *    - true  : the element is probably in the container
  *    - false : the element is definitely not in the container
  **/
  public boolean contains(Object o) {
    prng.init(o);
    for (RandomInRange r : prng)
      if (!hashes.get(r.value))
        return false;
    return true;
  }

  /**
   * Removes all of the elements from this filter.
   **/
  public void clear() {
    hashes.clear();
  }

  /**
   * Create a copy of the current filter
   **/
  public BloomFilter clone() throws CloneNotSupportedException {
    return (BloomFilter) super.clone();
  }

  /**
   * Generate a unique hash representing the filter
   **/
  public int hashCode() {
    return hashes.hashCode() ^ k;
  }

  /**
   * Test if the filters have equal bitsets.
   * WARNING: two filters may contain the same elements, but not be equal
   * (if the filters have different size for example).
   */
  public boolean equals(BloomFilter other) {
    return this.hashes.equals(other.hashes) && this.k == other.k;
  }

  /**
   * Merge another bloom filter into the current one.
   * After this operation, the current bloom filter contains all elements in
   * other.
   **/
  public void merge(BloomFilter other) {
    if (other.k != this.k || other.hashes.size() != this.hashes.size()) {
      throw new IllegalArgumentException("Incompatible bloom filters");
    }
    this.hashes.or(other.hashes);
  }

  private class RandomInRange
      implements Iterable<RandomInRange>, Iterator<RandomInRange> {

    private Random prng;
    private int max; // Maximum value returned + 1
    private int count; // Number of random elements to generate
    private int i = 0; // Number of elements generated
    public int value; // The current value

    RandomInRange(int maximum, int k) {
      max = maximum;
      count = k;
      prng = new Random();
    }
    public void init(Object o) {
      prng.setSeed(o.hashCode());
    }
    public Iterator<RandomInRange> iterator() {
      i = 0;
      return this;
    }
    public RandomInRange next() {
      i++;
      value = prng.nextInt() % max;
      if (value<0) value = -value;
      return this;
    }
    public boolean hasNext() {
      return i < count;
    }
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
}
```



## LRU缓存（最近最少使用）

### LRU Cache 

两个要素：大小，替换策略

实现：Hash Table + Double LinkedList

O(1) 查询、O(1)修改更新

![image-20200813163243563](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200813163243563.png)

![image-20200813164226861](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200813164226861.png)

```java
class LRUCache {
    /**
     * 缓存映射表
     */
    private Map<Integer, DLinkNode> cache = new HashMap<>();
    /**
     * 缓存大小
     */
    private int size;
    /**
     * 缓存容量
     */
    private int capacity;
    /**
     * 链表头部和尾部
     */
    private DLinkNode head, tail;

    public LRUCache(int capacity) {
        //初始化缓存大小，容量和头尾节点
        this.size = 0;
        this.capacity = capacity;
        head = new DLinkNode();
        tail = new DLinkNode();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 获取节点
     * @param key 节点的键
     * @return 返回节点的值
     */
    public int get(int key) {
        DLinkNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        //移动到链表头部
         (node);
        return node.value;
    }

    /**
     * 添加节点
     * @param key 节点的键
     * @param value 节点的值
     */
    public void put(int key, int value) {
        DLinkNode node = cache.get(key);
        if (node == null) {
            DLinkNode newNode = new DLinkNode(key, value);
            cache.put(key, newNode);
            //添加到链表头部
            addToHead(newNode);
            ++size;
            //如果缓存已满，需要清理尾部节点
            if (size > capacity) {
                DLinkNode tail = removeTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            node.value = value;
            //移动到链表头部
            moveToHead(node);
        }
    }

    /**
     * 删除尾结点
     *
     * @return 返回删除的节点
     */
    private DLinkNode removeTail() {
        DLinkNode node = tail.prev;
        removeNode(node);
        return node;
    }

    /**
     * 删除节点
     * @param node 需要删除的节点
     */
    private void removeNode(DLinkNode node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    /**
     * 把节点添加到链表头部
     *
     * @param node 要添加的节点
     */
    private void addToHead(DLinkNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * 把节点移动到头部
     * @param node 需要移动的节点
     */
    private void moveToHead(DLinkNode node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 链表节点类
     */
    private static class DLinkNode {
        Integer key;
        Integer value;
        DLinkNode prev;
        DLinkNode next;

        DLinkNode() {
        }

        DLinkNode(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
}
```

# 初级排序和高级排序

![image-20200814113605533](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200814113605533.png)

非比较类排序只能用于整型排序，而且需要额外的内存。

![image-20200814113824526](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200814113824526.png)

## 初级排序

![image-20200814113901658](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200814113901658.png)

### 选择排序

```
public void SelectionSort(int[] nums) {
        if (nums.length == 0) return;

        int minIndex = 0;
        int temp;
        for (int i = 0; i < nums.length; i++) {
            minIndex=i;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            temp = nums[minIndex];
            nums[minIndex] = nums[i];
            nums[i] = temp;
        }
    }
```

### 插入排序

```java
public void InsertionSort(int[] nums) {
        if (nums.length == 0) return;

        int sortIndex;
        int current;

        //0~i-1区间内的数据是排好序的
        for(int i=1;i<nums.length;i++){
            //sortIndex记录已排好序的最后一个数据的下标
            sortIndex = i-1;
            //current记录当前需要插入排序数组的数字
            current = nums[i];
            //找这个数字要插入的位置
            while(sortIndex>=0 && nums[sortIndex]>current){
                //将大于current的数字都向后移动一位
                nums[sortIndex+1] = nums[sortIndex];
                sortIndex--;
            }
            //sortIndex+1即要插入的位置
            nums[sortIndex+1] = current;
        }
    }
```

### 冒泡排序

```java
public void BubbleSort(int[] nums){
        if(nums.length==0) return;

        for(int i=nums.length-1;i>=0;i--){
            for(int j=0;j<i;j++){
                if(nums[j]>nums[j+1]){
                    int temp = nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=temp;
                }
            }
        }
    }
```



## 高级排序

### 快速排序

![image-20200814114557294](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200814114557294.png)

快排：分治。

代码模板：

```java
public static void quickSort(int[] array, int begin, int end) {
    if (end <= begin) return;
    int pivot = partition(array, begin, end);
    quickSort(array, begin, pivot - 1);
    quickSort(array, pivot + 1, end);
}
static int partition(int[] a, int begin, int end) {
    // pivot: 标杆位置，counter: 小于pivot的元素的个数
    int pivot = end, counter = begin;
    for (int i = begin; i < end; i++) {
        if (a[i] < a[pivot]) {
            //交换两个元素，count标记了第一个大于pivot的元素的位置，如果a[i] < a[pivot]，就交换a[i]和a[count]，就把小于pivot的放在了左边，大于的放在了右边。
            int temp = a[counter]; a[counter] = a[i]; a[i] = temp;
            counter++;
        }
    }
    //此时count是第一个大于pivot元素的位置，交换pivot和count位置上的元素，即可得到左边都小于pivot，右边都大于pivot
    int temp = a[pivot]; a[pivot] = a[counter]; a[counter] = temp;
    return counter;
}
```

### 归并排序

![image-20200814115248230](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200814115248230.png)

代码模板：

```java
public static void mergeSort(int[] array, int left, int right) {
    if (right <= left) return;
    int mid = (left + right) >> 1; // (left + right) / 2

    mergeSort(array, left, mid);
    mergeSort(array, mid + 1, right);
    merge(array, left, mid, right);
}

public static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1]; // 中间数组
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
        }

        while (i <= mid)   temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        //将temp中排序好之后的数组存入arr（原数组）中去
        for (int p = 0; p < temp.length; p++) {
            arr[left + p] = temp[p];
        }
        // 也可以用 System.arraycopy(a, start1, b, start2, length)
}
```

![image-20200815154659507](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200815154659507.png)



### 堆排序

![image-20200815154723612](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200815154723612.png)

堆排序可以直接使用PriorityQueue来实现。

代码模板：

```java
static void heapify(int[] array, int length, int i) {
    int left = 2 * i + 1, right = 2 * i + 2；
    int largest = i;
    if (left < length && array[left] > array[largest]) {
        largest = left;
    }
    if (right < length && array[right] > array[largest]) {
        largest = right;
    }
    if (largest != i) {
        int temp = array[i]; array[i] = array[largest]; array[largest] = temp;
        heapify(array, length, largest);
    }
}
public static void heapSort(int[] array) {
    if (array.length == 0) return;
    int length = array.length;
    //建立小顶堆
    for (int i = length / 2-1; i >= 0; i--) 
        heapify(array, length, i);
    for (int i = length - 1; i >= 0; i--) {
        int temp = array[0]; array[0] = array[i]; array[i] = temp;
        heapify(array, i, 0);
    }
}
```

## 特殊排序：要求数据是整型

![image-20200815155225553](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200815155225553.png)

