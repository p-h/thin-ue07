package ch.zhaw.students.thinue07;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TuringMachine {
	private static final int BAND_LENGTH = 16384;

	enum Alphabet {
		BLANK('␣'), ZERO('0'), ONE('1'), X('x'), Y('y'), Z('z');


		public char getValue() {
			return this.value;
		}

		private final char value;

		Alphabet(final char c) {
			this.value = c;
		}

		public static Alphabet fromChar(char c) {
			return Stream.of(Alphabet.values()).filter(a -> a.getValue() == c).findFirst().get();
		}
	}

	enum State {
		Q0,
		Q1,
		Q2,
		Q3,
		Q4,
		Q5,
		Q6,
		Q7,
		Q8,
		Q9,
		Q10,
		Q11,
		Q12,
		Q13,
		Q14,
		Q15,
		Q16,
		Q17,
		Q18,
		Q19,
		Q20,
	}

	enum Movement {
		LEFT(-1), STILL(0), RIGHT(1),;

		private final int value;

		Movement(final int i) {
			this.value = i;
		}

		public int getValue() {
			return value;
		}
	}

	static class DeltaInput {
		final State state;
		final Alphabet field;

		DeltaInput(State state, Alphabet field) {
			this.state = state;
			this.field = field;
		}

		@Override
		public boolean equals(final Object obj) {
			if (obj != null && obj instanceof DeltaInput) {
				DeltaInput other = (DeltaInput) obj;
				return this.state.equals(other.state) && this.field.equals(other.field);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return state.hashCode() ^ field.hashCode();
		}
	}

	static class DeltaNew {
		final State state;
		final Alphabet field;
		final Movement movement;

		DeltaNew(State state, Alphabet field, Movement movement) {
			this.state = state;
			this.field = field;
			this.movement = movement;
		}
	}


	private final static Map<DeltaInput, DeltaNew> Transitions = Collections.unmodifiableMap(new HashMap<DeltaInput, DeltaNew>() {{
		put(new DeltaInput(State.Q0, Alphabet.ONE), new DeltaNew(State.Q1, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q0, Alphabet.ZERO), new DeltaNew(State.Q3, Alphabet.X, Movement.RIGHT));

		put(new DeltaInput(State.Q1, Alphabet.BLANK), new DeltaNew(State.Q2, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q1, Alphabet.ZERO), new DeltaNew(State.Q1, Alphabet.BLANK, Movement.RIGHT));

		put(new DeltaInput(State.Q3, Alphabet.ONE), new DeltaNew(State.Q4, Alphabet.ONE, Movement.RIGHT));
		put(new DeltaInput(State.Q3, Alphabet.ZERO), new DeltaNew(State.Q3, Alphabet.ZERO, Movement.RIGHT));

		put(new DeltaInput(State.Q4, Alphabet.BLANK), new DeltaNew(State.Q18, Alphabet.BLANK, Movement.LEFT));
		put(new DeltaInput(State.Q4, Alphabet.ZERO), new DeltaNew(State.Q6, Alphabet.X, Movement.RIGHT));

		put(new DeltaInput(State.Q5, Alphabet.BLANK), new DeltaNew(State.Q2, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q5, Alphabet.ONE), new DeltaNew(State.Q5, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q5, Alphabet.X), new DeltaNew(State.Q5, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q5, Alphabet.Y), new DeltaNew(State.Q2, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q5, Alphabet.ZERO), new DeltaNew(State.Q5, Alphabet.BLANK, Movement.RIGHT));

		put(new DeltaInput(State.Q6, Alphabet.BLANK), new DeltaNew(State.Q19, Alphabet.BLANK, Movement.LEFT));
		put(new DeltaInput(State.Q6, Alphabet.ZERO), new DeltaNew(State.Q7, Alphabet.ZERO, Movement.RIGHT));

		put(new DeltaInput(State.Q7, Alphabet.BLANK), new DeltaNew(State.Q8, Alphabet.Y, Movement.RIGHT));
		put(new DeltaInput(State.Q7, Alphabet.Y), new DeltaNew(State.Q8, Alphabet.Y, Movement.RIGHT));
		put(new DeltaInput(State.Q7, Alphabet.ZERO), new DeltaNew(State.Q7, Alphabet.ZERO, Movement.RIGHT));

		put(new DeltaInput(State.Q8, Alphabet.BLANK), new DeltaNew(State.Q9, Alphabet.ZERO, Movement.LEFT));
		put(new DeltaInput(State.Q8, Alphabet.ZERO), new DeltaNew(State.Q8, Alphabet.ZERO, Movement.RIGHT));

		put(new DeltaInput(State.Q9, Alphabet.X), new DeltaNew(State.Q10, Alphabet.X, Movement.RIGHT));
		put(new DeltaInput(State.Q9, Alphabet.Y), new DeltaNew(State.Q9, Alphabet.Y, Movement.LEFT));
		put(new DeltaInput(State.Q9, Alphabet.ZERO), new DeltaNew(State.Q9, Alphabet.ZERO, Movement.LEFT));

		put(new DeltaInput(State.Q10, Alphabet.ZERO), new DeltaNew(State.Q11, Alphabet.X, Movement.RIGHT));

		put(new DeltaInput(State.Q11, Alphabet.BLANK), new DeltaNew(State.Q12, Alphabet.ZERO, Movement.RIGHT));
		put(new DeltaInput(State.Q11, Alphabet.Y), new DeltaNew(State.Q11, Alphabet.Y, Movement.RIGHT));
		put(new DeltaInput(State.Q11, Alphabet.ZERO), new DeltaNew(State.Q11, Alphabet.ZERO, Movement.RIGHT));

		put(new DeltaInput(State.Q12, Alphabet.BLANK), new DeltaNew(State.Q13, Alphabet.BLANK, Movement.LEFT));

		put(new DeltaInput(State.Q13, Alphabet.Y), new DeltaNew(State.Q14, Alphabet.Y, Movement.LEFT));
		put(new DeltaInput(State.Q13, Alphabet.ZERO), new DeltaNew(State.Q13, Alphabet.ZERO, Movement.LEFT));

		put(new DeltaInput(State.Q14, Alphabet.ONE), new DeltaNew(State.Q15, Alphabet.ONE, Movement.LEFT));
		put(new DeltaInput(State.Q14, Alphabet.X), new DeltaNew(State.Q14, Alphabet.ZERO, Movement.LEFT));
		put(new DeltaInput(State.Q14, Alphabet.ZERO), new DeltaNew(State.Q9, Alphabet.ZERO, Movement.LEFT));

		put(new DeltaInput(State.Q15, Alphabet.X), new DeltaNew(State.Q17, Alphabet.X, Movement.LEFT));
		put(new DeltaInput(State.Q15, Alphabet.ZERO), new DeltaNew(State.Q16, Alphabet.ZERO, Movement.LEFT));

		put(new DeltaInput(State.Q16, Alphabet.X), new DeltaNew(State.Q0, Alphabet.X, Movement.RIGHT));
		put(new DeltaInput(State.Q16, Alphabet.ZERO), new DeltaNew(State.Q16, Alphabet.ZERO, Movement.LEFT));

		put(new DeltaInput(State.Q17, Alphabet.BLANK), new DeltaNew(State.Q5, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q17, Alphabet.X), new DeltaNew(State.Q17, Alphabet.X, Movement.LEFT));

		put(new DeltaInput(State.Q18, Alphabet.BLANK), new DeltaNew(State.Q5, Alphabet.BLANK, Movement.RIGHT));
		put(new DeltaInput(State.Q18, Alphabet.ONE), new DeltaNew(State.Q18, Alphabet.ONE, Movement.LEFT));
		put(new DeltaInput(State.Q18, Alphabet.X), new DeltaNew(State.Q18, Alphabet.X, Movement.LEFT));
		put(new DeltaInput(State.Q18, Alphabet.ZERO), new DeltaNew(State.Q18, Alphabet.ZERO, Movement.LEFT));

		put(new DeltaInput(State.Q19, Alphabet.ONE), new DeltaNew(State.Q20, Alphabet.BLANK, Movement.LEFT));
		put(new DeltaInput(State.Q19, Alphabet.X), new DeltaNew(State.Q19, Alphabet.BLANK, Movement.LEFT));

		put(new DeltaInput(State.Q20, Alphabet.X), new DeltaNew(State.Q2, Alphabet.ZERO, Movement.LEFT));
		put(new DeltaInput(State.Q20, Alphabet.ZERO), new DeltaNew(State.Q20, Alphabet.ZERO, Movement.LEFT));
	}});

	private static final State INITIAL_STATE = State.Q0;
	private static final State ACCEPTING_STATE = State.Q2;

	private final boolean stepMode;
	private final Alphabet[] band;
	private int bandPosition;
	private State state;


	public TuringMachine(String input, boolean stepMode) {
		this.stepMode = stepMode;
		this.state = INITIAL_STATE;
		this.band = new Alphabet[BAND_LENGTH];
		for (int i = 0; i < this.band.length; i++) {
			this.band[i] = Alphabet.BLANK;
		}
		this.bandPosition = BAND_LENGTH / 2;

		char[] inputArr = input.toCharArray();
		for (int i = 0; i < inputArr.length && i <= this.band.length; i++) {
			this.band[this.bandPosition + i] = Alphabet.fromChar(inputArr[i]);
		}
	}

	public void run() {
		DeltaNew deltaNew;
		int numberOfComputations = 0;
		while ((deltaNew = Delta(new DeltaInput(this.state, this.band[this.bandPosition]))) != null) {
			if (stepMode) {
				printBandState();
				System.out.printf("δ(%s, %c) = (%s, %c, %s)\n", state, band[bandPosition].getValue(), deltaNew.state, deltaNew.field.getValue(), deltaNew.movement);
				try {
					System.in.read();
				} catch (IOException e) {
				}
			}
			this.state = deltaNew.state;
			this.band[bandPosition] = deltaNew.field;
			this.bandPosition += deltaNew.movement.getValue();

			numberOfComputations++;
		}

		if (!stepMode) {
			printBandState();
		}

		StringBuilder sb = new StringBuilder();
		for (Alphabet field : band) {
			if (field != Alphabet.BLANK) {
				sb.append(field.getValue());
			}
		}

		String result = sb.toString();

		System.out.printf("Result: %s\n", result);
		System.out.println();
		System.out.printf("Number of computations: %d\n", numberOfComputations);

	}

	private void printBandState() {
		System.out.printf("Current state: %s\n", this.state);
		System.out.println("               ↓");
		for (int i = bandPosition - 15; i < bandPosition + 15; i++) {
			System.out.print(band[i].getValue());
		}

		System.out.println();
	}

	private DeltaNew Delta(DeltaInput foo) {
		return Transitions.get(foo);
	}
}
