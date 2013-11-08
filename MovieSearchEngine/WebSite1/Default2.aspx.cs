using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Xml;
using System.Xml.XPath;
using Lucene.Net.Index;
using Lucene.Net.Analysis;
using Lucene.Net.Analysis.Standard;
using Lucene.Net.Documents;
using Lucene.Net.Store;
using Lucene.Net.Search;
using Lucene.Net.Index;
using Lucene.Net.QueryParsers;

public partial class Reviewer_Default2 : System.Web.UI.Page
{
    string connStr = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com;
    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        string index = @"C:\Users\Soumya\Documents\Visual Studio 2013\WebSites\WebSite1\indx";
        Directory dir = FSDirectory.GetDirectory(index);
        System.Xml.XmlDataDocument file = new System.Xml.XmlDataDocument();
        file.Load(@"C:\Users\Soumya\Documents\Visual Studio 2013\WebSites\WebSite1\App_Data\movies.xml");
        Analyzer ana = new StandardAnalyzer();
        IndexWriter wr = new IndexWriter(dir, ana, true);
        try
        {
            foreach (System.Xml.XPath.XPathNavigator movie in file.CreateNavigator().Select("//movie"))
            {
                string b = "", c = "", d = "", e2 = "", f = "", g = "", h = "", i = "", j = "", k = "", l = "", m = "", n = "", o = "", p = "";
                Document movieIterator = new Document();
                movieIterator.Add(new Field("id", movie.GetAttribute("id", string.Empty), Field.Store.YES, Field.Index.NO, Field.TermVector.NO));
                int a = Convert.ToInt32(movie.GetAttribute("id", string.Empty));
                if (movie.SelectSingleNode("imageLink") != null)
                {
                    b = movie.SelectSingleNode("imageLink").Value;
                }
                if (movie.SelectSingleNode("name") != null)
                {
                    c = movie.SelectSingleNode("name").Value;
                    movieIterator.Add(new Field("name", movie.SelectSingleNode("name").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));
                }
                if (movie.SelectSingleNode("score") != null)
                {
                    d = movie.SelectSingleNode("score").Value;
                }
                if (movie.SelectSingleNode("MovieInfo") != null)
                {
                    e2 = movie.SelectSingleNode("MovieInfo").Value;
                    movieIterator.Add(new Field("MovieInfo", movie.SelectSingleNode("MovieInfo").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));
                }
                if (movie.SelectSingleNode("duration") != null)
                {
                    f = movie.SelectSingleNode("duration").Value;
                }
                if (movie.SelectSingleNode("genres") != null)
                {
                    XPathNodeIterator xx = movie.SelectSingleNode("genres").SelectChildren(XPathNodeType.Element);
                    while (xx.MoveNext())
                    {
                        g = g + xx.Current.Value + "***";
                    }
                    movieIterator.Add(new Field("genres", movie.SelectSingleNode("genres").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));
                }
                if (movie.SelectSingleNode("director") != null)
                {
                    h = movie.SelectSingleNode("director").Value;
                    movieIterator.Add(new Field("director", movie.SelectSingleNode("director").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));

                }
                if (movie.SelectSingleNode("writer") != null)
                {
                    i = movie.SelectSingleNode("writer").Value;
                    movieIterator.Add(new Field("writer", movie.SelectSingleNode("writer").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));
                }
                if (movie.SelectSingleNode("TheatreRelease") != null)
                {
                    j = movie.SelectSingleNode("TheatreRelease").Value;
                }
                if (movie.SelectSingleNode("DvdRelease") != null)
                {
                    k = movie.SelectSingleNode("DvdRelease").Value;
                }
                if (movie.SelectSingleNode("BOCollection") != null)
                {
                    l = movie.SelectSingleNode("BOCollection").Value;
                }
                if (movie.SelectSingleNode("Production") != null)
                {
                    m = movie.SelectSingleNode("Production").Value;
                    movieIterator.Add(new Field("Production", movie.SelectSingleNode("Production").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));
                }
                if (movie.SelectSingleNode("actors") != null)
                {
                    XPathNodeIterator xx = movie.SelectSingleNode("actors").SelectChildren(XPathNodeType.Element);
                    while (xx.MoveNext())
                    {
                        n = n + xx.Current.Value + "***";
                    }

                    movieIterator.Add(new Field("actors", movie.SelectSingleNode("actors").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));
                }
                if (movie.SelectSingleNode("reviews") != null)
                {
                    XPathNodeIterator xx = movie.SelectSingleNode("reviews").SelectChildren(XPathNodeType.Element);
                    while (xx.MoveNext())
                    {
                        o = o + xx.Current.Value + "***";
                    }

                    movieIterator.Add(new Field("reviews", movie.SelectSingleNode("reviews").Value, Field.Store.YES, Field.Index.TOKENIZED, Field.TermVector.NO));
                }
                if (movie.SelectSingleNode("classifiedscore") != null)
                {
                    k = movie.SelectSingleNode("classifiedscore").Value;
                }
                SqlConnection con = new SqlConnection(connStr);
                com = new SqlCommand("insert into movies(id,imageLink,name,score,MovieInfo,duration,genres,director,writer,TheatreRelease,DvdRelease,BOCollection,Production,actors,reviews,classifiedscore) values (@a,@b,@c,@d,@e2,@f,@g,@h,@i,@j,@k,@l,@m,@n,@o,@p)", con);
                com.Parameters.Add("@a", a);
                com.Parameters.Add("@b", b);
                com.Parameters.Add("@c", c);
                com.Parameters.Add("@d", d);
                com.Parameters.Add("@e2", e2);
                com.Parameters.Add("@f", f);
                com.Parameters.Add("@g", g);
                com.Parameters.Add("@h", h);
                com.Parameters.Add("@i", i);
                com.Parameters.Add("@j", j);
                com.Parameters.Add("@k", k);
                com.Parameters.Add("@l", l);
                com.Parameters.Add("@m", m);
                com.Parameters.Add("@n", n);
                com.Parameters.Add("@o", o);
                com.Parameters.Add("@o", p);
                con.Open();
                com.ExecuteNonQuery();
                con.Close();

                wr.AddDocument(movieIterator);
            }
            dir.Close();
            wr.Optimize();

        }
        finally { wr.Close(); }



    }
}