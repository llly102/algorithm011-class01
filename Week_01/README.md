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
操作：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200628191206343.jpg#pic_center)

4. 优先队列（Priority Queue）：元素按照优先级进行存储。插入操作：O(1)，删除操作：O(logn)。
5. 要熟悉栈和队列的API的使用方法。分析源码，理解实现过程。
