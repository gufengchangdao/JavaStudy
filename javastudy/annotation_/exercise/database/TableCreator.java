//: annotations/database/TableCreator.java
// Reflection-based annotation processor.
// {Args: annotations.database.Member}
package javastudy.annotation_.exercise.database;

import javastudy.annotation_.exercise.database.type.*;
import javastudy.utilities.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static javastudy.annotation_.exercise.database.type.TypeEnum.*;

/**
 * 用于数据表生成，就只是利用反射读取注解类里面的信息生成SQL语句而已
 */
public class TableCreator {
	private static EnumMap<TypeEnum, Compound> enumMap = new EnumMap<>(TypeEnum.class);

	static {
		enumMap.put(CHAR, ((field, annotation, list) -> {
			String columnName;
			SQLChar sqlChar = (SQLChar) annotation;
			if (sqlChar.name().length() < 1)
				columnName = StringUtils.uppercaseToUnderline(field.getName());
			else
				columnName = sqlChar.name();
			list.add(columnName + " CHAR(" + sqlChar.value() + ")" + getConstraints(sqlChar.constraints()));
		}));
		enumMap.put(TEXT, ((field, annotation, list) -> {
			String columnName;
			SQLText text = (SQLText) annotation;
			if (text.name().length() < 1)
				columnName = StringUtils.uppercaseToUnderline(field.getName());
			else
				columnName = text.name();
			list.add(columnName + " TEXT" + getConstraints(text.constraints()));
		}));
		enumMap.put(DATETIME, ((field, annotation, list) -> {
			String columnName;
			SQLDateTime dateTime = (SQLDateTime) annotation;
			if (dateTime.name().length() < 1)
				columnName = StringUtils.uppercaseToUnderline(field.getName());
			else
				columnName = dateTime.name();
			list.add(columnName + " DATETIME" + getConstraints(dateTime.constraints()));
		}));
		enumMap.put(STRING, ((field, annotation, list) -> {
			String columnName;
			SQLString sString = (SQLString) annotation;
			// Use field name if name not specified.
			if (sString.name().length() < 1)
				columnName = StringUtils.uppercaseToUnderline(field.getName());
			else
				columnName = sString.name();
			list.add(columnName + " VARCHAR(" + sString.value() + ")" + getConstraints(sString.constraints()));
		}));
		enumMap.put(INTEGER, ((field, annotation, list) -> {
			String columnName;
			SQLInteger sInt = (SQLInteger) annotation;
			// Use field name if name not specified
			if (sInt.name().length() < 1)
				columnName = StringUtils.uppercaseToUnderline(field.getName());
			else
				columnName = sInt.name();
			list.add(columnName + " " + sInt.typeName() + getConstraints(sInt.constraints()));
		}));
		enumMap.put(TIMESTAMP, ((field, annotation, list) -> {
			String columnName;
			SQLTimestamp timestamp = (SQLTimestamp) annotation;
			if (timestamp.name().length() < 1)
				columnName = StringUtils.uppercaseToUnderline(field.getName());
			else
				columnName = timestamp.name();
			list.add(columnName + " TIMESTAMP" + getConstraints(timestamp.constraints()));
		}));
	}

	public static void main(String[] args) throws Exception {
		Class<?>[] classes = {Member.class};
		// 遍历所有类
		for (Class<?> cl : classes) {
			// 获取表名，如果没有设置表名，则默认为类名
			DBTable dbTable = cl.getAnnotation(DBTable.class);
			if (dbTable == null) {
				System.out.println(cl.getName() + "没有注释表名");
				continue;
			}
			String tableName = dbTable.name();
			if (tableName.length() < 1)
				tableName = cl.getName().toLowerCase();

			// 遍历所有字段，用字段创建表的字段
			List<String> columnDefs = new ArrayList<String>();
			for (Field field : cl.getDeclaredFields()) {
				field.setAccessible(true);
				Annotation[] anns = field.getAnnotations();
				// 该字段没有注释，不存入表内
				if (anns.length < 1) continue;
				// TODO 这里只读取第一个注解
				TypeEnum type; //获取注释类的TYPE元素值
				try {
					type = (TypeEnum) anns[0].getClass().getField("type").get(anns[0]);
				} catch (NoSuchFieldException e) {
					System.err.println("字段" + field + "的第一个注解不是规定的注解类");
					continue;
				}
				// 生成SQL语句
				enumMap.get(type).work(field, anns[0], columnDefs);

				// 使用当前列表中的字段生成创建表的代码
				StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
				for (String columnDef : columnDefs)
					createCommand.append("\n    ").append(columnDef).append(",");
				// Remove trailing comma
				String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ");";
				System.out.println("/* Table Creation SQL for " + tableName + " is : */\n" + tableCreate);
			}
		}
	}

	// 生成约束字符串
	private static String getConstraints(Constraints con) {
		String constraints = "";
		if (!con.allowNull())
			constraints += " NOT NULL";
		if (con.primaryKey())
			constraints += " PRIMARY KEY";
		if (con.unique())
			constraints += " UNIQUE";
		return constraints;
	}
}

// /* Table Creation SQL for member is : */
// CREATE TABLE member(
// 	id INT NOT NULL);
// /* Table Creation SQL for member is : */
// CREATE TABLE member(
// 	id INT NOT NULL,
// 	name CHAR(10) NOT NULL);
// 	/* Table Creation SQL for member is : */
// 	CREATE TABLE member(
// 	id INT NOT NULL,
// 	name CHAR(10) NOT NULL,
// 	department VARCHAR(255) NOT NULL);
// 	/* Table Creation SQL for member is : */
// 	CREATE TABLE member(
// 	id INT NOT NULL,
// 	name CHAR(10) NOT NULL,
// 	department VARCHAR(255) NOT NULL,
// 	salary MEDIUMINT NOT NULL);
// 	/* Table Creation SQL for member is : */
// 	CREATE TABLE member(
// 	id INT NOT NULL,
// 	name CHAR(10) NOT NULL,
// 	department VARCHAR(255) NOT NULL,
// 	salary MEDIUMINT NOT NULL,
// 	job TEXT NOT NULL);
// 	/* Table Creation SQL for member is : */
// 	CREATE TABLE member(
// 	id INT NOT NULL,
// 	name CHAR(10) NOT NULL,
// 	department VARCHAR(255) NOT NULL,
// 	salary MEDIUMINT NOT NULL,
// 	job TEXT NOT NULL,
// 	skin_num TINYINT NOT NULL);
// 	/* Table Creation SQL for member is : */
// 	CREATE TABLE member(
// 	id INT NOT NULL,
// 	name CHAR(10) NOT NULL,
// 	department VARCHAR(255) NOT NULL,
// 	salary MEDIUMINT NOT NULL,
// 	job TEXT NOT NULL,
// 	skin_num TINYINT NOT NULL,
// 	create_time TIMESTAMP NOT NULL);
