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

public partial class UserMovies : System.Web.UI.Page
{
    string conn = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com, com2;
    int i = 0;
    Dictionary<string, int> dict = new Dictionary<string, int>();
    protected void Page_Load(object sender, EventArgs e)
    {
        List<int> movies = new List<int>();
        int max = 0;
        List<string> max1 = new List<string>();
        SqlConnection con = new SqlConnection(conn);
        con.Open();
        com = new SqlCommand("Select id from UserLikeMovies where UserName ='" + @User.Identity.Name + "'", con);
        SqlDataReader rdr = com.ExecuteReader();
        if (rdr.HasRows)
        {
            while (rdr.Read())
            {
                movies.Add(Convert.ToInt32(rdr[0].ToString()));
            }
            rdr.Close();

            string m = "<table width=\"100%\"><tr>";
            foreach (int id in movies)
            {
                com2 = new SqlCommand("Select genres from Movies where id =" + id, con);
                com2.ExecuteNonQuery();
                string genres = (string)com2.ExecuteScalar();
                foreach (string s in genres.Split(new String[] { "***" }, StringSplitOptions.RemoveEmptyEntries))
                {
                    string t = s.Replace(",", "");
                    if (dict.ContainsKey(t))
                    {
                        dict[t]++;
                    }
                    else
                    {
                        dict.Add(t, 1);
                    }
                }


                com = new SqlCommand("Select imageLink from Movies where id =" + id, con);
                SqlDataReader rdr2 = com.ExecuteReader();

                while (rdr2.Read())
                {
                    i++;
                    if (i % 4 == 0)
                    {
                        m += "<td width=\"23%\"><a href='Movie.aspx?id=" + id + "'><img src=\"" + rdr2[0] + "\" style=\"height:200px;width:165px;\" /></a></td>";
                        m += "</tr><tr>";
                    }
                    else
                    {
                        m += "<td width=\"23%\"><a href='Movie.aspx?id=" + id + "'><img src=\"" + rdr2[0] + "\" style=\"height:200px;width:165px;\" /></a></td>";
                    }

                }
                rdr2.Close();

            }
            m += "</tr></table>";
            this.Label1.Text = m;

            max = dict.ElementAt(0).Value;
            for (int k = 1; k < dict.Count - 1; k++)
            {
                if (dict.ElementAt(k).Value > max)
                {
                    max = dict.ElementAt(k).Value;
                }

            }
            for (int k = 0; k < dict.Count - 1; k++)
            {
                if (dict.ElementAt(k).Value == max)
                {
                    max1.Add(dict.ElementAt(k).Key);
                }
            }

            Chart1.Series[0].Points.DataBindXY(dict.Keys, dict.Values);
            Chart1.Series[0]["PieLabelStyle"] = "Outside";
            Chart1.Legends.Add("Legend1");
            Chart1.Legends[0].Enabled = true;
            Chart1.Legends[0].Docking = Docking.Bottom;
            Chart1.Legends[0].Alignment = System.Drawing.StringAlignment.Center;
            Chart1.Series[0].LegendText = "#PERCENT{P2}";
            Chart1.DataManipulator.Sort(PointSortOrder.Descending, Chart1.Series[0]);

            foreach (string c in max1)
            {
                Label2.Text += c + ",";

            }

            foreach (string c in max1)
            {
                int j = 0;
                string m2 = "<h3>" + c + "</h3><br />" + "<table width=\"100%\"><tr>";
                com = new SqlCommand("Select id,imageLink,name,pos_score from Movies where genres like '%" + c + "%' order by pos_score desc", con);
                SqlDataReader sq2 = com.ExecuteReader();

                while (sq2.Read())
                {
                    j++;
                    if (j == 21)
                        break;
                    if (j % 2 == 0)
                    {
                        m2 += "<td width=\"25%\"><a href='Movie.aspx?id=" + sq2[0] + "'><img src=\"" + sq2[1] + "\" style=\"height:200px;width:165px;\" /></a></td><td width=\"25%\">Rank :" + j + "<br  />Name :" + sq2[2] + "<br />Score " + Convert.ToInt32(Math.Round(100 * Convert.ToDouble(sq2[3]))) + "   </td>";
                        m2 += "</tr><tr>";
                    }
                    else
                    {
                        m2 += "<td width=\"25%\"><a href='Movie.aspx?id=" + sq2[0] + "'><img src=\"" + sq2[1] + "\" style=\"height:200px;width:165px;\" /></a></td><td width=\"25%\">Rank :" + j + "<br  />Name :" + sq2[2] + "<br />Score " + Convert.ToInt32(Math.Round(100 * Convert.ToDouble(sq2[3]))) + "   </td>";
                    }


                }
                sq2.Close();
                m2 += "</tr></table>";
                this.Label3.Text += m2;

            }

            con.Close();
        }
    }
}
