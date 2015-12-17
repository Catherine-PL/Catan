package database;

import java.util.Random;

public class Dice {
	private int firstValue;
	private int secondValue;
	
	private static class InstanceHolder{
		private static final Dice instance = new Dice();
	}
	protected Dice(){}
	public static Dice getInstance(){
		return InstanceHolder.instance;
		
	}

	Random generator = new Random();
	//int result=0;
	/*int throDice(){
		//rzut dwoma koœæmi
		return generator.nextInt(11)+1;
		//result=generator.nextInt(5)+1+generator.nextInt(5)+1;
		//return result;
		//					0-11+1=1-12
		//return generator.nextInt(11)+1; alternatywa krótsza
	}*/
	
	public void throDice(){
		firstValue = generator.nextInt(5)+1;
		secondValue = generator.nextInt(5)+1;
	}
	
	public int getFirst(){
		return firstValue;
	}
	
	public int getSecond(){
		return secondValue;
	}
}
