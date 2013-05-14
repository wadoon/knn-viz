package weigl.knn;

public class Tripel<T extends Comparable<T>, V extends Comparable<V>, W extends Comparable<W>>
		extends Tupel<T, V> {
	protected final W w;

	public Tripel(T t, V v, W w) {
		super(t, v);
		this.w = w;
	}

	public Tripel(Tupel<T, V> tuple) {
		super(tuple);
		w = null;
	}

	public Tripel(Tripel<T, V, W> tuple3) {
		super(tuple3.t, tuple3.v);
		w = tuple3.w;
	}
	
	public static <T extends Comparable<T>, V extends Comparable<V>, W extends Comparable<W>>
			Tripel<T, V, W> create(T t, V v, W w) {
		return new Tripel<T, V, W>(t, v, w);
	}

	public W get3() {
		return w;
	}
}
