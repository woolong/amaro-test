package br.com.jar.amaro_test.to;

public class Tuple<First, Second> {

	private First first;
	private Second second;

	public Tuple(First _1st, Second _2nd) {
		this.first = _1st;
		this.second = _2nd;
	}

	public First getFirst() {
		return first;
	}

	public void setFirst(First first) {
		this.first = first;
	}

	public Second getSecond() {
		return second;
	}

	public void setSecond(Second second) {
		this.second = second;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Tuple [first=");
		builder.append(first);
		builder.append(", second=");
		builder.append(second);
		builder.append("]");
		return builder.toString();
	}

}
