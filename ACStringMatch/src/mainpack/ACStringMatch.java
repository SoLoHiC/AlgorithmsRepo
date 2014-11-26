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
	 * AC����״̬�������ڵ㶨��
	 */
	public static class Node {
		public char val; // ��ǰ�ڵ��ַ�
		public boolean inDict; // �Ӹ�����ǰ�ڵ���ַ������Ƿ��ѹ���һ���ֵ��еĵ���
		public Node pre; // ���ڵ�
		public HashMap<Character, Node> next; // �ӽڵ��б�
		public Node failShift; // ʧ����ת�ڵ�

		public Node(char v) {
			this.val = v;
			this.inDict = false;
			this.next = new HashMap<Character, Node>();
		}
	}

	/**
	 * ����DFS��AC����״̬��(��)����
	 * 
	 * @param pre
	 *            ���ڵ�
	 * @param dict
	 *            �����ֵ伯/�Ӽ�
	 * @return ���ɵĸ��ڵ�/�ӽڵ�
	 * @throws Exception
	 *             �����쳣(��ʼ�������ֵ�Ϊ��)
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
	 * ����BFS��ʧ��״̬��ת���
	 * 
	 * @param root
	 *            �����ɵ�AC����״̬��(��)���ڵ�
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
	 * ���ƥ��ĵ���
	 * 
	 * @param node
	 *            ��ǰ״̬�ڵ�
	 * @return ��ǰƥ��ĵ���
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
