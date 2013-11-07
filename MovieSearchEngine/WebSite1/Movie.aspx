<%@ Page Async="true" Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeFile="Movie.aspx.cs" Inherits="Movie" MaintainScrollPositionOnPostback="true" %>

<%@ Register Assembly="System.Web.DataVisualization, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35" Namespace="System.Web.UI.DataVisualization.Charting" TagPrefix="asp" %>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <div>
        <asp:ImageButton ID="ImageButton1" runat="server"
            ImageUrl="img/like2.jpg" Height="50px" Width="120px"
            title="tell us if you like it" ImageAlign="Right" OnClick="ImageButton1_Click" />

    </div>

    <h2>
        <asp:Label ID="MovieName" runat="server" Text="Label"></asp:Label>
    </h2>
    <br />
    <br />
    <br />
    <br />
    <asp:Chart ID="Chart1" runat="server" Height="200px"
        Palette="Chocolate" Width="250px">
        <Series>
            <asp:Series ChartType="Pie"
                CustomProperties="PieLineColor=BlanchedAlmond, PieDrawingStyle=SoftEdge"
                Legend="Legend1" Name="Series1">
            </asp:Series>
        </Series>
        <ChartAreas>
            <asp:ChartArea Name="ChartArea1" BackColor="#333333">
                <Area3DStyle Enable3D="True" />
            </asp:ChartArea>
        </ChartAreas>

        <BorderSkin BackColor="BurlyWood" BackGradientStyle="HorizontalCenter"
            BackSecondaryColor="Blue" SkinStyle="Emboss" />
    </asp:Chart>
    <asp:Image ID="m" runat="server" ImageAlign="Right" Height="500px" Width="400px" />

    <br />
    <br />
    <br />

    <asp:Label ID="Label3" runat="server" Text="Label" ForeColor="#0066ff"><b>Movie Information : </b></asp:Label><asp:Label ID="Movieinfo" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label2" runat="server" Text="Label" ForeColor="#0066ff"><b>Rotten Tomatos Score : </b></asp:Label><asp:Label ID="Score" runat="server" Text="Label" ForeColor="#ffffcc" Font-Bold="true"></asp:Label><br />
    <asp:Label ID="Label4" runat="server" Text="Label" ForeColor="#0066ff"><b>Duation : </b></asp:Label><asp:Label ID="Duration" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label5" runat="server" Text="Label" ForeColor="#0066ff"><b>Director(s) : </b></asp:Label><asp:Label ID="Director" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label6" runat="server" Text="Label" ForeColor="#0066ff"><b>Writer(s) : </b></asp:Label><asp:Label ID="Writer" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label7" runat="server" Text="Label" ForeColor="#0066ff"><b>Theatre Release : </b></asp:Label><asp:Label ID="Theatre" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label8" runat="server" Text="Label" ForeColor="#0066ff"><b>Dvd Release : </b></asp:Label><asp:Label ID="Dvd" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label9" runat="server" Text="Label" ForeColor="#0066ff"><b>Box Office Collection : </b></asp:Label><asp:Label ID="Bo" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label10" runat="server" Text="Label" ForeColor="#0066ff"><b>Production : </b></asp:Label><asp:Label ID="Production" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label11" runat="server" Text="Label" ForeColor="#0066ff"><b>Genres : </b></asp:Label><br />
    <asp:BulletedList ID="Genre" runat="server" ForeColor="#ffffcc">
    </asp:BulletedList>
    <asp:Label ID="Label13" runat="server" Text="Label" ForeColor="#0066ff"><b>Actors : </b></asp:Label><asp:Label ID="Actors" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
    <asp:Label ID="Label14" runat="server" Text="Label" ForeColor="#0066ff"><b>Reviews : </b></asp:Label><br />
    <asp:BulletedList ID="Review" runat="server" ForeColor="#ffffcc">
    </asp:BulletedList>
    <asp:Label ID="Label15" runat="server" Text="Label" ForeColor="#0066ff"><h3><b>Video Reviews : </b></h3></asp:Label><br />
    <asp:Label ID="results" runat="server" Text="Label"></asp:Label>
    <br />
    <br />
    <div>
        <h2>
            <asp:Label ID="Label1" runat="server" Text="Label" ForeColor="#ffffcc">What do you think about the movie ?</asp:Label>

        </h2>

    </div>
    <div>

        <asp:TextBox ID="TextBox1" runat="server" Rows="7" TextMode="MultiLine" ToolTip="Enter your review here" Width="503px" Wrap="False"></asp:TextBox>
        <asp:Button ID="SubmitButton" runat="server" Text="Submit" BackColor="#0066FF" BorderStyle="Solid" ForeColor="#FFFFCC" Font-Bold="True" Font-Overline="False" OnClick="SubmitButton_Click" ToolTip="Submit your review" Width="114px" />

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

    </div>
    <br />
    <div style="margin-left: 120px">
        <asp:Label ID="class1" runat="server" Font-Bold="True" ForeColor="White"></asp:Label>
        &nbsp;&nbsp;&nbsp;&nbsp;
            <asp:TextBox ID="value1" runat="server" Width="94px" Font-Bold="True" Visible="False"></asp:TextBox>
        <br />
        <asp:Label ID="class2" runat="server" Font-Bold="True" ForeColor="White"></asp:Label>
        &nbsp;&nbsp;&nbsp;&nbsp;
            <asp:TextBox ID="value2" runat="server" Width="94px" Font-Bold="True" Visible="False"></asp:TextBox>
    </div>
    <div>
        <h2>
            <asp:Label ID="Label12" runat="server" Text="Label" ForeColor="#ffffcc">Analysis on Youtube Videos</asp:Label>

        </h2>
        Click the 'Get Captions' button below and enter the youtube link from above on which you want to perform sentiment analysis.
            <br />
        <br />
        <asp:Button ID="videoReview" runat="server" Text="Get Captions" BackColor="#0066FF" BorderStyle="Solid" ForeColor="#FFFFCC" Font-Bold="True" Font-Overline="False" OnClick="videoReview_Click" ToolTip="Click and enter URL" Width="114px" />

        <br />
        <br />

    </div>
    <div>
        Enter the Youtube link in the textbox and Click the 'Analyze' button to perform Sentiment analyis on the video.

        <br />

        <br />
        <br />
        <asp:TextBox ID="TextBox2" runat="server" Width="244px"></asp:TextBox>

        <asp:Button ID="Analyze" runat="server" Text="Analyze" BackColor="#0066FF" BorderStyle="Solid" ForeColor="#FFFFCC" Font-Bold="True" Font-Overline="False" OnClick="Analyze_Click" ToolTip="Sentiment Analyis" Width="114px" />
        <br />
        <br />

        <asp:TextBox ID="TextBox3" runat="server" Rows="7" TextMode="MultiLine" ToolTip="Enter your review here" Width="503px" Wrap="False"></asp:TextBox>
        <div style="margin-left: 120px">
            <asp:Label ID="class3" runat="server" Font-Bold="True" ForeColor="White"></asp:Label>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <asp:TextBox ID="value3" runat="server" Width="94px" Font-Bold="True" Visible="False"></asp:TextBox>
            <br />
            <asp:Label ID="class4" runat="server" Font-Bold="True" ForeColor="White"></asp:Label>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <asp:TextBox ID="value4" runat="server" Width="94px" Font-Bold="True" Visible="False"></asp:TextBox>
        </div>
        <br />
    </div>
    <br />



</asp:Content>

