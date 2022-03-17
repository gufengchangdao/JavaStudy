package javastudy.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * 随机数生成器，用于生成测试数据，
 * Random使用常量初始化，所以每次数据一样
 */
public class RandomGenerator {
	private static Random r = new Random();
	private static final int MOD = 10000;

	/**
	 * 重新设置种子
	 */
	public static void setNowRandom() {
		r.setSeed(System.currentTimeMillis());
	}


	public static class Boolean implements Generator<java.lang.Boolean> {
		public java.lang.Boolean next() {
			return r.nextBoolean();
		}
	}

	public static class Byte implements Generator<java.lang.Byte> {
		public java.lang.Byte next() {
			return (byte) r.nextInt();
		}
	}

	public static class Character implements Generator<java.lang.Character> {
		public java.lang.Character next() {
			return CountingGenerator.chars[r.nextInt(CountingGenerator.chars.length)];
		}
	}

	public static class String extends CountingGenerator.String {
		{
			cg = new Character();// 把cg更换为Character随机生成的对象
		}

		public String() {
		}

		public String(int length) {
			super(length);
		}
	}

	public static class Short implements Generator<java.lang.Short> {
		public java.lang.Short next() {
			return (short) r.nextInt();
		}
	}

	public static class Integer implements Generator<java.lang.Integer> {
		private int mod = 10000;

		public Integer() {
		}

		public Integer(int modulo) {
			mod = modulo;
		}

		public java.lang.Integer next() {
			return r.nextInt(mod);
		}

		public static java.lang.Integer[] getIntegerArray(int count) {
			java.lang.Integer[] integers = new java.lang.Integer[count];
			for (int i = 0; i < count; i++) {
				integers[i] = r.nextInt(MOD);
			}
			return integers;
		}
	}

	public static class Long implements Generator<java.lang.Long> {
		private int mod = 10000;

		public Long() {
		}

		public Long(int modulo) {
			mod = modulo;
		}

		public java.lang.Long next() {
			return r.nextLong() % mod;
		}
	}

	public static class Float implements Generator<java.lang.Float> {
		/**
		 * 去掉小数点前两位以外的所有数字
		 */
		public java.lang.Float next() {
			int trimmed = Math.round(r.nextFloat() * 100);
			return ((float) trimmed) / 100;
		}
	}

	public static class Double implements Generator<java.lang.Double> {
		/**
		 * 去掉小数点前四位以外的所有数字
		 */
		public java.lang.Double next() {
			long trimmed = Math.round(r.nextDouble() * 10000);
			return ((double) trimmed) / 10000;
		}
	}

	public static class Collection {
		public static ArrayList<java.lang.Integer> getRandomIntegerList(int count) {
			ArrayList<java.lang.Integer> list = new ArrayList<>(count);
			for (int i = 0; i < count; i++) {
				list.add(r.nextInt(MOD));
			}
			return list;
		}

		/**
		 * 返回一个以 0->v1,1->v2,2->v3,...这样形式的HashMap，其中v是随机的整数
		 *
		 * @param count 键值对个数
		 * @return 填充后的HashMap
		 */
		public static HashMap<java.lang.Integer, java.lang.Integer> getRandomHashMap(int count) {
			HashMap<java.lang.Integer, java.lang.Integer> map = new HashMap<>();
			for (int i = 0; i < count; i++) {
				map.put(i, r.nextInt(MOD));
			}
			return map;
		}
	}


}
