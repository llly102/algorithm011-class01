### 哈希表（Hash table）
#### 简介
1. 哈希表，也叫散列表，是根据关键码值（key value）直接进行访问的数据结构。
2. 哈希表通过关键码值映射到表中的一个位置来访问记录，以加快查找的速率。
3. 这个映射函数叫做散列函数（Hash Function），存放记录的数组叫做哈希表（散列表）。
#### 工程实践
1. 电话号码簿
2. 用户信息表
3. 缓存（LRU Cache）
4. 键值对存储（Redis）
#### 哈希函数（Hash Function）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200630163002832.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
#####  哈希碰撞（Hash Collisios）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200630163124425.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
1. 哈希碰撞：不同的数据经过哈希函数之后得到相同的值，产生碰撞。
2. 解决方法：拉链式解决冲突法。在同一个位置拉一个链表来进行存储。若在同一位置堆积很多值，即这个链表很长，这个时候的查询时间复杂度会退化到O(n)。
3. 哈希表的查询复杂度为O(1)，最坏情况为O(n)。
4. 好的哈希函数会尽量少产生碰撞，但是当产生碰撞时，可以使用拉链式解决方法。![在这里插入图片描述](https://img-blog.csdnimg.cn/20200630163725679.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
#### Java中的哈希表
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200630163911748.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
##### Set的实现分为：HashSet和TreeSet
1. HashSet通过HashMap来实现。在存储时将元素放进HashMap的key位置上，生成一个占位放在value位置上。

```java
public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
```
2. TreeSet通过红黑树来实现
##### Map的实现：HashMap和TreeMap
1. 通过HashMap实现

常量与变量
```java
// 默认初始容量必须是2的幂，这里是16
static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

// 最大容量（必须是2的幂且小于2的30次方，如果在构造函数中传入过大的容量参数将被这个值替换）
static final int MAXIMUM_CAPACITY = 1 << 30;

// 默认负载因子，啥叫负载因子呢，HashMap通过负载因子与桶的数量计算得到所能容纳的最大元素数量
// 计算公式为threshold = capacity * loadFactor
static final float DEFAULT_LOAD_FACTOR = 0.75f;

// 链表转化为红黑树的阈值
static final int TREEIFY_THRESHOLD = 8;

// 红黑树转化为链表的阈值，扩容时才可能发生
static final int UNTREEIFY_THRESHOLD = 6;

// 进行树化的最小容量，防止在调整容量和形态时发生冲突
static final int MIN_TREEIFY_CAPACITY = 64;
```
hash()

```java
 static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
```
当key为null时直接返回0，key的hash值高16位不变，低16位与高16位异或作为key的最终hash值。如此设置的原因是因为下标的计算是：n = table.length; index = (n-1) & hash;
table的长度都是2的幂，因此index仅与hash值的低n位有关，hash值的高位都被与操作置为0了，所以异或降低冲突。

getNode()方法 ：

```java
public V get(Object key) {
        Node<K,V> e;
        //求出key的hash值，调用getNode
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    /**
     * Implements Map.get and related methods.
     *
     * @param hash hash for key
     * @param key the key
     * @return the node, or null if none
     */
final Node<K,V> getNode(int hash, Object key) {
        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        //当table不为空，且计算得到的查询位置table[i]也不为空时继续
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            //判断table[i]的首个元素first是否等于key，若相等直接将其返回
            if (first.hash == hash &&      // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
            //如果存储结构是红黑树，则执行红黑树中的查询操作
                if (first instanceof TreeNode)
                    return ((TreeNode<K,V>)first).getTreeNode(hash, key);
                do {
                //如果存储结构是链表，则遍历链表，找到key所在位置
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
```
putVal()方法

```java
public V put(K key, V value) {
        //求出Key的值，并直接调用putVal
        return putVal(hash(key), key, value, false, true);
    }

/**
     * Implements Map.put and related methods.
     *
     * @param hash hash for key
     * @param key the key
     * @param value the value to put
     * @param onlyIfAbsent if true, don't change existing value
     * @param evict if false, the table is in creation mode.
     * @return previous value, or null if none
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        //1.如果table为空或者length为0，则调用resize扩容
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //2.根据Key的hash值得到插入的数组索引i,如果table[i]为空，直接直接新建结点进行插入即可
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        //如果table[i]不为空
        else {
            Node<K,V> e; K k;
            //3.判断table[i]的首个元素是否等于Key，若等于直接覆盖value
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            //4.判断table[i]是否为treeNode，即它是否为红黑树，如果是直接在树中插入键值对
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
            //5.遍历table[i]
                for (int binCount = 0; ; ++binCount) {
                //若链表长度小于8，则执行链表的插入操作
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        //若链表长度大于等于8，则将链表转换为红黑树，执行插入操作
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    //在遍历过程中，如果发现Key已存在则直接覆盖value
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //Key已存在，用新的value覆盖原来的value，返回旧的value
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        //modCount+1,用来实现fail-fast机制
        ++modCount;
        //插入成功后，判断实际存在的键值对数量size是否超过最大容量threshold，如果超过调用resize扩容
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```
2. TreeMap通过红黑树来实现   
### 树、二叉树和二叉搜索树   
1.	树   
不同于链表中的结点，树的结点定义中可能有多个next指针。  
树中是没有环的，图中是有环的。   
链表是特殊化的树；树是特殊化的图。   
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705160658485.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70)
2.	二叉树
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705160710468.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70)
3.	二叉树结点定义
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020070516072422.png)
 4.	树的遍历：
