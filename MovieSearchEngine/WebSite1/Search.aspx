﻿<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Search.aspx.cs" Inherits="Search" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" Runat="Server">
    <script src="Scripts/jquery-1.9.1.js" type="text/javascript"></script>
    <script src="Scripts/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
    <link href="Styles/jquery-ui-1.10.3.custom.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        $(function () {
            $('#<%= search.ClientID %>').autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "Autocomplete.asmx/GetNames",
                        data: "{ 'name1': '" + request.term + "' }",
                        type: "POST",
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        success: function (data) {
                            response(data.d);
                        },
                        error: function (result) {
                            alert('There is a problem processing your request');
                        }
                    });
                },
                minLength: 0
            });
        });
    </script>
    <h2 style = "display; height: 38px;" align="left" class="mainbar">Select the search criteria and enter your query
    </h2>
    <asp:DropDownList ID="categoryChooser" runat="server" CssClass="active" 
        ForeColor="#3366FF">
        <asp:ListItem Selected="True">name</asp:ListItem>
        <asp:ListItem>MovieInfo</asp:ListItem>
        <asp:ListItem>genres</asp:ListItem>
        <asp:ListItem>director</asp:ListItem>
        <asp:ListItem>writer</asp:ListItem>
        <asp:ListItem>Production</asp:ListItem>
        <asp:ListItem>writer</asp:ListItem>
        <asp:ListItem>actors</asp:ListItem>
    </asp:DropDownList>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <asp:TextBox ID="search" runat="server" BackColor="White" BorderColor="#99CCFF" 
        BorderStyle="Groove" CssClass="gadget" ForeColor="Black" Width="359px"></asp:TextBox>
        <asp:Button ID="Button2" runat="server" onclick="Button2_Click" Text="Search" Font-Bold="true"/>
        <br />
    <br />
    <br />
    <div>

        <asp:Label ID="disp" runat="server" Text=""></asp:Label></div>
</asp:Content>

