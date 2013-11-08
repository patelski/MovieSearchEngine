using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


public partial class _Default : Page
{
    static int i = 1;
    protected void Page_Load(object sender, EventArgs e)
    {

        if (!IsPostBack)
        {
            SetImageUrl();
        }
    }
    protected void Timer1_Tick(object sender, EventArgs e)
    {
        SetImageUrl();
    }

    private void SetImageUrl()
    {
        if (i == 20)
            i = 1;
        Image1.ImageUrl = "/img/" + i.ToString() + ".jpg";
        i++;
    }

}