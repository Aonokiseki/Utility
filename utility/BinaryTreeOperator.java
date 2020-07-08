package utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BinaryTreeOperator {
	
	public enum Side{
		LEFT, RIGHT
	}
	/**
	 * 二叉树节点的接口
	 * @author zhaoyang
	 * @param <T>
	 */
	public interface ITreeNode<T>{
		ITreeNode<T> setLeft(ITreeNode<T> left);
		ITreeNode<T> left();
		ITreeNode<T> setRight(ITreeNode<T> right);
		ITreeNode<T> right();
		ITreeNode<T> setValue(T value);
		ITreeNode<T> parent();
		ITreeNode<T> setParent(ITreeNode<T> parent, Side side);
		T value();
		boolean equals(Object o);
		int hashCode();
	}
	/**
	 * 对<code>ITreeNode&ltT&gt</code>的简单实现
	 * @author zhaoyang
	 * @param <T>
	 */
	public static class BinaryTreeNode<T> implements ITreeNode<T>{
		private static long count = 0;
		private final long id = count++;
		private T value;
		private BinaryTreeNode<T> leftChild;
		private BinaryTreeNode<T> rightChild;
		private BinaryTreeNode<T> parent;
		public BinaryTreeNode(){}
		
		public long id() {
			return this.id;
		}
		
		@Override
		public BinaryTreeNode<T> setLeft(ITreeNode<T> left) {
			if(left == null) {
				this.leftChild = null;
				return this;
			}
			this.leftChild = (BinaryTreeNode<T>) left;
			this.leftChild.parent = this;
			return this;
		}
		@Override
		public BinaryTreeNode<T> left() {
			return this.leftChild;
		}
		@Override
		public BinaryTreeNode<T> setRight(ITreeNode<T> right) {
			if(right == null) {
				this.rightChild = null;
				return this;
			}
			this.rightChild = (BinaryTreeNode<T>) right;
			this.rightChild.parent = this;
			return this;
		}
		@Override
		public BinaryTreeNode<T> right() {
			return this.rightChild;
		}
		@Override
		public BinaryTreeNode<T> setValue(T value) {
			this.value = value;
			return this;
		}
		@Override
		public BinaryTreeNode<T> parent() {
			return this.parent;
		}
		
		@Override
		public BinaryTreeNode<T> setParent(ITreeNode<T> parent, Side side){
			if(parent == null) {
				this.parent = null;
				return this;
			}
			this.parent = (BinaryTreeNode<T>)parent;
			switch(side) {
				case LEFT: this.parent.leftChild = this; break;
				case RIGHT: this.parent.rightChild = this; break;
				/* 既不是左边也不是右边, 要断开指向父节点的指针 */
				default: this.parent = null;
			}
			return this;
		}
		
		@Override
		public T value() {
			return this.value;
		}
		
		@Override
		public String toString() {
			return String.format("[id=%d, value=%s]", id, value);
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(this.id);
		}
		
		@Override
		public boolean equals(Object o) {
			if(this == o)
				return true;
			if(o instanceof BinaryTreeNode)
				return false;
			@SuppressWarnings("rawtypes")
			BinaryTreeNode btn = (BinaryTreeNode)o;
			if(btn.id == this.id)
				return true;
			return false;
		}
	}
	
	/*防止实例化*/
	private BinaryTreeOperator(){}
	
	/**
	 * 交换节点和此节点的孩子节点之间的位置<br/>
	 * 注: 此方法要求实现 ITreeNode.equals()
	 * 
	 * @param <T> 类型参数
	 * @param current 当前节点
	 * @param side 当前节点的哪个孩子, 可选值: left(左孩子) | right(右孩子)
	 * @return 指向孩子节点的指针
	 */
	public static <T> ITreeNode<T> swapParentAndChild(ITreeNode<T> current, Side side){
		if(current == null)
			return null;
		switch(side){
			case LEFT: return swapParentAndLeft(current);
			case RIGHT: return swapParentAndRight(current);
			default: return current;
		}
	}
	
	private static <T> ITreeNode<T> swapParentAndLeft(ITreeNode<T> current){
		ITreeNode<T> left = current.left();
		if(left == null)
			return current;
		ITreeNode<T> right = current.right();
		ITreeNode<T> parent = current.parent();
		boolean parentExist = (parent != null ? true : false);
		ITreeNode<T> leftLeft = left.left();
		ITreeNode<T> leftRight = left.right();
		
		current.setLeft(leftLeft);
		current.setRight(leftRight);
		left.setLeft(current);
		left.setRight(right);
		
		if(parentExist && parent.left().equals(current)) {
			parent.setLeft(left);
			return left;
		}
		if(parentExist && parent.right().equals(current)) {
			parent.setRight(left);
			return left;
		}
		left.setParent(null, null);
		return left;
	}
	
	private static <T> ITreeNode<T> swapParentAndRight(ITreeNode<T> current){
		ITreeNode<T> right = current.right();
		if(right == null)
			return current;
		ITreeNode<T> left = current.left();
		ITreeNode<T> parent = current.parent();
		boolean parentExist = (parent != null ? true : false);
		ITreeNode<T> rightLeft = right.left();
		ITreeNode<T> rightRight = right.right();
		
		current.setLeft(rightLeft);
		current.setRight(rightRight);
		right.setLeft(left);
		right.setRight(current);
		
		if(parentExist && parent.left().equals(current)) {
			parent.setLeft(right);
			return right;
		}
		if(parentExist && parent.right().equals(current)) {
			parent.setRight(right);
			return right;
		}
		right.setParent(null, null);
		return right;
	}
	
	/**
	 * 将有序队列转换为二叉搜索树<br>
	 * 因为内部涉及new一个对象, 因此使用了一个最简单的实现类<code>BinaryTreeNode</code>
	 * @param sortedArray
	 * @return BinaryTreeNode 二叉搜索树的根节点
	 */
	public static <T> BinaryTreeNode<T> sortedArrayToBST(List<T> sortedArray){
		if(sortedArray == null || sortedArray.isEmpty())
			return null;
		return buildBinarySearchTree(sortedArray, 0, sortedArray.size()-1);
	}
	/**
	 * 递归创建二叉搜索树
	 * @param sortedArray
	 * @param left
	 * @param right
	 * @return
	 */
	private static <T> BinaryTreeNode<T> buildBinarySearchTree(List<T> sortedArray, int left, int right){
		if(left > right)
			return null;
		int middle = left + (right - left)/2;
		BinaryTreeNode<T> root = new BinaryTreeNode<T>();
		root.setValue(sortedArray.get(middle))
		    .setLeft(buildBinarySearchTree(sortedArray, left, middle - 1))
		    .setRight(buildBinarySearchTree(sortedArray, middle + 1, right));
		return root;
	}
	
	/**
	 * 中序遍历
	 * @param root
	 * @return
	 */
	public static <T> List<ITreeNode<T>> inOrderTraversal(ITreeNode<T> root){
		List<ITreeNode<T>> result = new ArrayList<ITreeNode<T>>();
		if(root == null)
			return result;
		List<ITreeNode<T>> stack = new LinkedList<ITreeNode<T>>();
		ITreeNode<T> current = root;
		while(current != null || !stack.isEmpty()){
			while(current != null){
				stack.add(current);
				current = current.left();
			}
			current = stack.remove(stack.size()-1);
			result.add(current);
			current = current.right();
		}
		return result;
	}
	/**
	 * 先序遍历
	 * @param root
	 * @return
	 */
	public static <T> List<ITreeNode<T>> preOrderTraversal(ITreeNode<T> root){
		List<ITreeNode<T>> result = new ArrayList<ITreeNode<T>>();
		if(root == null)
			return result;
		List<ITreeNode<T>> stack = new LinkedList<ITreeNode<T>>();
		stack.add(root);
		ITreeNode<T> current = null;
		while(!stack.isEmpty()){
			current = stack.remove(stack.size()-1);
			result.add(current);
			/*先写右子树*/
			if(current.right() != null)
				stack.add(current.right());
			if(current.left() != null)
				stack.add(current.left());
		}
		return result;
	}
	/**
	 * 深度优先遍历
	 * @param root
	 * @return
	 */
	public static <T> List<ITreeNode<T>> depthOrderTraversal(ITreeNode<T> root){
		return preOrderTraversal(root);
	}
	/**
	 * 层次遍历
	 * @param root
	 * @return
	 */
	public static <T> List<ITreeNode<T>> levelTraversal(ITreeNode<T> root){
		List<ITreeNode<T>> result = new ArrayList<ITreeNode<T>>();
		if(root == null)
			return result;
		List<ITreeNode<T>> queue = new LinkedList<ITreeNode<T>>();
		queue.add(root);
		ITreeNode<T> current = null;
		while(!queue.isEmpty()){
			current = queue.remove(0);
			result.add(current);
			if(current.left() != null)
				queue.add(current.left());
			if(current.right() != null)
				queue.add(current.right());
		}
		return result;
	}
	
	/**
	 * 判断这颗二叉树是否平衡<br>
	 * 平衡的定义: 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过1。
	 * @param root
	 * @return
	 */
	public static <T> boolean isBalanced(ITreeNode<T> root){
		return treeDegree(root) != -1;
	}
	
	private static <T> int treeDegree(ITreeNode<T> root){
		if(root == null)
			return 0;
		int left = treeDegree(root.left());
		if(left == -1)
			return -1;
		int right = treeDegree(root.right());
		if(right == -1)
			return -1;
		return Math.abs((left - right)) < 2 ? 1 + Math.max(left, right) : -1;
	}
	
	/**
	 * 二叉树的最大深度
	 * @param root
	 * @return
	 */
	public static <T> int maxDepth(ITreeNode<T> root){
		if(root == null)
			return 0;
		if(root.left() == null && root.right() == null)
			return 1;
		return Math.max(maxDepth(root.left()), maxDepth(root.right())) + 1;
	}
	/**
	 * 二叉树的最小深度
	 * @param root
	 * @return
	 */
	public static <T> int minDepth(ITreeNode<T> root){
		if(root == null)
            return 0;
        if(root.left() == null && root.right() == null)
            return 1;
        if(root.left() == null && root.right() != null)
            return 1 + minDepth(root.right());
        if(root.left() != null && root.right() == null)
            return 1 + minDepth(root.left());
        return Math.min(minDepth(root.left()), minDepth(root.right())) + 1;
	}
	/**
	 * 判断两颗二叉树是否相同
	 * @param p
	 * @param q
	 * @return
	 */
	public static <T> boolean isSameTree(ITreeNode<T> p, ITreeNode<T> q){
		if(p == null && q == null)
			return true;
		if( (p == null && q != null) || (p != null & q == null) )
			return false;
		if( !p.value().equals(q.value()) )
			return false;
		return isSameTree(p.left(), q.left()) && isSameTree(p.right(), q.right());
	}
	/**
	 * 二叉树是否镜像对称, 例如[1,2,2,3,4,4,3]是对称的      
	 *          
	 * @param root
	 * @return
	 */
	public static <T> boolean isSymmetric(ITreeNode<T> root){
		/*
		 *                1
		 *              ↙  ↘
		 *             2      2
		 *           ↙ ↘  ↙ ↘
		 *          3    4  4    3
		 *          
		 *          这个叫镜像对称
		 */
		List<ITreeNode<T>> queue = new LinkedList<ITreeNode<T>>();
		queue.add(root);
		queue.add(root);
		ITreeNode<T> left;
		ITreeNode<T> right;
		while(!queue.isEmpty()){
			left = queue.remove(0);
			right = queue.remove(0);
			if(left == null && right == null)
				continue;
			if(left == null || right == null)
				return false;
			else if(!left.value().equals(right.value()))
				return false;
			queue.add(left.left());
			queue.add(right.left());
			queue.add(left.right());
			queue.add(right.right());
		}
		return true;
	}
}
