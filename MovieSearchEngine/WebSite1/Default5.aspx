<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="Default5.aspx.cs" Inherits="Default5" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" Runat="Server">
    <div>
        Enter tag (ex: music):
        <asp:TextBox ID="tag" runat="server"></asp:TextBox>
        <asp:Button ID="search1" runat="server" OnClick="search1_Click" Text="Get by tag" />
        <br />
        <br />
        Enter search (ex: "big jump"):
        <asp:TextBox ID="search" runat="server"></asp:TextBox>
        <asp:Button ID="search2" runat="server" OnClick="search2_Click" Text="Search" /><br />
        <br />
        <input type="file" accept="image/*;capture=camera">
        <asp:Label ID="results" runat="server" Text="Label"></asp:Label></div>
</asp:Content>

