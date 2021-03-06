﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;
using System.Text.RegularExpressions;
using System.Net;
using System.IO;
using System.Text;
using System.Xml;
using System.Xml.Linq;
using System.Xml.XPath;
using System.ServiceModel;
using Google.GData.Client;
using Google.GData.Extensions;
using System.Web.UI.DataVisualization.Charting;
using System.Drawing;
using System.Diagnostics;
using System.Net;


public partial class Movie : System.Web.UI.Page
{
    string connStr = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com;
    string[] res;



    private static AtomFeed GetFeed(string url, int start, int number)
    {
        System.Diagnostics.Trace.Write("Conectando youtube at " + url);
        FeedQuery query = new FeedQuery("");
        Service service = new Service("youtube", "exampleCo");
        query.Uri = new Uri(url);
        query.StartIndex = start;
        query.NumberToRetrieve = number;

        AtomFeed myFeed = service.Query(query);
        return myFeed;
    }



    private string RenderVideoEmbedded(string id)
    {
        return string.Format("<div id=\"video{0}\"><object width=\"425\" height=\"355\"><param name=\"movie\" value=\"http://www.youtube.com/v/{0}&rel=1\"></param><param name=\"wmode\" value=\"transparent\"></param><embed src=\"http://www.youtube.com/v/{0}&rel=1\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" width=\"425\" height=\"355\"></embed></object></div>", id);
    }

    private string getIDSimple(string googleID)
    {
        int lastSlash = googleID.LastIndexOf("/");
        return googleID.Substring(lastSlash + 1);
    }


    protected void SubmitButton_Click(object sender, EventArgs e)
    {
        value1.Visible = true;
        value2.Visible = true;
        try
        {
            int i, i2;
            var b = Encoding.UTF8.GetBytes(TextBox1.Text);
            String txt = Convert.ToBase64String(b);
            String b1 = "", b2 = "", b3 = "", b4 = "";
            // Create the request
            string xmlRequest = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><uclassify xmlns=\"http://api.uclassify.com/1/RequestSchema\" version=\"1.01\"><texts><textBase64 id=\"TextId\">" + txt + "</textBase64></texts><readCalls readApiKey=\"pNQ3w9RCfbLcVsJ5tPT8dNAIjZM\"><classify id=\"Classify\" classifierName=\"ReviewClassifier\" textId=\"TextId\"/></readCalls></uclassify>";
            // Send the request
            HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create("http://api.uclassify.com");
            webRequest.Method = "Post";
            webRequest.ContentType = "text/XML";
            StreamWriter writer = new StreamWriter(webRequest.GetRequestStream());
            writer.Write(xmlRequest);
            writer.Close();

            // Read the response
            HttpWebResponse webResponse = (HttpWebResponse)webRequest.GetResponse();
            StreamReader reader = new StreamReader(webResponse.GetResponseStream());
            string xmlResponse = reader.ReadToEnd();
            reader.Close();
            i = xmlResponse.IndexOf("class className=");
            xmlResponse = xmlResponse.Substring(i);
            i = xmlResponse.IndexOf("\"");
            b1 = xmlResponse.Substring(i + 1, 8);
            xmlResponse = xmlResponse.Substring(i + 10);
            i = xmlResponse.IndexOf("\"");
            i2 = xmlResponse.Substring(i + 1).IndexOf("\"");
            b2 = xmlResponse.Substring(i + 1, i2);
            b2 = (Decimal.Parse(b2, System.Globalization.NumberStyles.Any)).ToString();
            xmlResponse = xmlResponse.Substring(i + i2 + 2);
            i = xmlResponse.IndexOf("\"");
            b3 = xmlResponse.Substring(i + 1, 8);
            xmlResponse = xmlResponse.Substring(i + 10);
            i = xmlResponse.IndexOf("\"");
            i2 = xmlResponse.Substring(i + 1).IndexOf("\"");
            b4 = xmlResponse.Substring(i + 1, i2);
            b4 = (Decimal.Parse(b4, System.Globalization.NumberStyles.Any)).ToString();
            class1.Text = b1;
            value1.Text = b2;
            class2.Text = b3;
            value2.Text = b4;
            if (Convert.ToDecimal(b2) > Convert.ToDecimal(b4))
                value1.BackColor = System.Drawing.Color.Red;
            else if (Convert.ToDecimal(b4) > Convert.ToDecimal(b2))
                value2.BackColor = System.Drawing.Color.Green;
            else
                value1.BackColor = value2.BackColor = System.Drawing.Color.White;

        }

        catch (Exception e2)
        {
            Console.WriteLine(e2.Message);
        }
    }

