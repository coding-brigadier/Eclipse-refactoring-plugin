package object_in;

public class Person {
	private int calc;
	private int age;
	private String name;
	private double height;
	private double weight;
	public Person(int age, String name, int calc){
		this.age = age;
		this.name = name;
		calc = calc + this.age;
	}
	public Person(double height, double weight){
		this.height = height;
		this.weight = weight;
		calc = height*weight;
	}
}
