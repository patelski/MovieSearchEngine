<%@ Page Title="Home Page" Language="C#" MasterPageFile="~/Site.Master" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="asp" %>



<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">
    <div>
            <div><h2><img src="/img/logo.png" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="www.google.com" class="btn btn-primary btn-large">Search &raquo;</a></h2></div>
        

        <div><asp:UpdatePanel ID="UpdatePanel1" runat="server">

            <ContentTemplate>
                <asp:Timer ID="Timer1" runat="server" Interval="3000" OnTick="Timer1_Tick"></asp:Timer>
                <asp:Image ID="Image1" Height="600px" Width="1500px" runat="server" />
            </ContentTemplate>
        </asp:UpdatePanel></div>
        <div></div>
        </div>
        
      
        <div class="row">
            <div class="span4" style="color :#ffffcc;">
                <h2>Reviews</h2>
                <p>
                    Movie Reviewer lets you search your favourite movies by movie name, actors and genre. Apart from all the details about the movie, you also get reviews to get an idea about what critics think about it.
                </p>
                <p>
                    <a class="btn" href="http://go.microsoft.com/fwlink/?LinkId=301948">Learn more &raquo;</a>
                </p>
            </div>
            <div class="span4" style="color :#ffffcc;">
                <h2>Recommendations</h2>
                <p>
                    Our content based filtering component recommends you other movies based on the movie you are currently searching.
                </p>
                <p>
                    <a class="btn" href="http://go.microsoft.com/fwlink/?LinkId=301949">Learn more &raquo;</a>
                </p>
            </div>
            <div class="span4" style="color :#ffffcc;">
                <h2>Analysis</h2>
                <p>
                   Our sentiment analysis component associates each review with a score which determines the category of the review i.e. whether it is a positive or a negative review. 
                </p>
                <p>
                    <a class="btn" href="http://go.microsoft.com/fwlink/?LinkId=301948">Learn more &raquo;</a>
                </p>
            </div>
        
    </div>
</asp:Content>
