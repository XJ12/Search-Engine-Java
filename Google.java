package org.webcrawling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Google {
      private static String URL = "https://www.google.com/search?q=[QUERY]&hl=en&start=[START]&btnG=Google+Search&gbv=1";
      private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
              + "|png|tiff?|mid|mp2|mp3|mp4|ppt|pdf|java|pptx"
              + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
              + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

	  public static List<String> getResults(String query, int count) {
		  String encodedQuery = getQueryString(query);
		  String searchUrl = URL.replace("[QUERY]", encodedQuery);
		  ArrayList<String> urlResults = new ArrayList<String>();
			try {
			        int start = 0;
			        while (urlResults.size() < count) {
			            String currentPageSearchUrl = searchUrl.replace("[START]", start + "");
			            Document pageDoc = Jsoup.connect(currentPageSearchUrl).userAgent("Mozilla/6.0 (Windows NT 6.2; WOW64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1").get();
			

			            Elements searchResults = pageDoc.select("li[class=g] a");
			            for (Element result : searchResults) {
			                    if (result.text().equals("Cached") || result.text().equals("Similar")) // don't include cached or related links
			                            continue;
			                    String href = result.attr("href");
			                    String extractedUrl = extractUrl(href);

			                    if (FILTERS.matcher(extractedUrl).matches())
			                            continue;

			                    if (extractedUrl.contains("https"))
			                            continue;
			                    
			                    urlResults.add(extractedUrl);
			                    if (urlResults.size() >= count)
			                            break;
			            }
			            
			            if (urlResults.size() < count) {
			                    start += 10;
			                    try {
			                            Thread.sleep(1000); // wait a second to be polite to google     
			                        }
			                        catch (InterruptedException  e) {                                               
			                        }
			                }                                       
			        }               
			}
	                catch (IOException e) {
	                        e.printStackTrace();
	                }
	                
	                // Cache results for future calls
	                //addResultsToCache(query, urlResults.toArray(new String[urlResults.size()]));

	                return urlResults;
	        }

	        private static String extractUrl(String googleUrl) {
	                // Google search result links are not the direct links, have to get it from the "q" parameter
	                int start = googleUrl.indexOf("q=");
	                if (start > -1) {
	                        start += "q=".length();

	                        int end = googleUrl.indexOf("&", start);
	                        if (end > start) {
	                                try {
	                                        return URLDecoder.decode(googleUrl.substring(start, end), "UTF-8");
	                                }
	                                catch (UnsupportedEncodingException e) {
	                                        e.printStackTrace();
	                                }
	                        }
	                }

	                return "";
	        }

	        private static String getQueryString(String query) {
	                try {
	                        // Limit search to ics.uci.edu
	                        return URLEncoder.encode("site:ics.uci.edu " + query, "UTF-8");
	                }
	                catch (UnsupportedEncodingException e) {
	                        return "";
	                }
	        }
	        public static void main(String args[]) throws IOException
	        {
	          FileOutputStream out1=new FileOutputStream("google_0.txt");
	    	  PrintStream p1=new PrintStream(out1); 
	      	  List<String>result=getResults("crista lopes",10);
	      	  p1.println("crista lopes");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_1.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("machine learning",10);
	      	  p1.println("machine learning");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_2.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("security",10);
	      	  p1.println("security");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_3.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("mondego",10);
	      	  p1.println("mondego");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_4.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("graduate student",10);
	      	  p1.println("graduate student");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_5.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("hadoop",10);
	      	  p1.println("hadoop");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_6.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("student affairs",10);
	      	  p1.println("student affairs");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_7.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("information retrieval",10);
	      	  p1.println("information retrieval");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_8.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("computer games",10);
	      	  p1.println("computer games");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_9.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("graduate courses",10);
	      	  p1.println("graduate courses");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	      	  
	      	  out1=new FileOutputStream("google_10.txt");
	    	  p1=new PrintStream(out1); 
	      	  result=getResults("apply graduate courses",10);
	      	  p1.println("apply graduate courses");
	      	  for(int i=0;i<result.size();i++)
	      		  p1.println(result.get(i));
	      	  out1.close();
	        }
}
