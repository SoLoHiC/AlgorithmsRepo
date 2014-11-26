package mainpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ACStringMatch1 {

	/**
	 * AC有限状态机的树节点定义
	 */
	public static class Node {
		public char val; // 当前节点字符
		public Node pre; // 父节点
		public HashMap<Character, Node> next; // 子节点列表
		public Set<String> words;// 当前节点路径所包含的单词
		public Node failShift; // 失败跳转节点

		public Node(char v) {
			this.val = v;
			this.next = new HashMap<Character, Node>();
			this.words = new HashSet<String>();
		}
	}

	/**
	 * 构建AC有限状态机(树)
	 * 
	 * @param dict
	 *            给定字典集/子集
	 * @return 生成的根节点
	 * @throws Exception
	 *             输入异常(初始给定的字典为空)
	 */
	public static Node buildTree(Set<String> dict) {
		Node root = new Node(' ');
		for (String word : dict) {
			Node node = root;
			for (int i = 0; i < word.length(); i++) {
				char cur = word.charAt(i);
				if (node.next.containsKey(cur)) {
					node = node.next.get(cur);
				} else {
					Node n = new Node(word.charAt(i));
					node.next.put(n.val, n);
					n.pre = node;
					node = n;
				}
			}
			node.words.add(word);
		}
		return root;
	}

	/**
	 * 基于BFS的失败状态跳转添加
	 * 
	 * @param root
	 *            已生成的AC有限状态机(树)根节点
	 */
	public static void addFailShift(Node root) {
		Deque<Node> queue = new ArrayDeque<Node>();
		queue.add(root);
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
					node.words.addAll(n.words);
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
	 * @return 当前节点所匹配的单词
	 */
	public static void printWords(Node node) {
		System.out.print(node.words);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String> dictionary = new HashSet<String>();
		String file = "./dataset/ac_string_match.txt";
		String input;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			for (String s : reader.readLine().split("\t")) {
				dictionary.add(s);
			}
			System.out.println("字典:" + dictionary);
			input = reader.readLine();
			System.out.println("待检测输入:" + input);
			reader.close();
			Node root = buildTree(dictionary);
			addFailShift(root);
			Node current = root;
			for (int i = 0; i < input.length(); i++) {
				char c = input.charAt(i);
				Node n = current.next.get(c);
				if (n != null) {
					current = n;
				} else if (current != root) {
					current = current.failShift;
					//失败状态跳转直到找到相对应的节点或回到根
					while ((n = current.next.get(c)) == null && current != root)
						current = current.failShift;
					current = n == null ? current : n;
				} else//状态机中不包括的状态,直接跳过
					continue;
				if (!current.words.isEmpty()) {
					printWords(current);
					System.out.println("," + i);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
