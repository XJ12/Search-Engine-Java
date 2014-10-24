package org.webcrawling;
import ir.assignments.one.a.Utilities;
import ir.assignments.one.b.WordFrequencyCounter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.parser.*;
import edu.uci.ics.crawler4j.url.WebURL;
public class MyCrawler extends WebCrawler {

	public static CrawlStat crawl=new CrawlStat();
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");
   /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            return !FILTERS.matcher(href).matches() 
            		&&url.getDomain().toLowerCase().contains("uci.edu")
            		&&url.getSubDomain().toLowerCase().contains(".ics")
            		&&!href.contains("archive.ics.uci.edu/ml/datasets.html")
            		&&!href.contains("calendar.ics.uci.edu")
            		&&!href.contains("djp3-pc2.ics")
            		&&!href.contains("drzaius.ics.uci.edu")
            		&&!href.contains("mine1.ics.uci.edu")
            		&&!href.contains("cradl.ics.uci.edu/index.php/nggallery");
    }
    
    @Override
	public Object getMyLocalData() {
		return crawl;
	}
    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
            System.out.println("URL: " + url);
            String domain = page.getWebURL().getDomain();
    		String path = page.getWebURL().getPath();
    		String subDomain = page.getWebURL().getSubDomain();
    		String parentUrl = page.getWebURL().getParentUrl();
    		String anchor = page.getWebURL().getAnchor();
    		crawl.addanchor(url,anchor);
    		if(crawl.fre_url.containsKey(url))
    				return;
    		//System.out.println(subDomain);
            if (page.getParseData() instanceof HtmlParseData) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    
                    String html = htmlParseData.getHtml();
                    String title= htmlParseData.getTitle();
                    List<WebURL> outgoing= htmlParseData.getOutgoingUrls();
            		try {
            			
            			for(int z=0;z<outgoing.size();z++)
            			{
            				text=((WebURL)outgoing.get(z)).getURL()+"\n"+text;
            			}
            			text=outgoing.size()+"\n"+text;
            			text=title+"\n"+text;
            			//text=anchor+"\n"+text;
						crawl.file_out(text,url);
	                    crawl.addFrequencies(Utilities.tokenizeString(text));
	                    crawl.addtwoFrequencies(Utilities.tokenizeString(text));
                		crawl.adddomain(domain);
                		crawl.addUrl(url);
                		crawl.addSubdomain(subDomain);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    if(crawl.longest_text<(text.split("[^a-z0-9']+")).length)
                    {
                    	crawl.longest_text=(text.split("[^a-z0-9']+")).length;
                    	crawl.longest_url=url;
                    }
        }
    }
}
