using System;
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

public partial class uclassify : System.Web.UI.Page
{
    string connStr = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com;
    SqlCommand com2;

    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(connStr);
        con.Open();
        for (int count = 4501; count <= 6227; count++)
        {
            try
            {
                string s3 = "";
                com = new SqlCommand("Select reviews from Movies where id =" + count, con);
                com.ExecuteNonQuery();
                string reviews = (string)com.ExecuteScalar();
                foreach (string s in reviews.Split(new String[] { "***" }, StringSplitOptions.RemoveEmptyEntries))
                {

                    foreach (string s2 in s.Split(new String[] { "$$" }, StringSplitOptions.RemoveEmptyEntries))
                    {
                        s2.Replace("$$", "");
                        if (s2.Length > 10)
                        {
                            s3 += s2;
                        }

                    }

                }

                int i, i2;
                var b = Encoding.UTF8.GetBytes(s3);
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

                com2 = new SqlCommand("UPDATE Movies SET neg_score=@b2,pos_score=@b4 WHERE id=@count", con);
                com2.Parameters.Add("@b2", b2);
                com2.Parameters.Add("@b4", b4);
                com2.Parameters.Add("@count", count);
                com2.ExecuteNonQuery();
            }

            catch (Exception e2)
            {
                Console.WriteLine(e2.Message);
            }
        }
        con.Close();
    }
}