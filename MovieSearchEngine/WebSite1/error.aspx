<%@ Page Title="" Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="error.aspx.cs" Inherits="error" %>

<asp:Content ID="Content1" ContentPlaceHolderID="MainContent" Runat="Server">
   <div> <asp:Button ID="Button1" runat="server" Text="Button" OnClick="Button1_Click" /><br />
    true positive <asp:Label ID="Label1" runat="server" Text="Label"></asp:Label><br />
     true negative<asp:Label ID="Label2" runat="server" Text="Label"></asp:Label><br />
     false negative<asp:Label ID="Label3" runat="server" Text="Label"></asp:Label><br />
     false positive<asp:Label ID="Label4" runat="server" Text="Label"></asp:Label><br />
    <asp:Label ID="Label5" runat="server" Text="Label"></asp:Label><br /></div>
    <div>
Total
<asp:Label ID="Label6" runat="server" Text="Label"></asp:Label><br /><br />
 positive   <asp:Label ID="Label7" runat="server" Text="Label"></asp:Label><br />negative : <asp:Label ID="Label8" runat="server" Text="Label"></asp:Label>
     <br />Total :    <asp:Label ID="Label9" runat="server" Text="Label"></asp:Label>
    </div>
</asp:Content>

