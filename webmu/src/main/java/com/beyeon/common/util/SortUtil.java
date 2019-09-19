package com.beyeon.common.util;

public class SortUtil {
	public static void main(String[] args) {
		String ss = "chao,zhao,shao";
		String[] s = ss.split(",");
		StringBuffer sb = new StringBuffer();
		for (String t : s) {
			sb.append(t.substring(0, 1));
		}
		System.out.println(sb.toString());
	}

	public String getFirst(String ss) {
		if (ss == null) {
			return null;
		}
		String[] s = ss.split(",");
		StringBuffer sb = new StringBuffer();
		for (String t : s) {
			sb.append(t.substring(0, 1)).append(",");
		}
		if (sb.toString().length() > 0) {
			return sb.toString().substring(0, sb.toString().length() - 1);
		} else {
			return sb.toString();
		}
	}

	public String sort_new(String[] spell) {
		int max = 0;
		for (int i = 0; i < spell.length; i++) {
			if (max < spell[i].split(",").length) {
				max = spell[i].split(",").length;
			}
		}
		int tt = max > spell.length ? max : spell.length;
		// int tt = spell.length;
		int[] b = new int[tt];
		for (int i = 0; i < tt; i++) {
			b[i] = i;
		}
		String[][] n_s = new String[spell.length][max]; // 定义一个二维数组，里面存放所有要组合的数据
		for (int i = 0; i < spell.length; i++) {
			for (int j = 0; j < max; j++) {
				String[] temp = spell[i].split(",");
				for (int n = 0; n < temp.length; n++) {
					n_s[i][n] = temp[n];
				}

				if (spell[i].split(",").length < max) {
					for (int m = (max - 1); m >= spell[i].split(",").length; m--) {
						n_s[i][m] = " ";
					}
				}
			}
		}
		StringBuffer sb = new StringBuffer();
		fun(sb, tt, b, null, n_s, max);
		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * @param level
	 *            嵌套的层次
	 * @param a
	 *            待传的数组
	 * @param rs
	 *            首次调用的时候，为null
	 * @param n_s
	 *            存放拼音的二维数组
	 * @param max
	 *            二维数组的Y最大值
	 */
	public void fun(StringBuffer sb, int level, int a[], int[] rs, String[][] n_s, int max) {
		if (rs == null)
			rs = new int[a.length];
		for (int i = 0; i < a.length; i++) {
			if (level <= 0) {
				StringBuffer sb1 = new StringBuffer();
				for (int k = 0; k < rs.length; k++) {
					if (rs[k] > max) {
						return;
					} else {
						sb1.append(rs[k]);
					}
				}
				// System.out.println(sb1.toString());
				String temp = printFull(sb1.toString(), max, n_s);
				if (!temp.equals("")) {
					if (sb.toString().indexOf(temp) < 0) {
						sb.append(temp).append(";");
					}
				}
				return;
			} else {
				rs[rs.length - level] = a[i];
				fun(sb, level - 1, a, rs, n_s, max);
			}
		}
	}

	/**
	 * 根据坐标换算出对应的拼音
	 * 
	 * @param n
	 *            坐标X
	 * @param max
	 *            坐标Y的最大值
	 * @param n_s
	 *            存放多音字的二维数组
	 * @return
	 */
	public String printFull(String n, int max, String[][] n_s) {
		int len = n.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			String t = n.substring(i, i + 1);
			int y = Integer.valueOf(t).intValue();
			if (y >= max || i >= n_s.length) {
				return sb.toString();
			} else {
				if (!n_s[i][y].equals(" ")) {
					sb.append(n_s[i][y]);
				} else {
					return "";
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 冒泡排序(降序)
	 */
	public static void bubble(int[] x) {
		for (int i = 0; i < x.length; i++) {
			for (int j = i + 1; j < x.length; j++) {
				if (x[i] < x[j]) {
					int temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
			}
		}
	}

	/**
	 * 冒泡排序(降序)
	 */
	public static void bubble(Integer[] x) {
		for (int i = 0; i < x.length; i++) {
			for (int j = i + 1; j < x.length; j++) {
				if (x[i] < x[j]) {
					int temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
			}
		}
	}

	/**
	 * 选择排序(升序)
	 */
	public static void choose(int[] x) {
		for (int i = 0; i < x.length; i++) {
			int lowerIndex = i;
			// 找出最小的一个索引
			for (int j = i + 1; j < x.length; j++) {
				if (x[j] < x[lowerIndex]) {
					lowerIndex = j;
				}
			}
			// 交换
			int temp = x[i];
			x[i] = x[lowerIndex];
			x[lowerIndex] = temp;
		}
	}

	/**
	 * 选择排序(升序)
	 */
	public static void choose(Integer[] x) {
		for (int i = 0; i < x.length; i++) {
			int lowerIndex = i;
			// 找出最小的一个索引
			for (int j = i + 1; j < x.length; j++) {
				if (x[j] < x[lowerIndex]) {
					lowerIndex = j;
				}
			}
			// 交换
			int temp = x[i];
			x[i] = x[lowerIndex];
			x[lowerIndex] = temp;
		}
	}

	/**
	 * 插入排序(升序)
	 */
	public static void insert(int[] x) {
		// i从一开始，因为第一个数已经是排好序的啦
		for (int i = 1; i < x.length; i++) {
			for (int j = i; j > 0; j--) {
				if (x[j] < x[j - 1]) {
					int temp = x[j];
					x[j] = x[j - 1];
					x[j - 1] = temp;
				}
			}
		}
	}

	/**
	 * 希尔排序(升序)
	 */
	public static void shell(int[] x) {
		// 分组
		for (int increment = x.length / 2; increment > 0; increment /= 2) {
			// 每个组内排序
			for (int i = increment; i < x.length; i++) {
				int temp = x[i];
				int j = 0;
				for (j = i; j >= increment; j -= increment) {
					if (temp < x[j - increment]) {
						x[j] = x[j - increment];
					} else {
						break;
					}
				}
				x[j] = temp;
			}
		}
	}

}
