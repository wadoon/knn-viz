package weigl.knn;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class KNNClassificator {
    private List<double[]> database;
    private int classes;

    public KNNClassificator(List<double[]> data) {
	database = data;
	classes = 0;
	for (double[] ds : data) {
	    classes = Math.max(classes, (int) ds[2]);
	}
    }

    public double classify(double x, double y, int k) {
	Tupel<Double, Integer>[] result = calculateDistance(x, y);

	Arrays.sort(result, new Comparator<Tupel<Double, Integer>>() {
	    @Override
	    public int compare(Tupel<Double, Integer> o1,
		    Tupel<Double, Integer> o2) {
		return o1.get1().compareTo(o2.get1());
	    }

	});

	int c[] = new int[classes + 2];
	int old_class = classes + 1;
	for (int i = 0; i < Math.min(k, result.length); i++) {
	    c[result[i].get2()]++;

	    if (c[result[i].get2()] > c[old_class])
		old_class = result[i].get2();
	}
	return old_class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Tupel<Double, Integer>[] calculateDistance(double x, double y) {
	Tupel[] r = new Tupel[database.size()];

	int i = 0;
	for (double[] p : database) {
	    double delta_x = p[0] - x;
	    double delta_y = p[1] - y;
	    Tupel<Double, Integer> t = new Tupel<>(Math.sqrt(delta_x * delta_x
		    + delta_y * delta_y), (int) p[2]);
	    r[i++] = t;
	}
	return r;
    }
}
