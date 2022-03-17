package javastudy.swing_.preference;

import java.util.prefs.Preferences;

/**
 * 演示首选项类Preferences的使用
 */
public class PreferencesTest {
	public static void main(String[] args) throws Exception {
        /* TODO 首选项类Preferences
            1.  引入问题：Properties类可以很轻松的加载配置信息，但是有些操作系统没有主目录的概念，很难为配置信息找一个统一的位置。关于配置文
            件的命名没有标准约定，多个java应用容易发生命名冲突
            2. 引入Preferences：Preferences提供这样一个中心存储库，在Windows里Preferences使用注册表(一个数据库)来储存信息，在Linux中，
            信息存储在本地文件系统中。当然，存储库是透明的
            3. Preferences有一个树状结构，建议配置节点路径和包名一致
            4. 存储库的各个节点分别有一个单独的键值对表，可以用来存储数值、字符串和字节数组，但是不能存储可串行话的对象(你想的话可以存字节数组嘛)
            5. 每个程序用户分别有一棵树，另外还有一颗系统树，用来存放所有用户的公共信息，Preferences使用操作系统的“当前用户”概念来访问相应的用户树
            6. 相当于把键值对保存到数据库中还省去了繁琐的操作
        */

		// TODO 1. 要访问树的节点，需要先获取根节点
		// 获取调用程序的用户的首选项根节点
		Preferences root = Preferences.userRoot();
		// 或者获取系统根节点
		// Preferences root = Preferences.systemRoot();

		// TODO 2. 访问节点，由节点路径名获取首选项节点
		Preferences node = root.node("/com/horstmann/corejava/javastudy/swing_/preference");

		// 如果节点的路径名等于类的包名，可以直接使用下面的方法
		// Preferences node = Preferences.userNodeForPackage(PreferencesTest.class);
		// Preferences node = Preferences.systemNodeForPackage(PreferencesTest.class);

		// TODO 3. 获取值
		// 没有就使用默认值，使用getXXX()方法必须提供默认值
		// 这里第一次运行时，注册表中还没有数据就会使用默认值，等存入数据后再运行程序就可以获取上一次的数据了
		System.out.println("name: " + node.get("name", "name默认值"));
		System.out.println("age: " + node.getInt("age", 0));
		System.out.println("isOld: " + node.getBoolean("isOld", false));
		System.out.println("property: " + node.getFloat("property", 0f));

		for (String key : node.keys()) {
			System.out.println(key + ": " + node.get(key, "默认值"));
		}

		// TODO 4. 存储键值对
		// node.put("name", "齐天大圣");
		// node.putInt("age", 20);
		// node.putBoolean("isOld", false);
		// node.putFloat("property", 9999.99f);

		// node.clear(); //删除首选项节点信息，不包括子节点

		// TODO 5. 数据的导出与导入
		// 为了方便数据迁移，还可以导出一个子树，数据用XML格式保存
		// node.exportSubtree(); //包含子节点
		// node.exportNode(new FileOutputStream("src/javastudy/swing_/preference/preference.xml")); //不包含子节点

		// 数据导入存储库
		// Preferences.importPreferences(new FileInputStream("src/javastudy/swing_/preference/preference.xml"));
	}
}
