package crawler;


import java.io.*;
import org.jsoup.*;
import org.w3c.dom.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

public class Crawler {

	public static ArrayList<String> ToCrawl = new ArrayList<String>();
	public static ArrayList<String> Crawl= new ArrayList<String>();
    public static int n=1;
    Properties prop = new Properties();
	String HTMLPage;
	public Crawler() throws IOException
	{
		//get the starting movie id
		prop.load(new FileInputStream("config.properties"));
		n = Integer.parseInt(prop.getProperty("Id"));
	}

	
	public void CrawlRT(String RTPage) throws IOException
	{
		ArrayList<String> t= new ArrayList<String>();
		String crawlData;
		String crawlData2;
		String crawlData3;
		
		
		FileReader freader = new FileReader("Crawl.txt");
		BufferedReader br = new BufferedReader(freader);
		FileReader freader2 = new FileReader("Tocrawl.txt");
		BufferedReader br2 = new BufferedReader(freader2);
		FileWriter fwriter2 = new FileWriter("Tocrawl.txt",true);
		BufferedWriter bw2 = new BufferedWriter(fwriter2);
		FileWriter fwriter = new FileWriter("Crawl.txt",true);
		BufferedWriter bw = new BufferedWriter(fwriter);
		
		/*while(null != (crawlData2 = br.readLine()))
		{
			if(crawlData2 !=null)
				Crawl.add(crawlData2);
		}
		t = collectLinks(RTPage);
		Iterator<String> e3= t.iterator();
		while(e3.hasNext())
		{
			String ee = e3.next();
			
				if(!Crawl.contains(ee))
				{
					bw2.write(ee+"\r\n");
				}
				
			
			
		}
		br.close();
		br2.close();
		bw.close();
		bw2.close();*/
		
		if(null == (crawlData = br.readLine()))
		//if(true)
		{
			//initial iteration
			bw.write(RTPage+"\r\n");
			Crawl.add(RTPage);
			t = collectLinks(RTPage);
			ToCrawl.addAll(t);
		}
		else
		{
			//collect data from files and load to array lists
			while(null != (crawlData2 = br.readLine()))
			{
				if(crawlData2 !=null)
					Crawl.add(crawlData2);
			}
				
			while(null != (crawlData3 = br2.readLine()))
			{
				if(crawlData3 !=null)
					ToCrawl.add(crawlData3);
			}
				
		}
		System.out.println("Crawlled");
		
		//Number of movies to be crawled
		for(int i=0;i<1000;i++)
		{
			if(ToCrawl.size()>0)
			{
				Crawl.removeAll(Collections.singleton(null));
				ToCrawl.removeAll(Collections.singleton(null));
				String c = ToCrawl.get(0);
				if(Crawl.contains(c))
					ToCrawl.remove(c);
				else
				{
				//collect links and collect data from a particular link
				Crawl.add(c);
				t = collectLinks(c);
				CollectData(c);
				ToCrawl.remove(c);
				Iterator<String> e3= t.iterator();
				while(e3.hasNext())
				{
					String ee = e3.next();
					if(!ToCrawl.contains(ee))
					{
						if(!Crawl.contains(ee))
						{
							ToCrawl.add(ee);
						}
						
					}
					
				}
				bw.write(c +"\r\n");
				}
			}
		
		}
		
		System.out.println("To Be Crawlled");
		Iterator<String> e2= ToCrawl.iterator();
		while(e2.hasNext())
		{
			//write to file the movies still to be crawled.
			bw2.write(e2.next()+"\r\n");
		}
		
		prop.setProperty("Id", Integer.toString(n));
		prop.store(new FileOutputStream("config.properties"), null);
		br.close();
		br2.close();
		bw.close();
		bw2.close();
	}
	
