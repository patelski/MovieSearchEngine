using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI.WebControls;
using Lucene.Net.Index;
using Lucene.Net.Analysis;
using Lucene.Net.Analysis.Standard;
using Lucene.Net.Documents;
using Lucene.Net.Store;
using System.Configuration;
using Lucene.Net.Search;
using Lucene.Net.Index;
using Lucene.Net.QueryParsers;
using System.Xml;
using System.Xml.XPath;



public partial class Search : System.Web.UI.Page
{

    static List<string> l = new List<string>();
    string connStr = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com;

    protected void Page_Load(object sender, EventArgs e)
    {

    }

    public List<string> resultsList()
    {
        return l;
    }
    protected void Button2_Click(object sender, EventArgs e)
    {
        if (search2.Text == "")
        {
            //User forgot to enter the query
            disp.Text = "You forgot to enter your query :)";
            return;
        }
        int i;
        int fl = 0;
        string message = "<table width=\"100%\">";
        disp.Text = "";
        string searchQ = search2.Text;
        searchQ = searchQ.Replace(":", " ");
        string index = @"C:\Users\Soumya\Documents\Visual Studio 2013\WebSites\WebSite1\indx";
        Directory d = FSDirectory.GetDirectory(index);
        SqlConnection con = new SqlConnection(connStr);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser(categoryChooser.Text, analyzer);
        Query query = parser.Parse(searchQ);
        var reader = IndexReader.Open(d);
        var searcher = new IndexSearcher(reader);
        var hits = searcher.Search(query, null, 10, new Sort());
        if (hits.totalHits == 0)
        {
            //no match
            disp.Text = "Sorry, your query did not match the movies in our database";
        }
        if (hits.totalHits < 10)
        {
            //when total hits is less than 10 , display all results
            for (int j = 0; j < hits.totalHits; j++)
            {
                fl = 0;
                ScoreDoc scoreDoc = hits.scoreDocs[j];
                float score = scoreDoc.score;
                int docId = scoreDoc.doc;
                Document doc = searcher.Doc(docId);
                i = Convert.ToInt32(doc.Get("id"));
                com = new SqlCommand("Select imageLink,name,MovieInfo from Movies where id =" + i, con);
                con.Open();

                SqlDataReader sq2 = com.ExecuteReader();
                while (sq2.Read())
                {
                    if (sq2[2].ToString() == "")
                        fl = 1;
                }
                if (fl == 1)
                {
                    con.Close();
                    sq2.Close();
                    continue;
                }
                con.Close();
                sq2.Close();
                con.Open();
                message = message + "<tr>";
                SqlDataReader sq = com.ExecuteReader();
                while (sq.Read())
                {
                    message = message + "<td height = \"185\" width= \"15%\" align=\"left\" valign=\"to\"> <img src='" + sq[0].ToString() + "' style=\"height:200px;width:165px;\"></img></td><td height = \"175\" width= \"85%\"><p><b><a href='Movie.aspx?id=" + i + "'>" + sq[1].ToString() + "</a></b></p><p>Movie Info :<font color =\"#ffffcc\">" + sq[2].ToString() + "</font></p></td>";
                }
                message = message + "</tr>";
                con.Close();
                sq.Close();


            }
            disp.Text += message;
            disp.Text += "</table>";
        }
        else
        {
            //if total hits greater than 10, display only top 10 results
            for (int j = 0; j < 10; j++)
            {
                fl = 0;
                ScoreDoc scoreDoc = hits.scoreDocs[j];
                float score = scoreDoc.score;
                int docId = scoreDoc.doc;
                Document doc = searcher.Doc(docId);
                i = Convert.ToInt32(doc.Get("id"));
                com = new SqlCommand("Select imageLink,name,MovieInfo from Movies where id =" + i, con);
                con.Open();

                SqlDataReader sq2 = com.ExecuteReader();
                while (sq2.Read())
                {
                    if (sq2[2].ToString() == "")
                        fl = 1;
                }
                if (fl == 1)
                {
                    con.Close();
                    sq2.Close();
                    continue;
                }
                con.Close();
                sq2.Close();
                con.Open();
                SqlDataReader sq = com.ExecuteReader();
                message = message + "<tr>";
                while (sq.Read())
                {
                    message = message + "<td height = \"185\" width= \"15%\" align=\"left\" valign=\"to\"> <img src='" + sq[0].ToString() + "' style=\"height:200px;width:165px;\"></img></td><td height = \"175\" width= \"85%\"><p><b><a href='Movie.aspx?id=" + i + "'>" + sq[1].ToString() + "</a></b></p><p>Movie Info :<font color =\"#ffffcc\">" + sq[2].ToString() + "</font></p></td>";
                }
                message = message + "</tr>";
                con.Close();
                sq.Close();



            }
            disp.Text += message;
            disp.Text += "</table>";
        }
        reader.Close();
        searcher.Close();
        d.Close();
    }

