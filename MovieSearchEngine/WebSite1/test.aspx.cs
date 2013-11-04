using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;
public static class TextTool
{
    /// <summary>
    /// Count occurrences of strings.
    /// </summary>
    public static int CountStringOccurrences(string text, string pattern)
    {
        // Loop through all instances of the string 'text'.
        int count = 0;
        int i = 0;
        while ((i = text.IndexOf(pattern, i)) != -1)
        {
            i += pattern.Length;
            count++;
        }
        return count;
    }
}
public partial class test : System.Web.UI.Page
{
    int count = 0;
    string c = "";
    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            using (StreamReader sr = new StreamReader(@"C:\\Users\\Soumya\\Desktop\\assign\\txt_sentoken\\pos3\\response.txt"))
            {
               c= sr.ReadToEnd();
               count = TextTool.CountStringOccurrences(c, "<?xml");
               Console.WriteLine(TextTool.CountStringOccurrences(c, "<?xml")); 
            }
        }
        catch (Exception e4)
        {
            Console.WriteLine("The file could not be read:");
            Console.WriteLine(e4.Message);
        }
        

    }
}