前序（pre-order）:根-左-右
中序（in-order）：左-根-右
后序（post-order）：左-右-根   
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705160732241.png)
5.	二叉搜索树，又称二叉排序树、有序二叉树、排序二叉树，是指一棵空树或者具有以下性质的二叉树：   
①	左子树上所有结点的值均小于它的根结点的值；   
②	右子树上所有结点的值均大于它的根结点的值。   
③	以此类推，左、右子树也分别为二叉搜索树。（重复性）   
它的中序遍历是升序排列（可以借此来检查一个二叉树是否为二叉搜索树）。  
查询、插入和删除操作的时间复杂度都是O(logn)。  
删除操作：①删除叶子结点 ②其他结点：找删除结点的比它大的最近的那个结点替上去。   
6.	时间复杂度
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705160746467.png)
二叉搜索树的最坏情况：全部只有左子树或者只有右子树，这样就退化成了链表，查询、插入和删除操作时间复杂度变为O(n)。要进行平衡二叉树。
7.	树的解法一般用递归：递归的本质是自身的重复，树和树中的子树的结构一致，根结点-左子树-右子树，这样的一种重复。树的定义就是采用递归的方式，所以关于树的问题一般采用递归。
### 堆Heap
1. （1） Heap：可以快速找到一堆数中的最大值或者最小值的数据结构。
将根节点最大的堆叫做大顶堆或大根堆，根结点最小的堆叫做小顶堆或小根堆。
（2）常见的堆有二叉堆、斐波那契堆。
（3）大顶堆的常见操作（API）：
①find-max:O(1)
② delete-max: O(logn)
③ insert(creat): O(logn)或O(1)（斐波那契堆）
2.  二叉堆BinaryHeap
二叉堆是堆的一种常见且简单的实现，调用PriorityQueue。
（1）二叉堆性质：通过完全二叉树来实现（不是二叉搜索树）。
二叉堆（大顶堆）满足以下性质：
①是一棵完全树
②树中任意节点的值总是大于等于其子节点的值
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705161402185.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70)
（2）二叉堆的实现细节
1.通过数组实现。
2.假设第一个元素在数组中的索引为0，即a[0],则
①	索引为i的结点的左孩子结点的索引为2*i+1;
②	索引为i的结点的右孩子结点的索引为2*i+2
③	索引为i的结点的父节点的索引为floor(i-1)/2)
（3）Insert插入操作
①	新元素先插入到堆的尾部。
②	依次向上调整整个堆的结构，通过HeapifyUp。
新节点插入到尾部之后，将结点和它的父节点进行比较，如果大于，就把这个节点和父节点进行交换，直到不再大于。
（4）DeleteMax删除堆顶操作
①	将堆尾元素替换到顶部（即堆顶被替换删除掉）
②	一次从根部向下调整整个堆的结构（一直到堆尾为止）
使用函数HeapifyDown
堆尾元素替换到顶部之后，将其和它的左右孩子进行比较，和较大的那个进行交换，直到不再小于。
### 图
1.	图
(1)	图的属性Graph(V,E)：
①V-vertex：点
a)	度-出度和入度
b)	点与点之间：连通与否
②E-edge：边 
c)	有向和无向
d)	权重（边长）
(2)	图的表示和分类
表示方法：邻接矩阵和邻接表
无向无权图：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705162046635.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
有向无权图：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705162103379.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
无向有权图：
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705162117765.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
有向有权图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705162804592.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
(3)	基于图的常见算法
①	 DFS-递归写法
注意有visited ，因为图中可能会有环路，看结点是否被访问过。
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705161600477.png)
②	BFS代码
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200705161606259.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70)

