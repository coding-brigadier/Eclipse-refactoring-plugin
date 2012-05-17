package object_in;

public class Person {
	private int age;
	private String name;
	private double height;
	private double weight;
	public int foo(){ return 0; }
	public Person(int age, String name){
		this.age = age;
		this.name = name;
		foo();
	}
	public Person(double height, double weight){
		this.height = height;
		this.weight = weight;
	}
}
