using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Net;
using System.Text;

public partial class Default4 : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        
    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        StreamWriter wr = new StreamWriter(@"C:\\Users\\Soumya\\Desktop\\assign\\txt_sentoken\\neg3\\response.txt", true);
        foreach (var file in Directory.EnumerateFiles(@"C:\\Users\\Soumya\\Desktop\\assign\\txt_sentoken\\neg3", "*.txt"))
        {

            StreamReader str = new StreamReader(file.ToString());
            
            //String txt = Convert.ToBase64String(b);
            string content = str.ReadToEnd();
            var b = Encoding.UTF8.GetBytes(content);
            String txt = Convert.ToBase64String(b);
            try
            {
                // Create the request
                string xmlRequest = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><uclassify xmlns=\"http://api.uclassify.com/1/RequestSchema\" version=\"1.01\"><texts><textBase64 id=\"Text1\">"+ txt +"</textBase64></texts><writeCalls writeApiKey=\"6z5Qqg4YmRo9OLhm2qN3ISFphw\" classifierName=\"ReviewClassifier\"><train id=\"Train\" className=\"Negative\" textId=\"Text1\"/></writeCalls></uclassify>";

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
                wr.Write(xmlResponse);
            }
                
            catch (Exception e2)
            {
                Console.WriteLine(e2.Message);
            }
        }
        wr.Close();
    }
}