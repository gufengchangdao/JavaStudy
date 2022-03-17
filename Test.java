import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Test {
	public static void main(String[] args) {
		Integer[] integers ={1,2,3,4};
		Stream<Integer> stream = Arrays.stream(integers);
		Integer i;
		Stream<Integer> stream1 = stream.map(integer -> {
			return integer;
		});
		System.out.println(stream.count());
	}
}