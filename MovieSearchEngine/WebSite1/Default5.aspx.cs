
using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using Google.GData.Client;
using Google.GData.Extensions;

public partial class Default5 : System.Web.UI.Page 
{
    
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

    private string getIDSimple(string googleID) { 
        int lastSlash=googleID.LastIndexOf("/");
        return googleID.Substring(lastSlash + 1);                
    }


   
    private void DisplayFeed(AtomFeed myFeed)
    {
        System.Text.StringBuilder sb = new System.Text.StringBuilder();
        foreach (AtomEntry entry in myFeed.Entries)
        {
            #region render each
            sb.Append("<br /><b>Title:</b> ");
            sb.Append(entry.Title.Text);
            sb.Append("<br /><b>Categories:</b> ");            
            foreach (AtomCategory cat in entry.Categories)
            {
                sb.Append(cat.Term);
                sb.Append(",");
            }
            sb.Append(RenderVideoEmbedded(getIDSimple(entry.Id.AbsoluteUri)));
            #endregion
        }
        this.results.Text = sb.ToString();
    }





    
    protected void search1_Click(object sender, EventArgs e)
    {
        string url = "http://gdata.youtube.com/feeds/videos/-/" + this.tag.Text;
        AtomFeed myFeed = GetFeed(url, 1, 5);
        DisplayFeed(myFeed);        
    }

    protected void search2_Click(object sender, EventArgs e)
    {
        string url = "http://gdata.youtube.com/feeds/videos?q=" + this.search.Text + "movie review";
        AtomFeed myFeed = GetFeed(url, 1, 5);
        DisplayFeed(myFeed);
    }
}
