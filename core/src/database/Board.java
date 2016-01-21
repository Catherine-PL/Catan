package database;

import database.Building;
import database.Node.portType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Board implements Serializable{
	public static Board instance;
	public int thiefPosition=0;
	private Player whoArmy=null;
	private Player whoRoad=null;
	private Node[] nodes = new Node[54];
	//private int[][] adjencyMatrix=new int[54][54];
	//private int[][] tilesToNodes=new int[19[54];
	private int[] letterToNumber=new int [] {5,2,6,3,8,10,9,12,11,4,8,10,9,4,5,6,3,11};//zamiast tego ca³ego ABCDEF
	public int numbersLayout=0;
	
	
	public  ArrayList <Road> boardRoads=new ArrayList<Road> ();
	private HashMap <DevelopType,Integer> BdevelopCards= new HashMap <DevelopType,Integer>();
	
	private int[][] adjencyMatrix=new int[][] {{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0},
		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0}};
		
	private int[][] tilesToNodes={{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}};
	
	private Tile tiles[]={new Tile.Builder("Forest").build(),new Tile.Builder("Forest").build(),new Tile.Builder("Forest").build(),
			new Tile.Builder("Forest").build(),new Tile.Builder("Desert").build(),new Tile.Builder("Mountains").build(),
			new Tile.Builder("Mountains").build(),new Tile.Builder("Mountains").build(),new Tile.Builder("Fields").build(),
			new Tile.Builder("Fields").build(),new Tile.Builder("Fields").build(),new Tile.Builder("Fields").build(),new Tile.Builder("Pasture").build(),
			new Tile.Builder("Pasture").build(),new Tile.Builder("Pasture").build(),new Tile.Builder("Pasture").build(),
			new Tile.Builder("Hills").build(),new Tile.Builder("Hills").build(),new Tile.Builder("Hills").build()};
	
	

	public Board(){
		
		instance=new Board();
		BdevelopCards.put(DevelopType.MONOPOL, 2);
		BdevelopCards.put(DevelopType.POINT, 5);
		BdevelopCards.put(DevelopType.ROAD, 2);
		BdevelopCards.put(DevelopType.SOLDIER, 14);
		BdevelopCards.put(DevelopType.YEAR, 2);
		
		//generowanie planszy
		//przemieszanie kafli l¹du
		Collections.shuffle(Arrays.asList(tiles));
		
	
		for(int i=0;i<19;i++){		
			tiles[i].setNumber(i);
			//przypisanie z³odzieja do pustyni 
			if(tiles[i].getType()=="Desert"){
				tiles[i].changeThiefState();				
				thiefPosition=i;				
			}
			
		}
			
		//numer koœci do tile
		int numberT=0;//tile startu
		int k=0;
		while(k<18){
				
				if(tiles[numberT].getType()=="Desert"){
						tiles[numberT].setDiceNumber(0);					
					}
				else{
					tiles[numberT].setDiceNumber(letterToNumber[k]);
					k++;
				}
				switch (numberT){
				case 0: numberT=(tiles[3].getDiceNumber()<0)? 3:4;break;
				case 1: numberT=(tiles[0].getDiceNumber()<0)? 0:4;break;
				case 2: numberT=(tiles[1].getDiceNumber()<0)? 1:5;break;
				case 3: numberT=(tiles[7].getDiceNumber()<0)? 7:8;break;
				case 4: numberT=(tiles[8].getDiceNumber()<0)? 8:9;break;
				case 5: numberT=(tiles[4].getDiceNumber()<0)? 4:9;break;
				case 6: numberT=(tiles[2].getDiceNumber()<0)? 2:5;break;
				case 7: numberT=(tiles[12].getDiceNumber()<0)? 12:8;break;
				case 8: numberT=(tiles[13].getDiceNumber()<0)? 13:9;break;
				case 9: numberT=-2;break;//stan akceptuj¹cy
				case 10: numberT=(tiles[5].getDiceNumber()<0)? 5:9;break;
				case 11: numberT=(tiles[6].getDiceNumber()<0)? 6:10;break;
				case 12: numberT=(tiles[16].getDiceNumber()<0)? 16:13;break;
				case 13: numberT=(tiles[14].getDiceNumber()<0)? 14:9;break;
				case 14: numberT=(tiles[10].getDiceNumber()<0)? 10:9;break;
				case 15: numberT=(tiles[11].getDiceNumber()<0)? 11:10;break;
				case 16: numberT=(tiles[17].getDiceNumber()<0)? 17:13;break;
				case 17: numberT=(tiles[18].getDiceNumber()<0)? 18:14;break;
				case 18: numberT=(tiles[15].getDiceNumber()<0)? 15:14;break;
				default: numberT=-3;break;//-3 error
				
			}
		}
		//indeksowanie wierzcho³ków
		for(int i=0;i<54;i++){
			nodes[i] = new Node(i);		
		}
		
		
		//loadMatrix();
		//loadTileToNodeMatrix();
		setNeighbours();
		setRoadsy();
		setNoRoads();

		//wgranie zasobów s¹siaduj¹cych do tile
		for (int i = 0; i < nodes.length; i++) {
			for(int j=0;j<19;j++){
				if(tilesToNodes[i][j]==1){
					nodes[i].addNearResources(tiles[j]);
					tiles[j].addTileNodes(nodes[i]);
				}
			}
		}
		
		//wgranie dodatkowych portów
		// na posdstawie catan rules 5th 
		nodes[0].setPort(portType.THREE);
		nodes[3].setPort(portType.THREE);
		nodes[26].setPort(portType.THREE);
		nodes[32].setPort(portType.THREE);
		nodes[52].setPort(portType.THREE);
		nodes[49].setPort(portType.THREE);
		nodes[47].setPort(portType.THREE);
		nodes[51].setPort(portType.THREE);		
		nodes[10].setPort(portType.ORE);
		nodes[15].setPort(portType.ORE);
		nodes[11].setPort(portType.WOOD);
		nodes[16].setPort(portType.WOOD);		
		nodes[33].setPort(portType.CLAY);
		nodes[39].setPort(portType.CLAY);		
		nodes[1].setPort(portType.GRAIN);
		nodes[5].setPort(portType.GRAIN);
		nodes[42].setPort(portType.SHEEP);
		nodes[46].setPort(portType.SHEEP);

	}
	
	public static Board getInstance() {
		return instance;
	}
	
	public static void setInstance(Board board) {
		instance=board;
	}
	
	
	
	public void setNeighbours(){
		for(int i=0;i<54;i++){
    		for(int j=0;j<54;j++){
    			if(adjencyMatrix[i][j] == 1){
    				if(!nodes[i].getNeighbours().contains(nodes[j])) nodes[i].addNeighbour(nodes[j]);
    				if(!nodes[j].getNeighbours().contains(nodes[i]))nodes[j].addNeighbour(nodes[i]);
    			}
    		}
		}	
	}
	public void setNoRoads(){
		for(int i=0;i<54;i++){
			nodes[i].setNoRoads(nodes[i].getNeighbours());		
		}
	}
	public void setRoadsy(){	
		//set RoadRoad
		int k=0;
		for(int i=0;i<54;i++){
			for(Node nod:nodes[i].getNeighbours()){
				
				if(!nodes[i].hasRoadRoadTo(nod)){
					
				Road r=new Road(nodes[i],nod);
				
				boardRoads.add(r);
				
				r.setID(k);
				k++;
				nod.addRoadRoad(r);
				nodes[i].addRoadRoad(r);
				}
				
			}
		}
	}
	public void loadMatrix(){
		 //wczytanie macierzy sasiedztwa
			Scanner scanner;
			try {
				scanner = new Scanner(new File("src\\database\\adjencymatrix.txt"));
				for(int i=0;i<54;i++){
		    		for(int j=0;j<54;j++){
		    			if(scanner.hasNextInt())
		    			    adjencyMatrix[i][j] = scanner.nextInt();
		    		}
			    }
				scanner.close();
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	public void loadTileToNodeMatrix(){
		 //wczytanie macierzy sasiedztwa
			Scanner scanner;
			try {
				scanner = new Scanner(new File("src\\database\\TileToNode.txt"));
				for(int i=0;i<19;i++){
		    		for(int j=0;j<54;j++){
		    			if(scanner.hasNextInt())
		    				tilesToNodes[i][j] = scanner.nextInt();
		    		}
			    }
				scanner.close();
			}
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	public Tile getTile(int index){
		return tiles[index];
	}
	public Node[] getNodes() {
		return nodes;
	}
	
	public Node getNode(int i){
		return nodes[i];
	}
	
	public int getLetterToNumber(int i){
		return this.letterToNumber[i];
	}
	
	/*
	 * 0-uda³o siê
	 * 1-"Nie mo¿esz przenieœæ z³odzieja w to samo miejsce";
	 */
	int moveThief(Player p,int intTo) {
		
		//czy to dobry sposób na pobieranie Tila który ma thief'a
		Board board = Board.getInstance();
		Tile from=board.getTile(board.thiefPosition);
		Tile to=board.getTile(intTo);
		if(from.equals(to)){
			return 1;
		}
		else
		{
		from.changeThiefState();
		to.changeThiefState();
		board.thiefPosition=to.getNumber();
		steal(p,to);
		
		return 0;
		}		
	}
	/*
	 * 0 -uda³o siê
	 * 1- gracz nie ma surowców
	 * 2- nie ma nikogo do okradzenia
	 * 
	 */
	int steal(Player player,Tile hex){
		ArrayList <Player> mayRob=hex.getTileplayer();
		int count,number;
		String [] resources={"clay","grain","ore","sheep","wood"};
		if(!mayRob.isEmpty()){
			Random generator=new Random();
			
			mayRob.remove(player);//gracz nie mo¿e okraœæ sam siebie
			
			//losowanie gracza którego okradnê
			Player toRob=mayRob.get(generator.nextInt(mayRob.size()));	
			//brak sorowców do ukradzenia
			if(toRob.getResources(resources[0])==0 && toRob.getResources(resources[1])==0 && toRob.getResources(resources[2])==0 && toRob.getResources(resources[3])==0 && toRob.getResources(resources[4])==0 )
				return 1;
			
			do{				
				number=generator.nextInt(4);
				count=toRob.getResources(resources[number]);				
			}
			while(count==0);
			toRob.changeResources(resources[number], -1);
			player.changeResources(resources[number], 1);
		
			return 0;
		}
		return 2;
	}
	
	
	public void resourceDistribution(int dice){
		String resource="";
		for(Tile tile:Board.getInstance().tiles){
			if(tile.getDiceNumber()==dice){
				
				if(tile.getType().equals("Forest"))
					resource="wood";
				if(tile.getType().equals("Fields"))
					resource="grain";
				if(tile.getType().equals("Mountains"))
					resource="ore";
				if(tile.getType().equals("Pasture"))
					resource="sheep";
				if(tile.getType().equals("Hills"))
					resource="clay";
				
				for(Node node: tile.getTileNodes()){	
					if(node.getPlayerNumber()>0)
					//	if(node.getPlayer().equals(obj)) //sprawdzanie czy RealPlayer
					node.getPlayer().changeResources(resource,node.getBuilding());
				}
				
				}
			
			}
		
	}
	
	//
	public Player getWhoArmy() {
		return whoArmy;
	}


	public void setWhoArmy(Player whoArmy) {
		if(this.whoArmy==null)
		{
		this.whoArmy = whoArmy;
		whoArmy.setBiggestArmy(true);
		whoArmy.addPoints(2);		
		}
		else{
			this.whoArmy.setBiggestArmy(false);
			this.whoArmy.addPoints(-2);	
			this.whoArmy = whoArmy;
			whoArmy.setBiggestArmy(true);
			whoArmy.addPoints(2);
		}
	}


	public Player getWhoRoad() {
		return whoRoad;
	}


	public void setWhoRoad(Player whoRoad) {
		if(this.whoRoad==null)
		{
		this.whoRoad = whoRoad;
		whoRoad.setLongestRoad(true);
		whoRoad.addPoints(2);		
		}
		else{
			this.whoRoad.setLongestRoad(false);
			this.whoRoad.addPoints(-2);	
			this.whoRoad = whoRoad;
			whoRoad.setLongestRoad(true);
			whoRoad.addPoints(2);
		}
	}
	/*
	 * 0-uda³o siê
	 * 2-nie masz surowców
	 * 3-brak³o kart
	 */
	
	public int buyCard(Player player){
		Board board=Board.getInstance();
		ArrayList <DevelopType> types=new ArrayList<DevelopType>();
		Random generator=new Random();
		DevelopType type=null;
		
		types.add(DevelopType.MONOPOL);
		types.add(DevelopType.POINT);
		types.add(DevelopType.ROAD);
		types.add(DevelopType.SOLDIER);
		types.add(DevelopType.YEAR);

			boolean gotCard=false;
			while(!gotCard){
				if(types.size()>0){
					type=types.get(generator.nextInt(types.size()));
					if(board.BdevelopCards.get(type)>0){
						gotCard=true;
					}
					else
						types.remove(type);
				}
				else{
					return 3;
				}	
								
			}
			
			if(player.getResources("grain")>=1 && player.getResources("ore")>=1 && player.getResources("sheep")>=1){
				player.addCard(type);
				board.BdevelopCards.put(type, board.BdevelopCards.get(type)-1);
				player.changeResources("grain", -1);
				player.changeResources("ore", -1);
				player.changeResources("sheep", -1);
			}
			else
				return 2;
			
			return 0;
			
		}
	
	
	public static void main(String [ ] args) throws FileNotFoundException{
		Board board = Board.getInstance();
		
		Player p=new Player(3);
		System.out.println(p.getResources("clay"));
		System.out.println(p.getResources("grain"));
		System.out.println(p.getResources("sheep"));
		System.out.println(p.getResources("ore"));
		System.out.println(p.getResources("wood")+"------");

		System.out.println("node 0  "+board.getNode(0).getBuilding());
		for(Node nod:board.getNode(0).getNeighbours()){
			System.out.println("\t"+nod.getNodeNumber()+" "+nod.getBuilding());
		}
		System.out.println("node 3  "+board.getNode(3).getBuilding());
		for(Node nod:board.getNode(3).getNeighbours()){
			System.out.println("\t"+nod.getNodeNumber()+" "+nod.getBuilding());
		}
		System.out.println("node 4 "+board.getNode(4).getBuilding());
		for(Node nod:board.getNode(4).getNeighbours()){
			System.out.println("\t"+nod.getNodeNumber()+" "+nod.getBuilding());
		}
		
		Building.buildSettlement(p, board.getNode(0) );
		Building.buildSettlement(p, board.getNode(3) );
		Building.buildSettlement(p, board.getNode(4) );
		
		System.out.println("node 0  "+board.getNode(0).getBuilding());
		for(Node nod:board.getNode(0).getNeighbours()){
			System.out.println("\t"+nod.getNodeNumber()+" "+nod.getBuilding());
		}
		//System.out.println("node 3  "+board.getNode(3).getBuilding()+board.getNode(3).neighboursHasBuildins());// .twoSpaceRule3()
		for(Node nod:board.getNode(3).getNeighbours()){
			System.out.println("\t"+nod.getNodeNumber()+" "+nod.getBuilding());
		}
		System.out.println("node 4 "+board.getNode(4).getBuilding());
		for(Node nod:board.getNode(4).getNeighbours()){
			System.out.println("\t"+nod.getNodeNumber()+" "+nod.getBuilding());
		}
		
	/*
		//test, wypisanie s¹siadów
		for(int i=0;i<54;i++){
			System.out.print(i + " - ");
			if(!board.nodes[i].getNeighbours().isEmpty()){
				for (Node node : board.nodes[i].getNeighbours()) {
				        System.out.print(node.getNodeNumber() + " ");
				} 
			}
	        System.out.println();
  
		}
*/
	/*
		//testowanie Kasi
		
				Player p1=new Player(3);
				
				p1.changeResources("grain", 500);
				p1.changeResources("sheep",500);
				p1.changeResources("wood", 500);
				p1.changeResources("clay", 500);		

			    for(Node e: board.getNodes()){
					Building.buildSettlement(p1, e);
					System.out.print("\nNode number "+e.getNodeNumber()+"Roads");
					for(Road r:e.getRoads()){
						System.out.print(r.getID()+"  ");
					}

				}
			    System.out.print("\n"+board.boardRoads.get(0).getOwnerID()+"  "+board.boardRoads.get(0).getState()+" to2imp"+board.nodes[0].getRoads2Improve()+" to2imp"+board.nodes[0].getRoadsIdImprove());
				board.getNode(0).buildRoad(p1, 0);
				board.getNode(0).buildRoad(p1, 1);
			    System.out.print("\n"+board.boardRoads.get(0).getOwnerID()+"  "+board.boardRoads.get(0).getState()+" to2imp"+board.nodes[0].getRoads2Improve()+" to2imp"+board.nodes[0].getRoadsIdImprove());

		*/
	}
	


	


	
	
}
