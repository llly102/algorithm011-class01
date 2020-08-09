## 字典树和并查集

### 字典树Trie

适用场景：词频的感应和根据前缀推后面

![](C:\Users\LY\Documents\360截图\360截图20200804181540199.jpg)

其中数字表示字符串出现的频次。

基本性质：

1.  结点本身不存储完整单词；

2. 从根结点到某一结点，路径上经过的字符连接起来，是该节点对应的字符串；
3. 每个结点的所有子结点路径代表的字符都不相同。

Trie树最大可能是26叉树。

![](C:\Users\LY\Documents\360截图\360截图20200804182315047.jpg)



![](C:\Users\LY\Documents\360截图\360截图20200804182532657.jpg)

Trie树的基本操作

```java
class TrieNode {

    // R links to node children
    private TrieNode[] links;

    private final int R = 26;

    private boolean isEnd;

    public TrieNode() {
        links = new TrieNode[R];
    }

    public boolean containsKey(char ch) {
        return links[ch -'a'] != null;
    }
    public TrieNode get(char ch) {
        return links[ch -'a'];
    }
    public void put(char ch, TrieNode node) {
        links[ch -'a'] = node;
    }
    public void setEnd() {
        isEnd = true;
    }
    public boolean isEnd() {
        return isEnd;
    }
}

```

实现前缀树Trie

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

#### 例题：单词搜索2（力扣212）

![](C:\Users\LY\Documents\360截图\360截图20200806082328645.jpg)

1. 动态回溯  
2. 前缀树：all word 构建成前缀树；对board，进行dfs。

```java
//定义前缀树结点
class TrieNode {
    public TrieNode[] children;
    public String word;
    boolean isEnd;
    public TrieNode(){
        children = new TrieNode[26];
        word = null;
    }
}
//定义前缀树和插入方法
class Trie {
    TrieNode root ;
    public Trie(){
        root = new TrieNode();
    } 
    //前缀树中插入word
    public void insert(String word){
        char[] words = word.toCharArray();
        TrieNode cur = root;
        for(int i=0;i<words.length;i++){
            int k=words[i] - 'a';
            if(cur.children[k] == null){
                cur.children[k] = new TrieNode();
            }
            cur = cur.children[k];
        }
        //相当于结束标记，只有结束的时候，将word赋值给cur.word
        cur.word = word;
    }
}

class Solution {
    int m,n;
    char[][] board;
    public List<String> findWords(char[][] board, String[] words) {
        this.board = board;
        Set<String> res = new HashSet<>();

        //构建前缀树
        Trie myTrie = new Trie();
        TrieNode root = myTrie.root;
        for(String word : words){
            myTrie.insert(word);
        }

        m=board.length;
        n=board[0].length;

        //遍历整个board数组，查找是否在前缀树中存在
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                dfs(i,j,root,res);
            }
        }

        return new LinkedList<String>(res);
    }
    public void dfs(int i,int j, TrieNode cur,Set<String> res){
        //终止条件
        if(i>=m || i<0 || j>=n || j<0 || board[i][j] == '#'){
            return;
        }
        cur = cur.children[board[i][j]-'a'];
        if(cur == null){
            return;
        }
        //标记该位置已经访问过
        char temp = board[i][j];
        board[i][j] = '#';
        //成功找到，加入到结果中
        if(cur.word!=null){
            res.add(cur.word);
            cur.word = null;
        }
        //下一层
        dfs(i+1,j,cur,res);
        dfs(i-1,j,cur,res);
        dfs(i,j+1,cur,res);
        dfs(i,j-1,cur,res);
        //回复状态
        board[i][j] = temp;
    }
}
```

### 并查集

#### 适用场景：

组团、配对问题和组队问题

![](C:\Users\LY\Documents\360截图\360截图20200806160030170.jpg)

初始化：每个元素都有一个parent数组指向自己，表示自己是自己的集合

![image-20200806160944253](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200806160944253.png)

查询：查看元素的parent，直到它的parent指向它自己时，就找到了元素所属集合。

合并：找到两个并查集的领头元素a和e，将a的parent指向e，或者将e的parent指向a。

![image-20200806160924907](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200806160924907.png)

路径压缩：使一个集合中的元素的parent都指向它的领头元素。

