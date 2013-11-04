using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Data;
using System.Data.SqlClient;
using System.Web.UI.WebControls;
using System.Configuration;

public partial class ScoreBuilder : System.Web.UI.Page
{
    string connStr = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
    SqlCommand com;
    int i = 0;
    protected void Page_Load(object sender, EventArgs e)
    {
        
    }
}