package object_in;

public class Person {
	private int calc;
	private int age;
	private String name;
	private double height;
	private double weight;
	public Person(int age, String name){
		this.age = age;
		this.name = name;
		weight = 0;
		height = 0;
		calc = weight * height;
	}
	public Person(double height, double weight){
		this.height = height;
		this.weight = weight;
		calc = this.weight + this.height;
	}
}
