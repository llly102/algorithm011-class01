### 1.队列Queue
Queue：只能在一端（队尾）进行插入，另一端（队头）进行输出，先进先出。![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628104310596.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
Java中Queue是java.util包中提供的接口，并扩展了java.util.Collection接口。

```
java.util
public interface Queue<E>
extends Collection<E>
```
### 2.Queue中常用方法
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628105130975.jpg#pic_center)
#### 2.1 boolean add(E e)：
 如果可以在不违反容量限制的情况下立即执行此操作，则将指定的元素插入此队列，成功时返回true，如果当前没有可用空间则抛出IllegalStateException。

#### 2.2 boolean offer(E e)：
 如果可以在不违反容量限制的情况下立即执行此操作，则将指定的元素插入此队列，成功时返回true，如果当前没有可用空间则返回 false。

#### 2.3 E remove()：
检索并删除此队列的头部元素。 此方法与poll的不同之处仅在于，如果此队列为空，则抛出异常 NoSuchElementException。

#### 2.4 E poll()： 
检索并删除此队列的头部，如果此队列为空，则返回null。

#### 2.5 E element()：
检索但不删除此队列的头部。 此方法与peek的不同之处仅在于，如果此队列为空，则抛出异常 NoSuchElementException。

#### 2.6 E peek()：
检索但不移除此队列的头部，如果此队列为空，则返回null。
### 3.示例
##### 3.1插入操作

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
##### 3.2删除操作

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
##### 3.3查询操作

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

