using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data;
using System.Data.SqlClient;
using System.Configuration;

/// <summary>
/// Summary description for WebService
/// </summary>
[WebService(Namespace = "http://tempuri.org/")]
[WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
// To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
[System.ComponentModel.ToolboxItem(false)]
[System.Web.Script.Services.ScriptService]

public class Autocomplete : System.Web.Services.WebService
{
    [WebMethod]
    public List<string> GetNames(string name1)
    {
        List<string> Names = new List<string>();
        string connStr = ConfigurationManager.ConnectionStrings["moviesConnection"].ConnectionString;
        using (SqlConnection con = new SqlConnection(connStr))
        {
            SqlCommand cmd = new SqlCommand("AutoProc", con);
            cmd.CommandType = CommandType.StoredProcedure;

            SqlParameter parameter = new SqlParameter("@name1", name1);
            cmd.Parameters.Add(parameter);
            con.Open();
            SqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                Names.Add(reader["Name"].ToString());
            }
        }

        return Names;
    }
}