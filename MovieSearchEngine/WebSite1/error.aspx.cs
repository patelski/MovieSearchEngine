using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Configuration;
using System.Data.SqlClient;
using System.Web.UI.DataVisualization.Charting;
using System.Drawing;

public partial class error : System.Web.UI.Page
{
    static string conn = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com, com2;
    Dictionary<string, int> dict = new Dictionary<string, int>();
    Dictionary<string, int> dict2 = new Dictionary<string, int>();
    Dictionary<string, int> dict3 = new Dictionary<string, int>();
    SqlConnection con = new SqlConnection(conn);

    protected void Page_Load(object sender, EventArgs e)
    {

    }
    protected void Button1_Click(object sender, EventArgs e)
    {
        int tp = 0, tn = 0, fp = 0, fn = 0, nothing = 0, nothing2=0;
        com = new SqlCommand("Select score,pos_score from Movies", con);
        con.Open();

        SqlDataReader sq2 = com.ExecuteReader();

        while (sq2.Read())
        {
            if ((Convert.ToString(sq2[0]) == "") || (Convert.ToString(sq2[1]) == ""))
            {
                nothing++;
                continue;
            }

            int n1 = Convert.ToInt32(Math.Round(Convert.ToDouble(sq2[0])));
            int n2 = Convert.ToInt32(Math.Round(100 * Convert.ToDouble(sq2[1])));
            if (n1 >= 65 && n2 >= 65)
            {
                tp++;
            }
            else if (n1 < 65 && n2 < 65)
            {
                tn++;
            }
            else if (n1 >= 65 && n2 < 65)
            {
                fn++;
            }
            else if (n1 < 65 && n2 >= 65)
            {
                fp++;
            }
            else
            {
                nothing++;
            }
        }
        Label1.Text = tp.ToString();
        Label2.Text = tn.ToString();
        Label3.Text = fn.ToString();
        Label4.Text = fp.ToString();
        Label5.Text = nothing.ToString();
        sq2.Close();

        com2 = new SqlCommand("Select pos_score,genres from Movies",con);
        SqlDataReader sq3 = com2.ExecuteReader();
        while (sq3.Read())
        {
            if (Convert.ToString(sq3[0]) == "" || Convert.ToString(sq3[1]) == "")
            {
                nothing2++;
                continue;
            }
            foreach (string s in Convert.ToString(sq3[1]).Split(new String[] { "***" }, StringSplitOptions.RemoveEmptyEntries))
            {
                string t = s.Replace(",", "");
                int n3 = Convert.ToInt32(Math.Round(100 * Convert.ToDouble(sq3[0])));
                if (dict.ContainsKey(t))
                {
                    dict[t]++;
                }
                else
                {
                    dict.Add(t, 1);
                }
                if (n3>=50)
                {
                    if (dict2.ContainsKey(t))
                    {
                        dict2[t]++;
                    }
                    else
                    {
                        dict2.Add(t, 1);
                    }
                }
                else
                {
                    if (dict3.ContainsKey(t))
                    {
                        dict3[t]++;
                    }
                    else
                    {
                        dict3.Add(t, 1);
                    }
                }
            }
            
        }
        foreach (KeyValuePair<string, int> total in dict)
        {

            Label6.Text += total.Key + "=" + total.Value + "<br />";
        }
        foreach (KeyValuePair<string, int> pos in dict2)
        {

            Label7.Text += pos.Key + "=" + pos.Value + "<br />";
        }
        foreach (KeyValuePair<string, int> neg in dict3)
        {

            Label8.Text += neg.Key + "=" + neg.Value + "<br />";
        }
        Label9.Text += nothing2;
    }
}