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
	Calculation[] result = nearestPoints(x, y, k);

	int c[] = new int[classes + 2];
	int old_class = classes + 1;
	for (int i = 0; i < Math.min(k, result.length); i++) {
	    c[result[i].label]++;

	    if (c[result[i].label] > c[old_class])
		old_class = result[i].label;
	}
	return old_class;
    }

    private Calculation[] calculateDistance(double x, double y) {
	Calculation[] r = new Calculation[database.size()];

	int i = 0;
	for (double[] p : database) {
	    final double delta_x = p[0] - x;
	    final double delta_y = p[1] - y;
	    final double distance = Math.sqrt(delta_x * delta_x + delta_y
		    * delta_y);
	    final int label = (int) p[2];

	    r[i++] = new Calculation(p[0], p[1], distance, label);
	}
	return r;
    }

    public Calculation[] nearestPoints(double x, double y, int k) {
	Calculation[] result = calculateDistance(x, y);
	Arrays.sort(result, new Comparator<Calculation>() {
	    @Override
	    public int compare(Calculation o1, Calculation o2) {
		return Double.compare(o1.distance, o2.distance);
	    }
	});
	return Arrays.copyOf(result, k);
    }
}

class Calculation {
    public final double x, y, distance;
    public final int label;

    public Calculation(double p, double q, double d, int l) {
	x = p;
	y = q;
	distance = d;
	label = l;
    }

    @Override
    public String toString() {
	return "[x=" + x + ", y=" + y + ", distance=" + distance + ", label="
		+ label + "]";
    }
}
