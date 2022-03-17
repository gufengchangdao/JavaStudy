import java.util.stream.Stream;

public class Main {
	public static void main(String[] args) {
		Stream<Integer> stream = Stream.of(1, 2, 3, 4);
		Stream<Integer> integerStream = stream.filter(integer -> {
			System.out.println("Main.main()");
			return integer > 2;
		});

	}
}