    protected void Button3_Click(object sender, EventArgs e)
    {
        if (search.Text == "")
        {
            //User forgot to enter the query
            disp.Text = "You forgot to enter your query :)";
            return;
        }
        int i;
        int fl = 0;
        string message = "<table width=\"100%\">";
        disp.Text = "";
        string searchQ = search.Text;
        searchQ = searchQ.Replace(":", " ");
        string index = @"C:\Users\Soumya\Documents\Visual Studio 2013\WebSites\WebSite1\indx";
        Directory d = FSDirectory.GetDirectory(index);
        SqlConnection con = new SqlConnection(connStr);
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("name", analyzer);
        Query query = parser.Parse(searchQ);
        var reader = IndexReader.Open(d);
        var searcher = new IndexSearcher(reader);
        var hits = searcher.Search(query, null, 10, new Sort());
        if (hits.totalHits == 0)
        {
            //no match
            disp.Text = "Sorry, your query did not match the movies in our database";
        }
        if (hits.totalHits < 10)
        {
            //when total hits is less than 10 , display all results
            for (int j = 0; j < hits.totalHits; j++)
            {
                fl = 0;
                ScoreDoc scoreDoc = hits.scoreDocs[j];
                float score = scoreDoc.score;
                int docId = scoreDoc.doc;
                Document doc = searcher.Doc(docId);
                i = Convert.ToInt32(doc.Get("id"));
                com = new SqlCommand("Select imageLink,name,MovieInfo from Movies where id =" + i, con);
                con.Open();

                SqlDataReader sq2 = com.ExecuteReader();
                while (sq2.Read())
                {
                    if (sq2[2].ToString() == "")
                        fl = 1;
                }
                if (fl == 1)
                {
                    con.Close();
                    sq2.Close();
                    continue;
                }
                con.Close();
                sq2.Close();
                con.Open();
                message = message + "<tr>";
                SqlDataReader sq = com.ExecuteReader();
                while (sq.Read())
                {
                    message = message + "<td height = \"185\" width= \"15%\" align=\"left\" valign=\"to\"> <img src='" + sq[0].ToString() + "' style=\"height:200px;width:165px;\"></img></td><td height = \"175\" width= \"85%\"><p><b><a href='Movie.aspx?id=" + i + "'>" + sq[1].ToString() + "</a></b></p><p>Movie Info :<font color =\"#ffffcc\">" + sq[2].ToString() + "</font></p></td>";
                }
                message = message + "</tr>";
                con.Close();
                sq.Close();


            }

            disp.Text += message;
            disp.Text += "</table>";
        }
        else
        {
            //if total hits greater than 10, display only top 10 results
            for (int j = 0; j < 10; j++)
            {
                fl = 0;
                ScoreDoc scoreDoc = hits.scoreDocs[j];
                float score = scoreDoc.score;
                int docId = scoreDoc.doc;
                Document doc = searcher.Doc(docId);
                i = Convert.ToInt32(doc.Get("id"));
                com = new SqlCommand("Select imageLink,name,MovieInfo from Movies where id =" + i, con);
                con.Open();

                SqlDataReader sq2 = com.ExecuteReader();
                while (sq2.Read())
                {
                    if (sq2[2].ToString() == "")
                        fl = 1;
                }
                if (fl == 1)
                {
                    con.Close();
                    sq2.Close();
                    continue;
                }
                con.Close();
                sq2.Close();
                con.Open();
                SqlDataReader sq = com.ExecuteReader();
                message = message + "<tr>";
                while (sq.Read())
                {
                    message = message + "<td height = \"185\" width= \"15%\" align=\"left\" valign=\"to\"> <img src='" + sq[0].ToString() + "' style=\"height:200px;width:165px;\"></img></td><td height = \"175\" width= \"85%\"><p><b><a href='Movie.aspx?id=" + i + "'>" + sq[1].ToString() + "</a></b></p><p>Movie Info :<font color =\"#ffffcc\">" + sq[2].ToString() + "</font></p></td>";
                }
                message = message + "</tr>";
                con.Close();
                sq.Close();



            }
            disp.Text += message;
            disp.Text += "</table>";
        }
        reader.Close();
        searcher.Close();
        d.Close();
    }
}