![image-20200806161404781](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200806161404781.png)

#### 代码模板

```
// Java
class UnionFind { 
	private int count = 0; //集合的个数size
	private int[] parent; 
	//初始化
	public UnionFind(int n) { 
		count = n; 
		parent = new int[n]; 
		//初始化时parent[i]=i
		for (int i = 0; i < n; i++) { 
			parent[i] = i;
		}
	} 
	//找集合的领头元素
	public int find(int p) { 
	    //向上寻找领头元素
	    int root = p;
		while (root != parent[root]) { 
			//parent[p] = parent[parent[p]]; 
			root = parent[root]; 
		}
		//路径压缩，降低下一次查询的时间复杂度为O(1)
		while(p != parent[p]){
		    int x = p;
		    p = parent[p];
		    parent[x] = root;
		}
		return p; 
	}
	//合并
	public void union(int p, int q) { 
	    //找两个集合的领头元素
		int rootP = find(p); 
		int rootQ = find(q); 
		//如果相等，说明是一个，不用再合并
		if (rootP == rootQ) return; 
		//将一个领头元素的parent指向另一个领头元素
		parent[rootP] = rootQ; 
		count--;
	}
	//返回集合个数
	public int count(){
	    return count;
	}
}
```

#### 例题：朋友圈

![image-20200806160425573](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200806160425573.png)

1. DFS、BFS（类似岛屿问题）
2. 并查集

```
class Solution {
    public int findCircleNum(int[][] M) {
        int n = M.length;
        //创建并查集
        UnionFind uf = new UnionFind(n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                //如果M[i][j]==1，说明i和j是朋友，合并i和j。
                if(M[i][j] == 1) uf.union(i,j);
            }
        }
        //最后有几个集合，就有几个朋友圈
        return uf.count();

    }
}
class UnionFind {
    private int count = 0;
    private int[] parent;
    public UnionFind(int n){
        count = n;
        parent = new int[n];
        for(int i=0;i<n;i++){
            parent[i]=i;
        }
    }
    public int find(int i){
        int root = i;
        while(root!=parent[root]){
            root = parent[root];
        }
        while(i != parent[i]){
            int x = i;
            i = parent[i];
            parent[x] = root;
        }
        return root;
    }
    public void union(int p,int q){
        int rootP = find(p);
        int rootQ = find(q);
        if(rootP == rootQ) return ;
        parent[rootP] = rootQ;
        count--;
    }
    public int count(){
        return count;
    }
}
```

## 高级搜索

![image-20200807153450823](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200807153450823.png)

DFS代码模板

```
public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> allResults = new ArrayList<>();
        if(root==null){
            return allResults;
        }
        travel(root,0,allResults);
        return allResults;
    }


    private void travel(TreeNode root,int level,  List<List<Integer>> results){
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

BFS代码模板

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

### 剪枝

剪去不成立的情况或者次优得到情况。

#### 例题：解数独（力扣37）

![image-20200807160048477](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200807160048477.png)

```java
class Solution {  

    //定义三个二维数组，分别代表行，列和块中的数字是否使用过了
    boolean[][] row = new boolean[9][10];
    boolean[][] col = new boolean[9][10];
    boolean[][] block = new boolean[9][10]; 


