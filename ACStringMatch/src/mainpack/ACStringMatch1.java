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
	 * AC����״̬�������ڵ㶨��
	 */
	public static class Node {
		public char val; // ��ǰ�ڵ��ַ�
		public Node pre; // ���ڵ�
		public HashMap<Character, Node> next; // �ӽڵ��б�
		public Set<String> words;// ��ǰ�ڵ�·���������ĵ���
		public Node failShift; // ʧ����ת�ڵ�

		public Node(char v) {
			this.val = v;
			this.next = new HashMap<Character, Node>();
			this.words = new HashSet<String>();
		}
	}

	/**
	 * ����AC����״̬��(��)
	 * 
	 * @param dict
	 *            �����ֵ伯/�Ӽ�
	 * @return ���ɵĸ��ڵ�
	 * @throws Exception
	 *             �����쳣(��ʼ�������ֵ�Ϊ��)
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
	 * ����BFS��ʧ��״̬��ת���
	 * 
	 * @param root
	 *            �����ɵ�AC����״̬��(��)���ڵ�
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
	 * ���ƥ��ĵ���
	 * 
	 * @param node
	 *            ��ǰ״̬�ڵ�
	 * @return ��ǰ�ڵ���ƥ��ĵ���
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
			System.out.println("�ֵ�:" + dictionary);
			input = reader.readLine();
			System.out.println("���������:" + input);
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
					//ʧ��״̬��תֱ���ҵ����Ӧ�Ľڵ��ص���
					while ((n = current.next.get(c)) == null && current != root)
						current = current.failShift;
					current = n == null ? current : n;
				} else//״̬���в�������״̬,ֱ������
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
