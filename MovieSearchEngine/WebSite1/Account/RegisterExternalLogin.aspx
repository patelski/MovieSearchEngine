<%@ Page Language="C#" MasterPageFile="~/Site.master" AutoEventWireup="true" CodeFile="RegisterExternalLogin.aspx.cs" Inherits="Account_RegisterExternalLogin" Async="true" %>
<asp:Content runat="server" ID="BodyContent" ContentPlaceHolderID="MainContent">
    <header>
        <h1>Register with your <%: ProviderName %> account</h1>
    </header>

    <asp:ValidationSummary ID="ValidationSummary1" runat="server" ShowModelStateErrors="true" CssClass="text-error" />
    <asp:PlaceHolder ID="PlaceHolder1" runat="server" >
        <fieldset class="form-horizontal">
            <legend>Association Form</legend>
            <p class="text-info">
                You've authenticated with <strong><%: ProviderName %></strong>. Please enter a user name below for the current site
                and click the Log in button.
            </p>

            <div class="control-group">
                <asp:Label ID="Label1" runat="server" AssociatedControlID="userName" CssClass="control-label">User name</asp:Label>
                <div class="controls">
                    <asp:TextBox runat="server" ID="userName" />
                    <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="userName"
                        Display="Dynamic" CssClass="text-error" ErrorMessage="User name is required" />
                    <asp:ModelErrorMessage ID="ModelErrorMessage1" runat="server" ModelStateKey="UserName" CssClass="text-error" />
                </div>
            </div>

            <div class="form-actions no-color">
                <asp:Button ID="Button1" runat="server" Text="Log in" CssClass="btn" OnClick="LogIn_Click" />
            </div>
        </fieldset>
    </asp:PlaceHolder>
</asp:Content>