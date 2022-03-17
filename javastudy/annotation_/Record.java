package javastudy.annotation_;

/**
 * 演示用的类，这个类标注释，Test类来读取注释信息
 */
public class Record {
    @Field_Method_Parameter_Annotation(describe = "编号",type = int.class)
    int id;

    @Field_Method_Parameter_Annotation(describe = "姓名",type = String.class)
    String name;

    @Construct_annotation()
    public Record() {
    }

    @Construct_annotation("含参数构造方法")
    public Record(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Field_Method_Parameter_Annotation(describe = "获得编号",type = int.class)
    public int getId() {
        return id;
    }

    @Field_Method_Parameter_Annotation(describe = "设置编号")
    public void setId(int id) {
        this.id = id;
    }
}
