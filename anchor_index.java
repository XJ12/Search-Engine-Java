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

public class anchor_index {
	//48429
	public static int TOTAL=84268;
	public static int patch=6000;
	public static void main(String args[]) throws IOException
	{
		Map<String, Integer> url_anchor=new HashMap<String, Integer>();
		Map<String, Integer> word_frequencies=new HashMap<String, Integer>();
		Map<String, Integer> two_word_frequencies=new HashMap<String, Integer>();
		Map<String, Integer> word_f=new HashMap<String, Integer>();
		File file = new File("stop.txt");
		List<String> words = Utilities.tokenizeFile(file);
		Map<String, Integer> stop_frequencies=new HashMap<String, Integer>();
		for(int i=0;i<words.size();i++)
		{
			if(stop_frequencies.containsKey(words.get(i)))
			{
				stop_frequencies.put(words.get(i),stop_frequencies.get(words.get(i))+1);
			}
			else
			{
				stop_frequencies.put(words.get(i),1);
			}
		}
		int num=0;
		int size=0;
		String url="";
		FileReader in = null;
		BufferedReader buffer;
		
		FileOutputStream urlt;
		PrintStream urlz;
		for(int i=0;i<=TOTAL;i++)
		{
			try {
				in=new FileReader("data_a/"+i+".txt");
				buffer=new BufferedReader(in);
						
				String result=new String();
				String line=buffer.readLine();
				while(line!=null)
				{
					result+=line+" ";
					line=buffer.readLine();
				}
				result=result.toLowerCase();
				in.close();
				String [] split=result.split("[^a-z]+");
				words=Utilities.tokenizeString(result);
				for(int m=0;m<words.size();m++)
				{
					if(!stop_frequencies.containsKey(words.get(m)))
					{
						if(word_frequencies.containsKey(words.get(m)))
						{
							word_frequencies.put(words.get(m),word_frequencies.get(words.get(m))+1);
						}
						else
						{
							word_frequencies.put(words.get(m),1);
						}
					}
				}
				for(int z=0;z<words.size()-1;z++)
				{
					if(!stop_frequencies.containsKey(words.get(z))&&!stop_frequencies.containsKey(words.get(z+1)))
					{
						String gram=words.get(z)+" "+words.get(z+1);
						if(two_word_frequencies.containsKey(gram))
						{
							two_word_frequencies.put(gram,two_word_frequencies.get(gram)+1);
						}
						else
						{
							two_word_frequencies.put(gram,1);
						}
					}
				}
				} catch (FileNotFoundException e) {
				}
		}
		FileOutputStream out=new FileOutputStream("index_word_anchor.txt");
		PrintStream p=new PrintStream(out);    
		List<Map.Entry<String, Integer>> info=new ArrayList<Map.Entry<String,Integer>>(word_frequencies.entrySet());
		Collections.sort(info,new Comparator<Map.Entry<String,Integer>>()
		{
			public int compare(Map.Entry<String, Integer> o1,Map.Entry<String,Integer> o2)
			{
					return(o1.getKey().toString().compareTo(o2.getKey()));
			}
		});
		String[] arr=new String[info.size()];
		
		for(int i=0;i<info.size();i++)
		{
			word_f.put(info.get(i).getKey(), info.get(i).getValue());
			p.println(info.get(i).getKey()+" "+i);
			word_f.put(info.get(i).getKey(),i);
		}
		out.close();
		for(int i=0;i<info.size();i++)
		{
			arr[i]=""+i+" "+info.get(i).getValue()+" ";
		}
		for(int x=0;x<=TOTAL/patch;x++)
		{
			FileOutputStream out1=new FileOutputStream("index_w_anchor"+x+".txt");
			PrintStream p1=new PrintStream(out1); 
			int big=(x+1)*patch-1;
			if(big>TOTAL)
				big=TOTAL;
			for(int i=x*patch;i<=big;i++)
			{
				System.out.println(i);

				try {
					in=new FileReader("data_a/"+i+".txt");
					buffer=new BufferedReader(in);
							
					String result=new String();
					String line=buffer.readLine();
					while(line!=null)
					{
						result+=line+" ";
						line=buffer.readLine();
					}
				in.close();
				result=result.toLowerCase();
				String [] split=result.split("[^a-z]+");
				for(int y=0;y<split.length;y++)
				{
					if(word_f.containsKey(split[y]))
						arr[word_f.get(split[y])]+=i+" "+y+" ";
				}

				} catch (FileNotFoundException e){
			            //e.printStackTrace();
				}
			}
			for(int i=0;i<info.size();i++)
			{
				p1.println(arr[i]);
			}			
			for(int i=0;i<info.size();i++)
			{
				arr[i]="";
			}
			out1.close();
		}

		FileReader []in_array=new FileReader[TOTAL/patch+1];
		BufferedReader[] p_array=new BufferedReader[TOTAL/patch+1];
		FileOutputStream out1=new FileOutputStream("index_anchor.txt");
		PrintStream p1=new PrintStream(out1); 
		for(int x=0;x<=TOTAL/patch;x++)
		{
			in_array[x]=new FileReader("index_w_anchor"+x+".txt");
			p_array[x]=new BufferedReader(in_array[x]); 
		}
		for(int i=0;i<info.size();i++)
		{
			String result="";
			for(int x=0;x<=TOTAL/patch;x++)
			{
				String line=p_array[x].readLine();
				if(line!=null&&!line.equals(""))
					result+=line;
			}
			p1.println(result);
		}
		System.out.print("number "+info.size());
		for(int x=0;x<=TOTAL/patch;x++)
		{
			in_array[x].close();
		}
		out1.close();
		
		
		//-----------------
		
		
		out=new FileOutputStream("index_two_word_anchor.txt");
		p=new PrintStream(out);    
		info=new ArrayList<Map.Entry<String,Integer>>(two_word_frequencies.entrySet());
		Collections.sort(info,new Comparator<Map.Entry<String,Integer>>()
		{
			public int compare(Map.Entry<String, Integer> o1,Map.Entry<String,Integer> o2)
			{
					return(o1.getKey().toString().compareTo(o2.getKey()));
			}
		});
		arr=new String[info.size()];
		word_f.clear();
		for(int i=0;i<info.size();i++)
		{
			word_f.put(info.get(i).getKey(), info.get(i).getValue());
			p.println(info.get(i).getKey()+" "+i);
			word_f.put(info.get(i).getKey(),i);
		}
		out.close();
		for(int i=0;i<info.size();i++)
		{
			arr[i]=""+i+" "+info.get(i).getValue()+" ";
		}
		for(int x=0;x<=TOTAL/patch;x++)
		{
			out1=new FileOutputStream("two_index_w_anchor"+x+".txt");
			p1=new PrintStream(out1); 
			int big=(x+1)*patch-1;
			if(big>TOTAL)
				big=TOTAL;
			for(int i=x*patch;i<=big;i++)
			{
				System.out.println(i);

				try {
				in=new FileReader("data_a/"+i+".txt");
				buffer=new BufferedReader(in);
				String line=buffer.readLine();
				String result=new String();
				while(line!=null)
				{
					result+=line+" ";
					line=buffer.readLine();
				}
				in.close();
				result=result.toLowerCase();
				String [] split=result.split("[^a-z]+");
				for(int y=0;y<split.length-1;y++)
				{
					String gram=split[y]+" "+split[y+1];
					if(word_f.containsKey(gram))
						arr[word_f.get(gram)]+=i+" "+y+" ";
				}

				} catch (FileNotFoundException e){
			            //e.printStackTrace();
				}
			}
			for(int i=0;i<info.size();i++)
			{
				p1.println(arr[i]);
			}			
			for(int i=0;i<info.size();i++)
			{
				arr[i]="";
			}
			out1.close();
		}

		in_array=new FileReader[TOTAL/patch+1];
		p_array=new BufferedReader[TOTAL/patch+1];
		out1=new FileOutputStream("index_two_anchor.txt");
		p1=new PrintStream(out1); 
		for(int x=0;x<=TOTAL/patch;x++)
		{
			in_array[x]=new FileReader("two_index_w_anchor"+x+".txt");
			p_array[x]=new BufferedReader(in_array[x]); 
		}
		for(int i=0;i<info.size();i++)
		{
			String result="";
			for(int x=0;x<=TOTAL/patch;x++)
			{
				String line=p_array[x].readLine();
				if(line!=null&&!line.equals(""))
					result+=line;
			}
			p1.println(result);
		}
		System.out.print("number "+info.size());
		for(int x=0;x<=TOTAL/patch;x++)
		{
			in_array[x].close();
		}
		out1.close();
	}	
}
