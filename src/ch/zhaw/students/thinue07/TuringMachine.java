package ch.zhaw.students.thinue07;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TuringMachine {
	public enum Alphabet {
		EMPTY(' '), ZERO('0'), ONE('1'), X('x'), Y('y'), Z('z');


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

	private enum State {
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
	}

	private enum Movement {
		LEFT(-1), STILL(0), RIGHT(1),;

		private final int value;

		Movement(final int i) {
			this.value = i;
		}

		public int getValue() {
			return value;
		}
	}

	private static class DeltaInput {
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

	private static class DeltaNew {
		final State state;
		final Alphabet field;
		final Movement movement;

		DeltaNew(State state, Alphabet field, Movement movement) {
			this.state = state;
			this.field = field;
			this.movement = movement;
		}
	}

	private final static Map<DeltaInput, DeltaNew> Transitions = new HashMap<DeltaInput, DeltaNew>() {{
		put(new DeltaInput(State.Q0, Alphabet.ONE), new DeltaNew(State.Q1, Alphabet.X, Movement.RIGHT));

		put(new DeltaInput(State.Q1, Alphabet.ONE), new DeltaNew(State.Q1, Alphabet.ONE, Movement.RIGHT));
		put(new DeltaInput(State.Q1, Alphabet.ZERO), new DeltaNew(State.Q2, Alphabet.ZERO, Movement.RIGHT));

		put(new DeltaInput(State.Q2, Alphabet.ONE), new DeltaNew(State.Q3, Alphabet.Y, Movement.RIGHT));

		put(new DeltaInput(State.Q3, Alphabet.ONE), new DeltaNew(State.Q3, Alphabet.ONE, Movement.RIGHT));
		put(new DeltaInput(State.Q3, Alphabet.Z), new DeltaNew(State.Q4, Alphabet.Z, Movement.RIGHT));
		put(new DeltaInput(State.Q3, Alphabet.EMPTY), new DeltaNew(State.Q4, Alphabet.Z, Movement.RIGHT));

		put(new DeltaInput(State.Q4, Alphabet.ONE), new DeltaNew(State.Q4, Alphabet.ONE, Movement.RIGHT));
		put(new DeltaInput(State.Q4, Alphabet.EMPTY), new DeltaNew(State.Q5, Alphabet.ONE, Movement.LEFT));

		put(new DeltaInput(State.Q5, Alphabet.Z), new DeltaNew(State.Q5, Alphabet.Z, Movement.LEFT));
		put(new DeltaInput(State.Q5, Alphabet.ONE), new DeltaNew(State.Q5, Alphabet.ONE, Movement.LEFT));
		put(new DeltaInput(State.Q5, Alphabet.Y), new DeltaNew(State.Q6, Alphabet.Y, Movement.RIGHT));

		put(new DeltaInput(State.Q6, Alphabet.ONE), new DeltaNew(State.Q6, Alphabet.Y, Movement.RIGHT));

		put(new DeltaInput(State.Q7, Alphabet.ONE), new DeltaNew(State.Q7, Alphabet.ONE, Movement.RIGHT));
		put(new DeltaInput(State.Q7, Alphabet.Z), new DeltaNew(State.Q7, Alphabet.Z, Movement.RIGHT));
		put(new DeltaInput(State.Q7, Alphabet.EMPTY), new DeltaNew(State.Q8, Alphabet.ONE, Movement.RIGHT));

		put(new DeltaInput(State.Q8, Alphabet.EMPTY), new DeltaNew(State.Q9, Alphabet.EMPTY, Movement.LEFT));

		put(new DeltaInput(State.Q9, Alphabet.ONE), new DeltaNew(State.Q9, Alphabet.ONE, Movement.LEFT));
		put(new DeltaInput(State.Q9, Alphabet.Z), new DeltaNew(State.Q10, Alphabet.Z, Movement.LEFT));

		put(new DeltaInput(State.Q10, Alphabet.Y), new DeltaNew(State.Q10, Alphabet.ONE, Movement.LEFT));
		put(new DeltaInput(State.Q10, Alphabet.ZERO), new DeltaNew(State.Q11, Alphabet.ZERO, Movement.LEFT));
		put(new DeltaInput(State.Q10, Alphabet.ONE), new DeltaNew(State.Q5, Alphabet.ONE, Movement.LEFT));

		put(new DeltaInput(State.Q11, Alphabet.X), new DeltaNew(State.Q12, Alphabet.X, Movement.LEFT));
		put(new DeltaInput(State.Q11, Alphabet.ONE), new DeltaNew(State.Q15, Alphabet.ONE, Movement.LEFT));

		put(new DeltaInput(State.Q1, Alphabet.ONE), new DeltaNew(State.Q1, Alphabet.X, Movement.RIGHT));
		put(new DeltaInput(State.Q1, Alphabet.ONE), new DeltaNew(State.Q1, Alphabet.X, Movement.RIGHT));

		put(new DeltaInput(State.Q12, Alphabet.X), new DeltaNew(State.Q12, Alphabet.X, Movement.LEFT));
		put(new DeltaInput(State.Q12, Alphabet.EMPTY), new DeltaNew(State.Q13, Alphabet.EMPTY, Movement.RIGHT));

		put(new DeltaInput(State.Q13, Alphabet.ONE), new DeltaNew(State.Q13, Alphabet.EMPTY, Movement.RIGHT));
		put(new DeltaInput(State.Q13, Alphabet.ZERO), new DeltaNew(State.Q13, Alphabet.EMPTY, Movement.RIGHT));
		put(new DeltaInput(State.Q13, Alphabet.X), new DeltaNew(State.Q13, Alphabet.EMPTY, Movement.RIGHT));
		put(new DeltaInput(State.Q13, Alphabet.Z), new DeltaNew(State.Q14, Alphabet.EMPTY, Movement.RIGHT));

		put(new DeltaInput(State.Q15, Alphabet.ONE), new DeltaNew(State.Q15, Alphabet.ONE, Movement.LEFT));
		put(new DeltaInput(State.Q15, Alphabet.X), new DeltaNew(State.Q0, Alphabet.X, Movement.RIGHT));
	}};

	private static final State INITIAL_STATE = State.Q0;

	private final boolean verbose;
	private final Alphabet[] band;
	private int bandPosition;
	private State state;


	public TuringMachine(String input, boolean verbose) {
		this.verbose = verbose;
		this.state = INITIAL_STATE;
		this.band = new Alphabet[Integer.MAX_VALUE];
		for (int i = 0; i < this.band.length; i++) {
			this.band[i] = Alphabet.EMPTY;
		}
		this.bandPosition = Integer.MAX_VALUE / 2;

		char[] inputArr = input.toCharArray();
		for (int i = 0; i < inputArr.length && i <= this.band.length; i++) {
			this.band[this.bandPosition + i] = Alphabet.fromChar(inputArr[i]);
		}
	}

	public void Run() {
		DeltaNew deltaNew;
		while ((deltaNew = Delta(new DeltaInput(INITIAL_STATE, this.band[this.bandPosition]))) != null) {
			this.state = deltaNew.state;
			this.band[bandPosition] = deltaNew.field;
			this.bandPosition += deltaNew.movement.getValue();
		}
	}

	private DeltaNew Delta(DeltaInput foo) {
		return Transitions.get(foo);
	}
}
