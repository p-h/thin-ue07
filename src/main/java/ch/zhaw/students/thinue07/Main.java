package ch.zhaw.students.thinue07;

import java.util.Optional;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) {
		boolean stepMode = Stream.of(args).anyMatch("-s"::equals);
		Optional<String> input = Stream.of(args).filter(a -> a.matches("0*10*")).findFirst();
		if (input.isPresent()) {
			TuringMachine myMachine = new TuringMachine(input.get(), stepMode);
			myMachine.run();
		} else {
			System.out.println("Please enter a valid input string");
		}
	}
}
