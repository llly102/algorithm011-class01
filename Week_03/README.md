### 递归Recursion                   
递归本质就是循环，通过函数体进行循环。                

递归的代码模板：                    
```java
// Java
public void recur(int level, int param) { 
  // terminator 终止条件
  if (level > MAX_LEVEL) { 
    // process result 
    return; 
  }
  // process current logic 当前层逻辑
  process(level, param); 
  // drill down 下一层
  recur( level: level + 1, newParam); 
  // restore current status  内存回收
}
```
注意：                     
1. 不要人肉进行递归                      
2. 找最近重复子问题                     
3. 数学归纳法思维                  

### 分治和回溯                   
一种特殊的递归。                       
找问题的重复性。将问题分解。                   
#### 分治                           
![递归状态树](https://img-blog.csdnimg.cn/20200712104717623.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
分治的代码模板                  
```java
// java
public void divide_conquer(problem, param1, param2, ...): 
  // recursion terminator 
  if(problem == None): 
	print_result ;
	return ;
  // prepare data 
  //将问题分解
  data = prepare_data(problem) 
  subproblems = split_problem(problem, data) 
  // conquer subproblems 
  //解决子问题
  subresult1 = divide_conquer(subproblems[0], p1, ...) 
  subresult2 = divide_conquer(subproblems[1], p1, ...) 
  subresult3 = divide_conquer(subproblems[2], p1, ...) 
  …
  // process and generate the final result 
  //将子问题的结果合起来
  result = process_result(subresult1, subresult2, subresult3, …)
	
  // revert the current level states
```

#### 回溯                            
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200712120602115.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MjE1Mjg0OQ==,size_16,color_FFFFFF,t_70#pic_center)