    protected void classify(string cl)
    {
        int i, i2;
        var b = Encoding.UTF8.GetBytes(cl);
        String txt = Convert.ToBase64String(b);
        String b1 = "", b2 = "", b3 = "", b4 = "";
        // Create the request
        string xmlRequest = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><uclassify xmlns=\"http://api.uclassify.com/1/RequestSchema\" version=\"1.01\"><texts><textBase64 id=\"TextId\">" + txt + "</textBase64></texts><readCalls readApiKey=\"pNQ3w9RCfbLcVsJ5tPT8dNAIjZM\"><classify id=\"Classify\" classifierName=\"ReviewClassifier\" textId=\"TextId\"/></readCalls></uclassify>";
        // Send the request
        HttpWebRequest webRequest = (HttpWebRequest)WebRequest.Create("http://api.uclassify.com");
        webRequest.Method = "Post";
        webRequest.ContentType = "text/XML";
        StreamWriter writer = new StreamWriter(webRequest.GetRequestStream());
        writer.Write(xmlRequest);
        writer.Close();

        // Read the response
        HttpWebResponse webResponse = (HttpWebResponse)webRequest.GetResponse();
        StreamReader reader = new StreamReader(webResponse.GetResponseStream());
        string xmlResponse = reader.ReadToEnd();
        reader.Close();
        i = xmlResponse.IndexOf("class className=");
        xmlResponse = xmlResponse.Substring(i);
        i = xmlResponse.IndexOf("\"");
        b1 = xmlResponse.Substring(i + 1, 8);
        xmlResponse = xmlResponse.Substring(i + 10);
        i = xmlResponse.IndexOf("\"");
        i2 = xmlResponse.Substring(i + 1).IndexOf("\"");
        b2 = xmlResponse.Substring(i + 1, i2);
        b2 = (Decimal.Parse(b2, System.Globalization.NumberStyles.Any)).ToString();
        xmlResponse = xmlResponse.Substring(i + i2 + 2);
        i = xmlResponse.IndexOf("\"");
        b3 = xmlResponse.Substring(i + 1, 8);
        xmlResponse = xmlResponse.Substring(i + 10);
        i = xmlResponse.IndexOf("\"");
        i2 = xmlResponse.Substring(i + 1).IndexOf("\"");
        b4 = xmlResponse.Substring(i + 1, i2);
        b4 = (Decimal.Parse(b4, System.Globalization.NumberStyles.Any)).ToString();
        class3.Text = b1;
        value3.Text = b2;
        class4.Text = b3;
        value4.Text = b4;
        if (Convert.ToDecimal(b2) > Convert.ToDecimal(b4))
            value3.BackColor = System.Drawing.Color.Red;
        else if (Convert.ToDecimal(b4) > Convert.ToDecimal(b2))
            value4.BackColor = System.Drawing.Color.Green;
        else
            value3.BackColor = value4.BackColor = System.Drawing.Color.White;
    }
    protected void Page_Load(object sender, EventArgs e)
    {
        Genre.Items.Clear();
        Review.Items.Clear();
        value1.BackColor = value2.BackColor = System.Drawing.Color.White;
        int movieId = Convert.ToInt32(System.Web.HttpUtility.UrlDecode(Request.QueryString["id"]));
        SqlConnection con = new SqlConnection(connStr);
        List<int> currentMovie = new List<int>();
        List<string> totalUsers = new List<string>();
        List<int> movieToShow = new List<int>();
        con.Open();

        com = new SqlCommand("Select name from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string name = (string)com.ExecuteScalar();

        com = new SqlCommand("Select score from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string score = (string)com.ExecuteScalar();
        Score.Text = score;

        com = new SqlCommand("Select MovieInfo from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string MovieInfo = (string)com.ExecuteScalar();
        Movieinfo.Text = MovieInfo;

        com = new SqlCommand("Select duration from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string duration = (string)com.ExecuteScalar();
        Duration.Text = duration;

        com = new SqlCommand("Select director from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string director = (string)com.ExecuteScalar();
        Director.Text = director;

        com = new SqlCommand("Select writer from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string writer = (string)com.ExecuteScalar();
        Writer.Text = writer;

        com = new SqlCommand("Select TheatreRelease from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string TheatreRelease = (string)com.ExecuteScalar();
        Theatre.Text = TheatreRelease;

        com = new SqlCommand("Select DvdRelease from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string DvdRelease = (string)com.ExecuteScalar();
        Dvd.Text = DvdRelease;

        com = new SqlCommand("Select BOCollection from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string BOCollection = (string)com.ExecuteScalar();
        Bo.Text = BOCollection;

        com = new SqlCommand("Select Production from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string production = (string)com.ExecuteScalar();
        Production.Text = production;

        com = new SqlCommand("Select genres from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string genres = (string)com.ExecuteScalar();
        foreach (string s in genres.Split(new String[] { "***" }, StringSplitOptions.RemoveEmptyEntries))
        {
            string t = s.Replace("***", "");
            t = t.Replace(",", "");
            Genre.Items.Add(t);
        }

        com = new SqlCommand("Select actors from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string Actor = (string)com.ExecuteScalar();
        Actors.Text = Actor.Replace("***", ", ");

        com = new SqlCommand("Select reviews from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string reviews = (string)com.ExecuteScalar();
        foreach (string s in reviews.Split(new String[] { "***" }, StringSplitOptions.RemoveEmptyEntries))
        {
            string s3 = "";
            foreach (string s2 in s.Split(new String[] { "$$" }, StringSplitOptions.RemoveEmptyEntries))
            {
                s2.Replace("$$", "");
                if (s2.Length > 10)
                {
                    s3 += s2;
                }
                else
                {
                    s3 += "--" + s2;
                }

            }
            Review.Items.Add(s3.Replace("***", ""));
        }


        com = new SqlCommand("Select imageLink from Movies where id =" + movieId, con);

        com.ExecuteNonQuery();
        string imageMovie = (string)com.ExecuteScalar();

        MovieName.Text = name;
        m.ImageUrl = imageMovie;

        com = new SqlCommand("Select pos_score from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string pos = (string)com.ExecuteScalar();
        Production.Text = production;

        com = new SqlCommand("Select neg_score from Movies where id =" + movieId, con);
        com.ExecuteNonQuery();
        string neg = (string)com.ExecuteScalar();

        Production.Text = production;
        con.Close();

        string url = "http://gdata.youtube.com/feeds/videos?q=" + name + "movie review";
        int ii = -1;
        AtomFeed myFeed = GetFeed(url, 1, 6);
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        sb.Append("<table width=\"100%\"><tr>");
        foreach (AtomEntry entry in myFeed.Entries)
        {
            ii++;
            if (ii % 2 == 0)
            {
                sb.Append("</tr><tr><td width=\"45%\"><br /><b>Title:</b> ");
                sb.Append(entry.Title.Text);
                sb.Append("<br /> URL : " + entry.AlternateUri);
                sb.Append(RenderVideoEmbedded(getIDSimple(entry.Id.AbsoluteUri)));
                sb.Append("</td>");
            }
            else
            {
                sb.Append("<td width=\"30%\"><b>Title:</b> ");
                sb.Append(entry.Title.Text);
                sb.Append("<br /> URL : " + entry.AlternateUri);
                sb.Append(RenderVideoEmbedded(getIDSimple(entry.Id.AbsoluteUri)));
                sb.Append("</td>");
            }

        }
        sb.Append("</tr></table>");
        results.Text = sb.ToString();

        int pp = Convert.ToInt32(Math.Round(100 * Convert.ToDouble(pos)));
        int nn = 100 - pp;

        Dictionary<string, int> cdata = new Dictionary<string, int>();
        cdata.Add("Positive (" + pp + "%)", pp);
        cdata.Add("Negative (" + nn + "%)", nn);
        Chart1.Series[0].Points.DataBindXY(cdata.Keys, cdata.Values);

        Color[] pieColor = new Color[2] { Color.FromKnownColor(KnownColor.Green), Color.FromKnownColor(KnownColor.Red) };
        Chart1.Palette = ChartColorPalette.None;
        Chart1.PaletteCustomColors = pieColor;
    }




    protected void ImageButton1_Click(object sender, ImageClickEventArgs e)
    {
        SqlConnection con = new SqlConnection(connStr);
        int id = Convert.ToInt32(System.Web.HttpUtility.UrlDecode(Request.QueryString["id"]));
        com = new SqlCommand("insert into UserLikeMovies(UserName,id) values ('" + @User.Identity.Name + "'," + id + ")", con);
        con.Open();
        com.ExecuteNonQuery();
        con.Close();
        Response.Redirect("Movie.aspx?id=" + System.Web.HttpUtility.UrlDecode(Request.QueryString["id"]));

    }
    protected void videoReview_Click(object sender, EventArgs e)
    {
        Process.Start(new ProcessStartInfo(@"C:\Users\Soumya\Desktop\youtube\Google2SRT-0.6\Google2SRT.jar"));
    }
    protected void Analyze_Click(object sender, EventArgs e)
    {
        value3.Visible = true;
        value4.Visible = true;

        if (!(TextBox2.Text == ""))
        {

            string link = TextBox2.Text;
            link = link.Substring(link.IndexOf("v=") + 2);
            link = link.Substring(0, link.IndexOf("&feature"));
            DirectoryInfo directory = new DirectoryInfo(@"C:\Users\Soumya\Documents\reviews");
            FileInfo[] files = directory.GetFiles("*.srt");
            foreach (var f in files)
            {
                if (f.Name.Contains(link))
                {
                    string ln, classificationText = null;
                    StreamReader strReader = f.OpenText();
                    while (true)
                    {
                        ln = strReader.ReadLine();
                        if (ln != null)
                        {
                            if (ln.Contains("-->") || ln.Equals("") || ln.Length < 4)
                            {
                                continue;
                            }
                            classificationText += ln + "\n";
                        }
                        else
                        {
                            break;
                        }
                    }
                    TextBox3.Text = classificationText;
                    classify(classificationText);
                    break;
                }
                else continue;
            }

        }

    }
}