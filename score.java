package org.webcrawling;

import ir.assignments.one.a.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class score {
	//48429
	public static int TOTAL=85036;
	public static int patch=6000;
	public static void main(String args[]) throws IOException
	{
		Map<Integer, Integer> score=new HashMap<Integer, Integer>();
		Map<Integer, Integer> score_two=new HashMap<Integer, Integer>();
		FileOutputStream out1=new FileOutputStream("index_score.txt");
		PrintStream p1=new PrintStream(out1); 
		FileReader in =new FileReader("index.txt");
		BufferedReader buffer=new BufferedReader(in);
		String line;
		line=buffer.readLine();
		while(line!=null)
		{
			String []arr=line.split(" ");
			String result=arr[0]+" "+arr[1]+" ";
			System.out.println(result);
			int max=Integer.parseInt(arr[1]);
			for(int i=1;i<=max;i++)
			{
				if(score.containsKey(Integer.parseInt(arr[i*2])))
				{
					score.put(Integer.parseInt(arr[i*2]),score.get(Integer.parseInt(arr[i*2]))+1);
				}
				else
					score.put(Integer.parseInt(arr[i*2]),1);
			}
			List<Map.Entry<Integer, Integer>> info=new ArrayList<Map.Entry<Integer,Integer>>(score.entrySet());
			
			for(int z=0;z<info.size();z++)
			{
				double s=0;
				if(info.size()!=0)
				{
					s=(1+Math.log10(info.get(z).getValue()))*(Math.log10(TOTAL/info.size()));
				}
				int sc=Integer.parseInt(new java.text.DecimalFormat("0").format(s));
				result+=info.get(z).getKey()+" "+sc+" ";
			}
			p1.println(result);
			score.clear();
			line=buffer.readLine();
		}
		out1.close();
		in.close();
		
		
		out1=new FileOutputStream("index_two_score.txt");
		p1=new PrintStream(out1); 
		in =new FileReader("index_two.txt");
		buffer=new BufferedReader(in);
		line=buffer.readLine();
		while(line!=null)
		{
			String []arr=line.split(" ");
			String result=arr[0]+" "+arr[1]+" ";
			System.out.println(result);
			int max=Integer.parseInt(arr[1]);
			for(int i=1;i<=max;i++)
			{
				if(score.containsKey(Integer.parseInt(arr[i*2])))
				{
					score.put(Integer.parseInt(arr[i*2]),score.get(Integer.parseInt(arr[i*2]))+1);
				}
				else
					score.put(Integer.parseInt(arr[i*2]),1);
			}
			List<Map.Entry<Integer, Integer>> info=new ArrayList<Map.Entry<Integer,Integer>>(score.entrySet());
			
			for(int z=0;z<info.size();z++)
			{
				double s=0;
				if(info.size()!=0)
				{
					s=(1+Math.log10(info.get(z).getValue()))*(Math.log10(TOTAL/info.size()));
				}
				int sc=Integer.parseInt(new java.text.DecimalFormat("0").format(s));
				result+=info.get(z).getKey()+" "+sc+" ";
			}
			p1.println(result);
			score.clear();
			line=buffer.readLine();
		}
		out1.close();
		in.close();
	}
}
