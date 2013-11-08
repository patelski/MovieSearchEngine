<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="UserMovies.aspx.cs" Inherits="UserMovies" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" runat="Server">

    <h2>Movies You Liked : </h2>
    <br />
    <div>
        <asp:Chart ID="Chart1" runat="server" Height="400px"
            Palette="EarthTones" Width="500px">
            <Series>
                <asp:Series ChartType="Pie"
                    CustomProperties="PieLineColor=Black, PieDrawingStyle=SoftEdge"
                    Legend="Legend1" Name="Series1" Color="White">
                </asp:Series>
            </Series>
            <ChartAreas>
                <asp:ChartArea Name="ChartArea1" BackColor="#C3690F">
                    <Area3DStyle Enable3D="True" />
                </asp:ChartArea>
            </ChartAreas>

            <BorderSkin BackColor="BurlyWood" BackGradientStyle="HorizontalCenter"
                BackSecondaryColor="Blue" SkinStyle="Emboss" />
        </asp:Chart>
        <h3>User Category : </h3>
        <asp:Label ID="Label2" runat="server" ForeColor="#0066ff"
            Text=""></asp:Label>
    </div>
    <br />
    <br />
    <asp:Label ID="Label1" runat="server"
        Text=""></asp:Label>
    <br />
    <br />
    <h3>Here are some recommendations, Best 20 movies in</h3>
    <br />
    <asp:Label ID="Label3" runat="server" ForeColor="#0066ff"
        Text="">
    </asp:Label>
</asp:Content>

