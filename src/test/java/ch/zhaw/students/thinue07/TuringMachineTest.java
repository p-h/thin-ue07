package ch.zhaw.students.thinue07;

import org.junit.Test;

import java.util.stream.Stream;

import static ch.zhaw.students.thinue07.TuringMachine.*;
import static org.junit.Assert.assertTrue;

public class TuringMachineTest {
	@Test
	public void DeltaInputUniqueness() {
		final Alphabet[] alphabet = Alphabet.values();
		final State[] states = State.values();
		final Object[] inputs = Stream.of(states).flatMap(s -> Stream.of(alphabet).map(a -> new DeltaInput(s, a))).toArray();
		for (Object input1 : inputs) {
			for (Object input2 : inputs) {
				assertTrue(input1.equals(input2) == (input1.hashCode() == input2.hashCode()));
			}
		}
	}
}