    public void solveSudoku(char[][] board) {
        //初始化，使用过的数字，对应的标记为true
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                int num = board[i][j] - '0';
                if(num>=1 && num<=9){
                    row[i][num] = true;
                    col[j][num] = true;
                    block[(i/3)*3+j/3][num] =true;
                }                
            }
        }
        //填充数字
        backtrace(0,0,board);
    }
    public boolean backtrace(int r,int c,char[][] board){
        //先填充行，当当前列c==9时，说明填充完一行，将行r++;
        //如果当前行r==9，说明整个填充完毕了，返回true
        if(c == 9){
            c=0;
            r++;
            if(r==9){
                return true;
            }
        }
        //当board[r][c]=='.'时，当前位置空缺，可以填入数字
        if(board[r][c] == '.'){
            //尝试将1~9的数字填进去
            for(int num=1;num<=9;num++){
                //只有这个数字在同行、列和块中没出现过，才能填入，否则试下一个数字
                boolean canUse = !(row[r][num] || col[c][num] || block[(r/3)*3+c/3][num]);
                if(canUse){
                    //将数字对应标记为true，说明已使用过
                    row[r][num] = true;
                    col[c][num] = true;
                    block[(r/3)*3+c/3][num] =true;

                    //将board对应位置填上数字
                    board[r][c] = (char) ('0' + num);
                    
                    //进入下一个位置的填充
                    if(backtrace(r,c+1,board)){
                        return true;
                    }

                    //恢复状态，填充失败了要将状态恢复
                    board[r][c] = '.';
                    row[r][num] = false;
                    col[c][num] = false;
                    block[(r/3)*3+c/3][num] = false;
                }
            }
        }
        //否则进入下一个位置的填充，返回其结果
        else return backtrace(r,c+1,board);

        return false;
    }
}
```

### 双向BFS

![image-20200808154417860](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200808154417860.png)

从源和目的两个方向同时进行BFS，直到它们搜索到同一个结点。

### 启发式搜索A*

先搜索优先级高的。

使用优先队列。

确定优先级：估价函数。

![image-20200808161020555](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200808161020555.png)

二进制矩阵中的最短路径的A*解法

```
	public class AStar
	{
		public final static int BAR = 1; // 障碍值
		public final static int PATH = 2; // 路径
		public final static int DIRECT_VALUE = 10; // 横竖移动代价
		public final static int OBLIQUE_VALUE = 14; // 斜移动代价
		
		Queue<Node> openList = new PriorityQueue<Node>(); // 优先队列(升序)
		List<Node> closeList = new ArrayList<Node>();
		
		/**
		 * 开始算法
		 */
		public void start(MapInfo mapInfo)
		{
			if(mapInfo==null) return;
			// clean
			openList.clear();
			closeList.clear();
			// 开始搜索
			openList.add(mapInfo.start);
			moveNodes(mapInfo);
		}
	

		/**
		 * 移动当前结点
		 */
		private void moveNodes(MapInfo mapInfo)
		{
			while (!openList.isEmpty())
			{
				Node current = openList.poll();
				closeList.add(current);
				addNeighborNodeInOpen(mapInfo,current);
				if (isCoordInClose(mapInfo.end.coord))
				{
					drawPath(mapInfo.maps, mapInfo.end);
					break;
				}
			}
		}
		
		/**
		 * 在二维数组中绘制路径
		 */
		private void drawPath(int[][] maps, Node end)
		{
			if(end==null||maps==null) return;
			System.out.println("总代价：" + end.G);
			while (end != null)
			{
				Coord c = end.coord;
				maps[c.y][c.x] = PATH;
				end = end.parent;
			}
		}
	

		/**
		 * 添加所有邻结点到open表
		 */
		private void addNeighborNodeInOpen(MapInfo mapInfo,Node current)
		{
			int x = current.coord.x;
			int y = current.coord.y;
			// 左
			addNeighborNodeInOpen(mapInfo,current, x - 1, y, DIRECT_VALUE);
			// 上
			addNeighborNodeInOpen(mapInfo,current, x, y - 1, DIRECT_VALUE);
			// 右
			addNeighborNodeInOpen(mapInfo,current, x + 1, y, DIRECT_VALUE);
			// 下
			addNeighborNodeInOpen(mapInfo,current, x, y + 1, DIRECT_VALUE);
			// 左上
			addNeighborNodeInOpen(mapInfo,current, x - 1, y - 1, OBLIQUE_VALUE);
			// 右上
			addNeighborNodeInOpen(mapInfo,current, x + 1, y - 1, OBLIQUE_VALUE);
			// 右下
			addNeighborNodeInOpen(mapInfo,current, x + 1, y + 1, OBLIQUE_VALUE);
			// 左下
			addNeighborNodeInOpen(mapInfo,current, x - 1, y + 1, OBLIQUE_VALUE);
		}
	

		/**
		 * 添加一个邻结点到open表
		 */
		private void addNeighborNodeInOpen(MapInfo mapInfo,Node current, int x, int y, int value)
		{
			if (canAddNodeToOpen(mapInfo,x, y))
			{
				Node end=mapInfo.end;
				Coord coord = new Coord(x, y);
				int G = current.G + value; // 计算邻结点的G值
				Node child = findNodeInOpen(coord);
				if (child == null)
				{
					int H=calcH(end.coord,coord); // 计算H值
					if(isEndNode(end.coord,coord))
					{
						child=end;
						child.parent=current;
						child.G=G;
						child.H=H;
					}
					else
					{
						child = new Node(coord, current, G, H);
					}
					openList.add(child);
				}
				else if (child.G > G)
				{
					child.G = G;
					child.parent = current;
					openList.add(child);
				}
			}
		}
	

		/**
		 * 从Open列表中查找结点
		 */
		private Node findNodeInOpen(Coord coord)
		{
			if (coord == null || openList.isEmpty()) return null;
			for (Node node : openList)
			{
				if (node.coord.equals(coord))
				{
					return node;
				}
			}
			return null;
		}
	

		/**
		 * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
		 */
		private int calcH(Coord end,Coord coord)
		{
			return Math.abs(end.x - coord.x)
					+ Math.abs(end.y - coord.y);
		}
		
		/**
		 * 判断结点是否是最终结点
		 */
		private boolean isEndNode(Coord end,Coord coord)
		{
			return coord != null && end.equals(coord);
		}
	

		/**
		 * 判断结点能否放入Open列表
		 */
		private boolean canAddNodeToOpen(MapInfo mapInfo,int x, int y)
		{
			// 是否在地图中
			if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight) return false;
			// 判断是否是不可通过的结点
			if (mapInfo.maps[y][x] == BAR) return false;
			// 判断结点是否存在close表
			if (isCoordInClose(x, y)) return false;
	

			return true;
		}
	

		/**
		 * 判断坐标是否在close表中
		 */
		private boolean isCoordInClose(Coord coord)
		{
			return coord!=null&&isCoordInClose(coord.x, coord.y);
		}
	

		/**
		 * 判断坐标是否在close表中
		 */
		private boolean isCoordInClose(int x, int y)
		{
			if (closeList.isEmpty()) return false;
			for (Node node : closeList)
			{
				if (node.coord.x == x && node.coord.y == y)
				{
					return true;
				}
			}
			return false;
		}
	}
