## 总结
### 1 关于做题
1. 审题：多看两遍题，理解题意，确定边界条件。
2. 思考可能的解题方法，分析它们的复杂度，进行比较。
3. 在面试时，设置测试用例。
4. 一道题做五遍。对比别人的优秀解法和代码，进行记忆理解，并自己写出代码。
5. 使用自顶向下的编程方式，以高层次主干逻辑为主。
6. 熟练IDE使用技巧。   
    ①Fn + ←/→   home/end 键   
    ②CTRL+D   复制行    
    ③CTRL+X   剪切,删除行   
    ④CTRL+DELETE  光标在单词前，删除一个单词   
    ⑤CTRL+←/→  光标移动一个单词的位置   
    ⑥Ctrl + Shift + ↑/↓   将此行上移或下移   
    ⑦CTRL+/   注释//      
    ⑧CTRL+SHIFT+/  注释/*...*/    
    ⑨CTRL+W   选中代码     
    ⑩Ctrl + E :  找到最近浏览的文件     
    ⑪Ctrl + Shift + E : 找到最近编辑的文件   
    ⑫快速定位到指定行，快捷键: Ctrl +G ,输入行数,按回车即可   
    ⑬psvm+Enter —— public static void main(String[] args) {}   
    ⑭sout+Enter —— System.out.println();  
    ⑮fori+Enter —— for (int i = 0; i < ; i++) {        }  
### 2 复杂度
1. 时间复杂度：要注意各种常用算法的时间复杂度，在编程时注意分析时间复杂度，选择更高效的方法。
2. 空间复杂度：注意数组、链表的长度；注意递归的深度。
### 3 数组、链表和跳表
1. 数组：插入删除操作：O(n)，查询操作：O(1)。
2. 链表：分为单链表、双向链表和循环链表。熟悉链表的结点实现代码。插入删除操作：O(1)，查询操作：O(n)。
3. 跳表：仅用于链表中元素有序的情况。
① 采用升维的思想，为链表添加索引。
② 查询、插入和删除操作的时间复杂度都为O(logn)。
### 4 栈与队列
1. 栈（Stack）：先入后出，插入删除都是O(1)。
操作：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628191044868.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
2. 队列（Queue）：先入先出，插入删除都是O(1)。
操作：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628191123820.jpg#pic_center)
3. 双端队列（Deque）：两端都可以进行插入和删除，分为First端和Last端。
操作：![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628191206343.jpg#pic_center)
4. 优先队列（Priority Queue）：元素按照优先级进行存储。插入操作：O(1)，删除操作：O(logn)。
5. 要熟悉栈和队列的API的使用方法。分析源码，理解实现过程。

## 改写Deque

```
import java.util.Deque;
import java.util.LinkedList;

public class deque {
    public static void main(String[] args) {
        Deque<String> deque = new LinkedList<String>();

        deque.offerFirst("a");
        deque.offerFirst("b");
        deque.offerFirst("c");
        System.out.println(deque);//从first处开始打印deque

        String str1 = deque.peekFirst();//first处取相当于栈
        String str2 = deque.peekLast();//last处取相当于队列
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(deque);

        while(deque.size() > 0) {
            System.out.println(deque.pollLast());
        }
        System.out.println(deque);
    }
}

```

## 队列Queue
Queue：只能在一端（队尾）进行插入，另一端（队头）进行输出，先进先出。![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628104310596.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
Java中Queue是java.util包中提供的接口，并扩展了java.util.Collection接口。

```
java.util
public interface Queue<E>
extends Collection<E>
```
### 1.Queue中常用方法
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628105130975.jpg#pic_center)
#### 1.1 boolean add(E e)：
 如果可以在不违反容量限制的情况下立即执行此操作，则将指定的元素插入此队列，成功时返回true，如果当前没有可用空间则抛出IllegalStateException。

#### 1.2 boolean offer(E e)：
 如果可以在不违反容量限制的情况下立即执行此操作，则将指定的元素插入此队列，成功时返回true，如果当前没有可用空间则返回 false。

#### 1.3 E remove()：
检索并删除此队列的头部元素。 此方法与poll的不同之处仅在于，如果此队列为空，则抛出异常 NoSuchElementException。

#### 1.4 E poll()： 
检索并删除此队列的头部，如果此队列为空，则返回null。

#### 1.5 E element()：
检索但不删除此队列的头部。 此方法与peek的不同之处仅在于，如果此队列为空，则抛出异常 NoSuchElementException。

#### 1.6 E peek()：
检索但不移除此队列的头部，如果此队列为空，则返回null。
### 2.示例
##### 2.1插入操作

```
public class e_queue {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<String>();
        queue.offer("one");
        queue.offer("two");
        queue.offer("three");
        queue.offer("four");
        queue.add("five");
        System.out.println(queue);       
    }
}
```
结果：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628111831750.jpg#pic_center)
##### 2.2删除操作

```
public class e_queue {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<String>();
        queue.offer("one");
        queue.offer("two");
        System.out.println("输出队列："+queue);

        System.out.println("删除第一个元素："+queue.poll());
        System.out.println("删除第二个元素："+queue.remove());
        System.out.println("不抛出异常："+queue.poll());
        System.out.println("抛出异常："+queue.remove());  
    }
}
```
结果：![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628112509374.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
##### 2.3查询操作

```
public class e_queue {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<String>();
        queue.offer("one");
        queue.offer("two");
        System.out.println("输出队列："+queue);

        System.out.println("查询第一个元素："+queue.element());
        System.out.println("查询第二个元素："+queue.peek());
        while(queue.size() > 0) {
            System.out.println(queue.poll());
        }
        System.out.println("队列中已空");
        System.out.println("不抛出异常："+queue.peek());
        System.out.println("抛出异常："+queue.element());
    }
}
```
结果：![在这里插入图片描述](https://img-blog.csdnimg.cn/2020062811283090.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)

### PriorityQueue介绍
1. PriorityQueue是一个优先级队列，其中元素按照其优先级存储。它继承了AbstractQueue抽象类。
2. PriorityQueue内部有一个比较器，通过比较器实现对其元素进行优先级排序，如果没有传入比较器，那么会使用元素自身的camparTo方法,如果没有，会进行报错。
### PriorityQueue中的方法
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628115447746.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
## PriorityQueue的实现
### 常量与变量
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
### 添加操作
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
### 删除操作
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

