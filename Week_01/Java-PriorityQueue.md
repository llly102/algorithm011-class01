﻿### PriorityQueue介绍
1. PriorityQueue是一个优先级队列，其中元素按照其优先级存储。它继承了AbstractQueue抽象类。
2. PriorityQueue内部有一个比较器，通过比较器实现对其元素进行优先级排序，如果没有传入比较器，那么会使用元素自身的camparTo方法,如果没有，会进行报错。
### PriorityQueue中的方法
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628115447746.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
### PriorityQueue的实现
#### 常量与变量
```
 //默认初始化容量
private static final int DEFAULT_INITIAL_CAPACITY = 11;

 //queue数组用于存储优先级队列中的元素，由于优先级队列主要用堆来实现，所以queue[0]始终为优先级最小的元素
 transient Object[] queue; 

//优先级队列实际存储的元素个数
private int size = 0;

//比较器，用于比较元素之间的优先级，如果为空，那么会使用元素的自然顺序
 private final Comparator<? super E> comparator;

//优先级队列被修改的次数
 transient int modCount = 0; 
```
#### 添加操作
offer(E e)   将指定的元素插入此优先级队列。

```
public boolean offer(E e) {
        if (e == null)
            throw new NullPointerException();
        modCount++;
        int i = size;
        //如果实际存储元素个数大于当前队列长度，进行扩容
        if (i >= queue.length)
            grow(i + 1);
        //实际存储元素数增加1
        size = i + 1;
        //如果之前队列没有元素，那么当前元素直接放在堆顶，否则，进行放到堆的末尾进行下滤操作
        if (i == 0)
            queue[0] = e;
        else
            siftUp(i, e);
        return true;
    }
```
其中，grow()方法对队列数组进行扩容

```
private void grow(int minCapacity) {
        int oldCapacity = queue.length;
        // 如果之前的容量小于64，那么新容量为2*oldCapacity+2。否则，新容量为oldCapacity*1.5
        int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                                         (oldCapacity + 2) :
                                         (oldCapacity >> 1));
        // 检验当前容量是否以超出内存限制
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // 根据新的容量对数组进行拷贝
        queue = Arrays.copyOf(queue, newCapacity);
    }
```
siftUpUsingComparator方法，使用Comparator比较器进行堆排序

```
private void siftUpUsingComparator(int k, E x) {
        while (k > 0) {
            // k为当前元素插入位置，由于堆一个父节点索引为k，那么其左右孩子分别为2*k+1，2*k+2，所以一个子节点其父节点的所以一定为(k-1)>>>1
            int parent = (k - 1) >>> 1;
            Object e = queue[parent];
            //对子节点与父节点优先级进行比较，如果子节点大，那么顺序不变，否则，进行交换
            if (comparator.compare(x, (E) e) >= 0)
                break;
            queue[k] = e;
            k = parent;
        }
        queue[k] = x;
    }
```
#### 删除操作
remove(Object o)方法删除特定元素

```
public boolean remove(Object o) {
        // 查找元素的位置，具体实现就是遍历
        int i = indexOf(o);
        // 如果没有，直接返回空，否则调用removeAt函数
        if (i == -1)
            return false;
        else {
            removeAt(i);
            return true;
        }
    }
```
removeAt(Object o)方法删除特定位置上的元素

```
private E removeAt(int i) {
        modCount++;
        int s = --size;
        // 如果为最后一个元素，直接置为null
        if (s == i) 
            queue[i] = null;
        else {
            E moved = (E) queue[s];
            queue[s] = null;
            // 重新进行堆化，因为小顶堆的性质是父节点的值比左右子节点都大
            siftDown(i, moved);
            // 如果最后i位置上的是moved节点，那么相当于往i位置中插入moved了，所以需要siftUp函数
            if (queue[i] == moved) {
                siftUp(i, moved);
                if (queue[i] != moved)
                    return moved;
            }
        }
        return null;
    }
```
使用比较器的siftdown方法

```
private void siftDownUsingComparator(int k, E x) {
        // 对size取半，因为要使k节点与其左右子节点进行比较，所以k值必须小于half
        int half = size >>> 1;
        while (k < half) {
            // 取左节点
            int child = (k << 1) + 1;
            Object c = queue[child];
            int right = child + 1;
            // 令c等于右节点与左节点中优先级小的一个
            if (right < size &&
                comparator.compare((E) c, (E) queue[right]) > 0)
                c = queue[child = right];
            // 如果父节点小于c的话，那么说明比左右节点都小，不需要在比较，否则进行交换
            if (comparator.compare(x, (E) c) <= 0)
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = x;
    }
```