```

## AVL树和红黑树

查询的时间复杂度与树的深度相关，为了避免出现树变成一根链条（即链表）的情况，使查询时间复杂度增高，所以要使树平衡。

保证性能的关键：

1. 保证二维维度：左右子树结点平衡。
2. 保证平衡。

### AVL树

平衡因子：结点的左子树和右子树的高度差，{-1,0,1}。

![image-20200809162940010](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809162940010.png)

所有叶子结点的平衡因子都是0，因为它没有左右子树。

#### 旋转操作：

1. 左旋

   ![image-20200809163247974](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809163247974.png)

   

2. 右旋

   ![image-20200809163306015](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809163306015.png)

   

3. 左右旋

   ![image-20200809163415805](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809163415805.png)

   ![image-20200809163434707](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809163434707.png)

   

4. 右左旋

   ![image-20200809163522799](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809163522799.png)

有子树的情况：

<img src="C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809163837412.png" alt="image-20200809163837412" style="zoom:150%;" />



增加3

![image-20200809163140299](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809163140299.png)

![image-20200809164349869](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809164349869.png)

![image-20200809164401729](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809164401729.png)



增加15

![image-20200809164510901](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809164510901.png)

![image-20200809164525352](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809164525352.png)

![image-20200809164558685](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809164558685.png)

#### AVL总结

![image-20200809164633348](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809164633348.png)

### 红黑树（近似平衡二叉树）

![image-20200809164811117](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809164811117.png)

关键性质：从根节点到叶子结点的最长的可能路径不多于最短的可能路径的两倍长。

![image-20200809170135921](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809170135921.png)

### 对比

![image-20200809170437157](C:\Users\LY\AppData\Roaming\Typora\typora-user-images\image-20200809170437157.png)

AVL的查询操作更快，因为它是严格平衡的。

红黑树的插入操作更快，因为它是近似平衡的。

AVL结点需要存储平衡因子。

查询或读操作比较多，比如数据库，使用AVL树更快；插入删除操作比较多，比如java中的map、set，使用红黑树更快。