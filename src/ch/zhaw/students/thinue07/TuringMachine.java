package ch.zhaw.students.thinue07;

import java.util.stream.Stream;

public class TuringMachine {
	public enum Alphabet {
		EMPTY(' '), ONE('1'), X('x');


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
		Q15,
	}

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
		// TODO: implement running
	}

	private void Delta() {
		// TODO: Implement output
		switch (State.Q0) {
			case Q0:
				switch (this.band[this.bandPosition]) {
					case EMPTY:
						break;
					case ONE:
						this.state = State.Q1;
						this.band[this.bandPosition] = Alphabet.X;
						break;
				}
				break;
			case Q1:
				break;
			case Q2:
				break;
			case Q3:
				break;
			case Q4:
				break;
			case Q5:
				break;
			case Q6:
				break;
			case Q7:
				break;
			case Q8:
				break;
			case Q9:
				break;
			case Q10:
				break;
			case Q11:
				break;
			case Q12:
				break;
			case Q13:
				break;
			case Q15:
				break;
		}
	}
}
