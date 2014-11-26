package mainpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ACStringMatch {

	/**
	 * AC有限状态机的树节点定义
	 */
	public static class Node {
		public char val; // 当前节点字符
		public boolean inDict; // 从根到当前节点的字符序列是否已构成一个字典中的单词
		public Node pre; // 父节点
		public HashMap<Character, Node> next; // 子节点列表
		public Node failShift; // 失败跳转节点

		public Node(char v) {
			this.val = v;
			this.inDict = false;
			this.next = new HashMap<Character, Node>();
		}
	}

	/**
	 * 基于DFS的AC有限状态机(树)生成
	 * 
	 * @param pre
	 *            父节点
	 * @param dict
	 *            给定字典集/子集
	 * @return 生成的根节点/子节点
	 * @throws Exception
	 *             输入异常(初始给定的字典为空)
	 */
	public static void buildTree(Node root, List<String> dict) throws Exception {
		if (dict.isEmpty())
			throw new Exception("Dictionary is null!");
		while (!dict.isEmpty()) {
			char c = dict.get(0).charAt(0);
			Node node = new Node(c);
			node.pre = root;
			List<String> subDict = new ArrayList<String>();
			Iterator<String> iter = dict.iterator();
			while (iter.hasNext()) {
				String s = iter.next();
				if (s.charAt(0) == c) {
					if (s.length() > 1)
						subDict.add(s.substring(1));
					else
						node.inDict = true;
					iter.remove();
				}
			}
			if (!subDict.isEmpty())
				buildTree(node, subDict);
			root.next.put(node.val, node);
		}
	}

	/**
	 * 基于BFS的失败状态跳转添加
	 * 
	 * @param root
	 *            已生成的AC有限状态机(树)根节点
	 */
	public static void addFailShift(Node root) {
		Deque<Node> queue = new ArrayDeque<Node>();
		queue.addLast(root);
		while (!queue.isEmpty()) {
			Node node = queue.removeFirst();
			for (Node n : node.next.values()) {
				queue.addLast(n);
			}
			node.failShift = root;
			Node pre = node.pre;
			while (pre != null && pre != root) {
				Node n = pre.failShift.next.get(node.val);
				if (n != null) {
					node.failShift = n;
					break;
				}
				pre = pre.pre;
			}
		}
	}

	/**
	 * 输出匹配的单词
	 * 
	 * @param node
	 *            当前状态节点
	 * @return 当前匹配的单词
	 */
	public static String getWord(Node node) {
		StringBuilder rstBuilder = new StringBuilder();
		while (node.pre != null) {
			rstBuilder.insert(0, node.val);
			node = node.pre;
		}
		return rstBuilder.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> dictionary = new ArrayList<String>();
		String file = "./dataset/ac_string_match2.txt";
		String input;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			for (String s : reader.readLine().split("\t")) {
				dictionary.add(s);
			}
			System.out.println(dictionary);
			input = reader.readLine();
			System.out.println(input);
			reader.close();
			Node root = new Node(' ');
			buildTree(root, dictionary);
			addFailShift(root);
			Node current = root;
			for (int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				Node n = current.next.get(c);
				if (n != null)
					current = n;
				else
					current = current.failShift;
				if (current.inDict)
					System.out.printf("%s,%d\n", getWord(current), i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
