package object_in;

public class Person {
	private int calc;
	private int age;
	private String name;
	private double height;
	private double weight;
	public Person(int age, String name, int calc){
		this(age,name,0,0,calc+age);
	}
	public Person(double height, double weight){
	this(0,0,height,weight,0);
	}
	public Person(int age, String name, int calc, double height, double weight){
		this.age = age;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.calc = calc;
	}
}
