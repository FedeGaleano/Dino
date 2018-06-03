package fede.utils;

public class Random {
	public int between(int min, int max) {
		return (int) Math.round(Math.random() * (max - min) + min);
	}
}
