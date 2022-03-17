package javastudy.enum_.exercise;

/**
 * 使用接口组织枚举达到为枚举分组的效果
 */
public interface Member {
	// TODO 让枚举继承该接口，使得枚举实例可以用接口Member来引用
	enum TianMing implements Member {
		KallenKaslana("卡莲·卡斯兰娜"),
		RitaRossweisse("丽塔·洛丝薇瑟"),
		FuHua("浮华"),
		CarolePepper("卡萝尔·佩珀");
		private String name;

		TianMing(String name) {
			this.name = name;
		}

		public String toString() {
			return "TianMing{" +
					"name='" + name + '\'' +
					'}';
		}
	}

	enum XueYuan implements Member {
		KianaKaslana("琪亚娜·卡斯兰娜"),
		RaidenMei("雷电芽衣"),
		BronyaZaychik("布洛妮娅·扎伊切克"),
		MurataHimeko("无量塔姬子");
		private String name;

		XueYuan(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "XueYuan{" +
					"name='" + name + '\'' +
					'}';
		}
	}

	enum NiSang implements Member {
		RozaliyaOlenyeva("萝莎莉娅·阿琳"),
		LiliyaOlenyeva("莉莉娅·阿琳");
		private String name;

		NiSang(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "NiSang{" +
					"name='" + name + '\'' +
					'}';
		}
	}

	enum Other implements Member {
		YaeSakura("八重樱"),
		SeeleVollerei("希儿·芙乐艾"),
		Elysia("爱莉希雅");
		private String name;

		Other(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Other{" +
					"name='" + name + '\'' +
					'}';
		}
	}
}

class MemberTest {
	public static void main(String[] args) {
		// TODO 枚举类为final，虽不能继承，但可以实现接口，达到分组的同时还能使用同一引用
		Member member = Member.TianMing.FuHua;
		member = Member.NiSang.LiliyaOlenyeva;
		member = Member.Other.Elysia;
	}
}