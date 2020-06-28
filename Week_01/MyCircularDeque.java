//数组的大小size为k+1，因为要保证rear指向的是一个空的位置
//front始终指向队列的第一个元素的位置，当在队头进行插入时，要先减1，再赋值
//rear始终指向下一个元素可以插入的位置，当在队尾进行插入时，要赋值之后再加1
//队列空的条件：front==rear
//队列满的条件：(rear+1)%size==front
//注意在减1取模时，要减1再加size，避免取模时结果为负数
public class MyCircularDeque {
    private int[] deque;
    //数组的大小
    private int size;
    //front始终指向队列的第一个元素的位置
    private int front;
    //rear始终指向下一个元素可以插入的位置
    private int rear;

    /**
     * Initialize your data structure here. Set the size of the deque to be k.
     */
    public MyCircularDeque(int k) {
        size = k + 1;
        deque = new int[size];
        //初始为0
        front = 0;
        //初始为0
        rear = 0;
    }

    /**
     * Adds an item at the front of Deque. Return true if the operation is successful.
     */
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }
        //在front插入时，front要先减1，再赋值
        front = (front - 1 + size) % size;
        deque[front] = value;
        return true;
    }

    /**
     * Adds an item at the rear of Deque. Return true if the operation is successful.
     */
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }
        //在队尾插入时，要先赋值，rear再减1
        deque[rear] = value;
        rear = (rear + 1) % size;
        return true;
    }

    /**
     * Deletes an item from the front of Deque. Return true if the operation is successful.
     */
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }
        //队头删除时，只需将front加1即可，原来的队头元素再下一次队头插入时会直接覆盖
        front = (front + 1) % size;
        return true;
    }

    /**
     * Deletes an item from the rear of Deque. Return true if the operation is successful.
     */
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }
        //队尾删除时，只需将rear减1即可，原来的队尾元素再下一次队尾插入时会直接覆盖
        rear = (rear - 1 + size) % size;
        return true;
    }

    /**
     * Get the front item from the deque.
     */
    public int getFront() {
        if (isEmpty()) {
            return -1;
        }
        return deque[front];

    }

    /**
     * Get the last item from the deque.
     */
    public int getRear() {
        if (isEmpty()) {
            return -1;
        }
        return deque[(rear - 1 + size) % size];

    }

    /**
     * Checks whether the circular deque is empty or not.
     */
    public boolean isEmpty() {
        return front == rear;
    }

    /**
     * Checks whether the circular deque is full or not.
     */
    public boolean isFull() {
        return (rear + 1) % size == front;
    }

    public static void main(String[] args) {
        int k = 2;
        int value1 = 10, value2 = 6;
        MyCircularDeque obj = new MyCircularDeque(k);
        System.out.println("在队头插入：" + obj.insertFront(value1));
        System.out.println("在队尾插入：" + obj.insertLast(value2));
        System.out.println("队列已满？" + obj.isFull());
        System.out.println("查询队头元素：" + obj.getFront());
        System.out.println("查询队尾元素：" + obj.getRear());
        System.out.println("在队头删除：" + obj.deleteFront());
        System.out.println("在队尾删除：" + obj.deleteLast());
        System.out.println("队列为空？" + obj.isEmpty());


    }
}

