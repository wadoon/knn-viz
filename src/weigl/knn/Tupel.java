package weigl.knn;

public class Tupel<T extends Comparable<T>, V extends Comparable<V>> implements
	Comparable<Tupel<T, V>> {
    protected T t;
    protected V v;

    public Tupel(T t, V v) {
	set1(t);
	set2(v);
    }

    public Tupel(Tupel<T, V> tuple) {
	this(tuple.t, tuple.v);
    }

    public static <T extends Comparable<T>, V extends Comparable<V>> Tupel<T, V> create(
	    T t, V v) {
	return new Tupel<T, V>(t, v);
    }

    public T get1() {
	return t;
    }

    void set1(T t) {
	this.t = t;
    }

    public V get2() {
	return v;
    }

    void set2(V v) {
	this.v = v;
    }

    @Override
    public String toString() {
	return "(" + get1() + "," + get2() + ")";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((t == null) ? 0 : t.hashCode());
	result = prime * result + ((v == null) ? 0 : v.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	@SuppressWarnings("rawtypes")
	Tupel other = (Tupel) obj;
	if (t == null) {
	    if (other.t != null)
		return false;
	} else if (!t.equals(other.t))
	    return false;
	if (v == null) {
	    if (other.v != null)
		return false;
	} else if (!v.equals(other.v))
	    return false;
	return true;
    }

    @Override
    public int compareTo(Tupel<T, V> o) {
	int c = get1().compareTo(o.get1());
	if (c != 0)
	    return c;
	return get2().compareTo(o.get2());
    }
}
