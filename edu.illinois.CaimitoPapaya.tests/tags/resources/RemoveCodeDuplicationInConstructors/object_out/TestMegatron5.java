package object_in;

public class Person {
	private int calc;
	private int age;
	private String name;
	private double height;
	private double weight;
	public Person(int age, String name){
		this(age,name,0,0);
		calc = weight * height;
	}
	public Person(double height, double weight){
	this(0,0,height,weight);
	calc = this.weight + this.height;
	}
	public Person(int age, String name, double height, double weight){
		this.age = age;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.calc = 0;
	}
}
