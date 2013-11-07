using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;

public partial class Worst_Movies : System.Web.UI.Page
{
    string connStr = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com;

    protected void Page_Load(object sender, EventArgs e)
    {
        SqlConnection con = new SqlConnection(connStr);
        con.Open();
        int i = 0;
        string m = "<table width=\"100%\"><tr>";
        com = new SqlCommand("Select id,imageLink,name,pos_score from Movies order by pos_score asc", con);
        SqlDataReader sq2 = com.ExecuteReader();

        while (sq2.Read())
        {
            i++;
            if (i == 101)
                break;
            if (i % 2 == 0)
            {
                m += "<td width=\"25%\"><a href='Movie.aspx?id=" + sq2[0] + "'><img src=\"" + sq2[1] +"\" style=\"height:200px;width:165px;\" /></a></td><td width=\"25%\">Rank :" + i + "<br  />Name :" + sq2[2] + "<br />Score " + Convert.ToInt32(Math.Round(100 * Convert.ToDouble(sq2[3]))) + "   </td>";
                m += "</tr><tr>";
            }
            else
            {
                m += "<td width=\"25%\"><a href='Movie.aspx?id=" + sq2[0] + "'><img src=\"" + sq2[1] + "\" style=\"height:200px;width:165px;\" /></a></td><td width=\"25%\">Rank :" + i + "<br  />Name :" + sq2[2] + "<br />Score " + Convert.ToInt32(Math.Round(100 * Convert.ToDouble(sq2[3]))) + "   </td>";
            }


        }
        m += "</tr></table>";
        sq2.Close();
        Label1.Text = m;
    }
}