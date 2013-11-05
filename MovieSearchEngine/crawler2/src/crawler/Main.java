package crawler;


public class Main {
 
    public static void main(String[] args)  {
        
    	try
    	{
	        Crawler c = new Crawler();
	      //seed rottentomatos website
	        c.CrawlRT("http://www.rottentomatoes.com/top/bestofrt/?category=16");
        }
        catch(java.io.IOException e){
        	e.printStackTrace();
        }
    }

  }
