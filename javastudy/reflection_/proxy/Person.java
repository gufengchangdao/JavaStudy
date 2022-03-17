package javastudy.reflection_.proxy;

/**
 * 演示空对象的使用
 * 使用空对象来替换null，可省去一些null检查，并且可以携带一些具体信息
 */
public class Person {
	public final String first;
	public final String last;
	public final String address;

	public Person(String first, String last, String address) {
		this.first = first;
		this.last = last;
		this.address = address;
	}

	public String toString() {
		return "Person: " + first + " " + last + " " + address;
	}

	public static class NullPerson extends Person implements Null {
		//构造器私有化，单例模式
		private NullPerson() {
			super("None", "None", "None");
		}

		public String toString() {
			return "NullPerson";
		}
	}

	/**
	 * 空对象，一般为单例，可用instanceOf、==或equals()来检测
	 */
	public static final Person NULL = new NullPerson();
} ///:~

/**
 * 标记接口，实现该接口的对象表示空对象，
 */
interface Null {
}

class Position {
	private String title;
	private Person person;

	public Position(String title, Person person) {
		this.title = title;
		this.person = person;
		if (person == null)
			person = Person.NULL;
	}

	public Position(String jobTitle) {
		title = jobTitle;
		person = Person.NULL;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person newPerson) {
		person = newPerson;
		if (person == null)
			person = Person.NULL;
	}

	public String toString() {
		return "Position: " + title + " " + person;
	}
}