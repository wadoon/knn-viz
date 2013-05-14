package weigl.knn;

import java.util.Random;

public class RandomGenerator {
    private Random random = new Random();
    private int max;
    private int min;
    private double stdY;
    private int muY;
    private double stdX;
    private int muX;

    public RandomGenerator(int min, int max) {
	this.min = min;
	this.max = max;
    }

    public void newRound() {
	muX = random.nextInt(max - min) + min;
	stdX = random.nextInt((int) Math.sqrt(max * 4)) + 3;

	muY = random.nextInt(max - min) + min;
	stdY = random.nextInt((int) Math.sqrt(max * 4)) + 3;
    }

    public int[] getNextFeature() {
	return new int[] { getValue(muX, stdX), getValue(muY, stdY) };
    }

    private int getValue(double mu, double std) {
	return (int) (random.nextGaussian() * std + mu);
    }
}
