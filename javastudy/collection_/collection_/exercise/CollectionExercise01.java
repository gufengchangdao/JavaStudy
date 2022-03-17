package javastudy.collection_.collection_.exercise;

import java.util.*;

/**
 * 集合小练习
 * 题目要求：目前有三个集合，
 * 一个管理员映射(空的)
 * 一个所有员工集合映射
 * 一个要裁员的员工id集
 * 现在要求把员工集合中的要裁员的员工去掉，并从剩下的员工挑出来三个id最小的人放进管理员映射中，按照id递增打印管理员和剩余员工
 */
public class CollectionExercise01 {
    private static Map<Integer, Member> manaMap; //老板
    private static Map<Integer, Member> staffMap; //所有员工
    private static Set<Integer> terminatedIDs; //要裁掉的员工

    public static void main(String[] args) {
        init();
        solution();
    }

    private static void solution() {
        staffMap.keySet().removeAll(terminatedIDs); //员工键值对中删除相同的键值的数据
        Integer[] ids = staffMap.keySet().toArray(Integer[]::new); //先把员工键值转为数组
        Arrays.sort(ids); //排序
        manaMap.put(ids[0], staffMap.get(ids[0])); //添加
        manaMap.put(ids[1], staffMap.get(ids[1]));
        manaMap.put(ids[2], staffMap.get(ids[2]));
        staffMap.remove(ids[0]); //移除
        staffMap.remove(ids[1]);
        staffMap.remove(ids[2]);

        System.out.println("管理员：");
        for (Member manager : manaMap.values()) {
            System.out.println(manager + " ");
        }
        System.out.println("\n普通员工");
        for (Member member : staffMap.values()) {
            System.out.println(member + " ");
        }
    }

    private static void init() {
        manaMap = new HashMap<>();
        staffMap = new HashMap<>();
        staffMap.put(1, new Member(1, "李小龙"));
        staffMap.put(2, new Member(2, "李云龙"));
        staffMap.put(3, new Member(3, "李嘉欣"));
        staffMap.put(4, new Member(4, "王思"));
        staffMap.put(5, new Member(5, "王思琪"));
        staffMap.put(6, new Member(6, "那类多"));
        staffMap.put(7, new Member(7, "卡卡西"));
        staffMap.put(8, new Member(8, "白助"));
        staffMap.put(9, new Member(9, "加布里"));
        staffMap.put(10, new Member(10, "斯福特曼"));
        terminatedIDs = Set.of(1, 3, 5, 7, 9);
    }

    private static class Member {
        public int id;
        public String name;

        public Member(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Member{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