	public void CollectData(String link)
	{
		
            try {
                //Creating an empty XML Document

            	
                DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
                Document doc = docBuilder.newDocument();
                int flag=0;
                //create the root element and add it to the document
                Element movie = doc.createElement("movie");
                doc.appendChild(movie);
                movie.setAttribute("id", String.valueOf(n));
                n++;
                //create sub elements
                Element genres = doc.createElement("genres");
                Element actors = doc.createElement("actors");
                Element reviews = doc.createElement("reviews");
                
	        URL movieUrl = new URL(link);
	        URL reviewsURL = new URL(link+"reviews/#type=top_critics");
	        BufferedWriter bw3 = new BufferedWriter(new FileWriter("movies.xml",true));
	        int count=-1;
	        String auth = "";
	        BufferedReader br3 = new BufferedReader(new InputStreamReader(movieUrl.openStream()));
	        String str2= "";
	        String info ="";
	        while(null != (str2 = br3.readLine()))
	        {
	        	//start reading the html document
                    if(str2.isEmpty())
                        continue;
                    if(count==14)
                    	break;
                    if(count==12)
                    {
                    	if(!str2.contains("<h3>Cast</h3>"))
                    		continue;
                    	else
                    		count++;
                    }
                    if(count==13)
                    {
                    	if(str2.contains(">ADVERTISEMENT</p>"))
                    	{
                    		count++;
                    		movie.appendChild(actors);
                    		continue;
                    	}
                    	else 
                    		{
                    			if(str2.contains("itemprop=\"name\">"))
                    			{
                    				Element actor = doc.createElement("actor");
                    				actors.appendChild(actor);
                    				Text text = doc.createTextNode(Jsoup.parse(str2.toString()).text());
                    				actor.appendChild(text);
                    			}
                    			else
                    				continue;
                		}
                    		
                    }
                    
                    if(count<=11)
                    {
                    switch(count)
                    {
                    case -1:
                    {
                    	if(!str2.contains("property=\"og:image\""))
                    		continue;
                    	else
                    	{
                    		Pattern image = Pattern.compile("http://.*.jpg",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
                            Matcher match = image.matcher(str2);
                            while(match.find())
                            {
                                
                                Element imageLink = doc.createElement("imageLink");
                                movie.appendChild(imageLink);
                                Text text = doc.createTextNode(match.group());
                                imageLink.appendChild(text);
                                count++;
                            }
                    	}
                    	break;
                    }
                    case 0:
                    {
                    if(str2.contains("<title>"))
                    		{
                    		
                            Element name = doc.createElement("name");
                            movie.appendChild(name);
                            Text text = doc.createTextNode(Jsoup.parse(str2.toString().replace(" - Rotten Tomatoes", "")).text());
                            name.appendChild(text);
                            count++;
                    		}
                    		break;
                    }
                    case 1:
                    {
                    	if(!str2.contains("itemprop=\"ratingValue\""))
                    		break;
                    	else
                    	{
                    		Element score = doc.createElement("score");
                            movie.appendChild(score);
                            Text text = doc.createTextNode(Jsoup.parse(str2.toString()).text());
                            score.appendChild(text);
                    		count++;
                    	}
                    	break;
                    }
                    case 2:
                    {
                    if(!str2.contains("itemprop=\"description\">"))
                            continue;
                    else
                            count++;
                    		break;
                    }
                    case 3:
                    {
                    	
                    	if(!str2.contains("itemprop=\"duration\""))
                    		info=info.concat(str2);
                    	else
                    	{
                    		Element MovieInfo = doc.createElement("MovieInfo");
                            movie.appendChild(MovieInfo);
                            Text text = doc.createTextNode(Jsoup.parse(info.toString()).text());
                            MovieInfo.appendChild(text);
                            info=str2;
                    		count++;
                    	}
                    	break;
                    }
                    case 4:
                    {
                    	if(!str2.contains("itemprop=\"genre\""))
                    		info=info.concat(str2);
                    	else
                    	{
                    		Element duration = doc.createElement("duration");
                    		movie.appendChild(duration);
                    		Text text = doc.createTextNode(Jsoup.parse(info.toString()).text());
                    		duration.appendChild(text);
                            info=str2;
                    		count++;
                    	}
                            break;
                    }
                    case 5:
                    {
                    	if(info.contains("itemprop=\"genre\""))
                		{
                			Element genre = doc.createElement("genre");
                    		genres.appendChild(genre);
                    		Text text = doc.createTextNode(Jsoup.parse(info.toString()).text());
                            genre.appendChild(text);
                            info="";
                		}
                    	if(str2.contains(">Directed By:<"))
                    	{
                    		count++;
                            movie.appendChild(genres);
                            continue;
                    	}
                    	else
                    	{
                    		
                    		if(str2.contains("itemprop=\"genre\""))
                    		{
                    		Element genre = doc.createElement("genre");
                    		genres.appendChild(genre);
                    		Text text = doc.createTextNode(Jsoup.parse(str2.toString()).text());
                            genre.appendChild(text);
                    		}
                    		else
                    			continue;
                    	}
                    	break;	
                            
                    }
                    case 6:
                    {
                    	if(!str2.contains(">Written By:<"))
                    	{
                    		if(str2.contains(">In Theaters:<"))
                    				{
                    				Element director = doc.createElement("director");
                    				movie.appendChild(director);
                    				Text text = doc.createTextNode(Jsoup.parse(info.toString().replace("Directed By: ", "")).text());
                                    director.appendChild(text);
                                    info=str2;
                                    count+=2;
                                    break;
                    				}
                    		info=info.concat(str2);
                    	}
                    	else
                    	{
                    		Element director = doc.createElement("director");
                            movie.appendChild(director);
                            Text text = doc.createTextNode(Jsoup.parse(info.toString().replace("Directed By: ", "")).text());
                            director.appendChild(text);
                            info="";
                            count++;
                    	}
                    	break;
                    }
                    case 7:
                    {
                    	if(!str2.contains(">In Theaters:<"))
                    	{
                    		if(str2.contains(">On DVD:<"))
            				{
            				Element writer = doc.createElement("writer");
            				movie.appendChild(writer);
            				Text text = doc.createTextNode(Jsoup.parse(info.toString()).text());
            				writer.appendChild(text);
                            info=str2;
                            count+=2;
                            break;
            				}
                    		info=info.concat(str2);
                    	}
                    	else
                    	{
                    		Element writer = doc.createElement("writer");
                    		movie.appendChild(writer);
                    		Text text = doc.createTextNode(Jsoup.parse(info.toString()).text());
                            writer.appendChild(text);
                            info=str2;
                    		count++;
                    	}
                            break;
                    }
                    case 8:
                    {
                    	if(!str2.contains(">On DVD:<"))
                    		info=info.concat(str2);
                    	else
                    	{
                    		Element TheatreRelease = doc.createElement("TheatreRelease");
                    		movie.appendChild(TheatreRelease);
                    		Text text = doc.createTextNode(Jsoup.parse(info.toString().replace("In Theaters:", "")).text());
                    		TheatreRelease.appendChild(text);
                            info=str2;
                    		count++;
                    	}
                            break;
                    }
                    case 9:
                    {
                    	if(!str2.contains(">US Box Office:<"))
                    	{
                    		if(str2.contains("itemprop=\"productionCompany\""))
            				{
            				Element DvdRelease = doc.createElement("DvdRelease");
            				movie.appendChild(DvdRelease);
            				Text text = doc.createTextNode(Jsoup.parse(info.toString().replace("On DVD:", "")).text());
            				DvdRelease.appendChild(text);
                            info=str2;
                            count+=2;
                            break;
            				}
                    		info=info.concat(str2);
                    	}
                    	else
                    	{
                    		Element DvdRelease = doc.createElement("DvdRelease");
                    		movie.appendChild(DvdRelease);
                    		Text text = doc.createTextNode(Jsoup.parse(info.toString().replace("On DVD:", "")).text());
                    		DvdRelease.appendChild(text);
                            info=str2;
                    		count++;
                    	}
                            break;
                    }
                    case 10:
                    {
                    	if(!str2.contains("itemprop=\"productionCompany\""))
                    		info=info.concat(str2);
                    	else
                    	{
                    		Element BOCollection = doc.createElement("BOCollection");
                    		movie.appendChild(BOCollection);
                    		Text text = doc.createTextNode(Jsoup.parse(info.toString().replace("US Box Office:", "")).text());
                    		BOCollection.appendChild(text);
                            info=str2;
                    		count++;
                    	}
                            break;
                    }
                    case 11:
                    {
                    	if(!str2.contains(">Official Site"))
                    		info=info.concat(str2);
                    	else
                    	{
                    		Element Production = doc.createElement("Production");
                    		movie.appendChild(Production);
                    		Text text = doc.createTextNode(Jsoup.parse(info.toString()).text());
                    		Production.appendChild(text);
                            info=str2;
                    		count++;
                    	}
                    	break;
                            
                    }
                    	
                    default :
                    	break;
                    }
                    }
                   
                    	
                }
	        	BufferedReader br4 = new BufferedReader(new InputStreamReader(reviewsURL.openStream()));
	        	String str3= "";
	        	String info2="";
	        	int count2=0;
	        	while(null != (str3 = br4.readLine()))
	        	{
	        		if(count2==0)
	        			
	        		{
	        			if(!str3.contains("<div class=\"reviewsnippet\">"))
	        				continue;
	        			else
	        				count2++;
	        		}
	        		if(count2==1)
	        		{
	        			if(!str3.contains("<p class=\"small subtle\">"))
	        				info2= info2.concat(str3);
	        			else
	        			{
	        				Element review = doc.createElement("review");
                            reviews.appendChild(review);
                            Text text = doc.createTextNode(Jsoup.parse(info2.toString()).text());
                            review.appendChild(text);
                            info2="";
                    		count2=0;
	        			}
	        		}
	        			
	        	}
	        	movie.appendChild(reviews);
                TransformerFactory transfac = TransformerFactory.newInstance();
                Transformer trans = transfac.newTransformer();
                trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                trans.setOutputProperty(OutputKeys.INDENT, "yes");

                //create string from xml tree
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                DOMSource source = new DOMSource(doc);
                trans.transform(source, result);
                String xmlString = sw.toString();
                bw3.write(xmlString);
                br3.close();
                br4.close();
                bw3.close();
		}
                catch (Exception ex) {
	        ex.printStackTrace();
	    }	
	}
	
	public ArrayList<String> collectLinks(String p)
	{
            ArrayList<String> PageLinks = new ArrayList<String>();
            try {
            
                URL url = new URL(p);
                BufferedReader br3 = new BufferedReader(new InputStreamReader(url.openStream()));
                String str = "";
                while(null != (str = br3.readLine()))
                        {
                        Pattern link = Pattern.compile("<a target=\"_top\" href=\"/m/.*",  Pattern.CASE_INSENSITIVE|Pattern.DOTALL);
                            Matcher match = link.matcher(str);
                                    while(match.find())
                                    {
                                          String tmp = match.group();
                                          int start = tmp.indexOf('/');
                                          tmp = tmp.substring(start+1,tmp.indexOf('\"', start+1));
                                          if(Crawl.contains("http://www.rottentomatoes.com/"+tmp)||ToCrawl.contains("http://www.rottentomatoes.com/"+tmp)||PageLinks.contains("http://www.rottentomatoes.com/"+tmp))
                                                  continue;
                                          PageLinks.add("http://www.rottentomatoes.com/"+tmp);
                                          //bw4.write("http://www.rottentomatoes.com/"+tmp+"\r\n");
                                    }

                        }

             br3.close();
	}
        catch (Exception ex) {
            ex.printStackTrace();
    }
            
return PageLinks;
	}
}

