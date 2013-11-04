<%@ Page Async="true" Language="C#" AutoEventWireup="true" MasterPageFile="~/Site.Master" CodeFile="Movie.aspx.cs" Inherits="Movie" MaintainScrollPositionOnPostback="true"%>

<asp:Content ID="BodyContent" ContentPlaceHolderID="MainContent" runat="server">

    <div>

        <asp:ImageButton ID="ImageButton1" runat="server" 
            ImageUrl="img/like.jpg" Height=120px Width=120px 
            title="tell us if you like it" ImageAlign="Right" onclick="ImageButton1_Click"/>
            </div>
        <h1>
            <asp:Label ID="MovieName" runat="server" Text="Label" ForeColor="#0066ff"></asp:Label>
        </h1>
        <br />
        <br />
        <br /><br />
            <asp:Image ID="m" runat="server" ImageAlign="Right" Height="500px" Width="400px" /><br />
        
        <asp:Label ID="Label2" runat="server" Text="Label" ForeColor="#0066ff"><b>Score : </b></asp:Label><asp:Label ID="Score" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
        <asp:Label ID="Label3" runat="server" Text="Label" ForeColor="#0066ff"><b>Movie Information : </b></asp:Label><asp:Label ID="Movieinfo" runat="server" Text="Label" ForeColor="#ffffcc"></asp:Label><br />
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
    <asp:Label ID="Label15" runat="server" Text="Label" ForeColor="#0066ff"><b>Video Reviews : </b></asp:Label><br />
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
                <asp:Label ID="Label12" runat="server" Text="Label" ForeColor="#ffffcc">Audio Input</asp:Label>

            </h2>

        </div>
           <div> <asp:TextBox ID="TextBox2" runat="server" Rows="7" TextMode="MultiLine" ToolTip="Enter your review here" Width="500px" Wrap="False"></asp:TextBox>
                <button class="btn btn-primary" onclick="startRecording(this);">Record</button>
        <button class="btn btn-warning" onclick="stopRecording(this);" disabled>Stop</button>
         <button class="btn btn-primary" onclick="startSequencer(this);">Play</button>
        <button class="btn btn-warning" onclick="stopSequencer(this);" disabled>Stop</button>  

        </div>
         <table id="recordingslist">
        <tr>
          
          <th>
            <label for='NumOsc'>Number of Generators: <span class='controlVal'></span></label>
            <input class='control' id='NumOsc' type='range' min='1' max='40' value='40'>
          </th>
          <th>
          </th>
        </tr>
      </table> 
    <script src="sound/jquery/js/jquery-1.7.2.js"></script>
    <script src="sound/bootstrap/js/bootstrap-transition.js"></script>
    <script src="sound/bootstrap/js/bootstrap-alert.js"></script>
    <script src="sound/bootstrap/js/bootstrap-modal.js"></script>
    <script src="sound/bootstrap/js/bootstrap-dropdown.js"></script>
    <script src="sound/bootstrap/js/bootstrap-scrollspy.js"></script>
    <script src="sound/bootstrap/js/bootstrap-tab.js"></script>
    <script src="sound/bootstrap/js/bootstrap-tooltip.js"></script>
    <script src="sound/bootstrap/js/bootstrap-popover.js"></script>
    <script src="sound/bootstrap/js/bootstrap-button.js"></script>
    <script src="sound/bootstrap/js/bootstrap-collapse.js"></script>
    <script src="sound/bootstrap/js/bootstrap-carousel.js"></script>
    <script src="sound/bootstrap/js/bootstrap-typeahead.js"></script>


    <script type="text/javascript" src="sound/app/js/ACFIRFilter.js"></script>
    <script type="text/javascript" src="sound/app/js/ACAAFilter.js"></script> 
    <script type="text/javascript" src="sound/app/js/ACSpectrum.js"></script>    
    <script type="text/javascript" src="sound/app/js/ACFFT.js"></script>
    <script type="text/javascript" src="sound/app/js/SpectrumWorker.js"></script>
    <script type="text/javascript" src="sound/app/js/SpectrumDisplay.js"></script>
    <script type="text/javascript" src="sound/app/js/audioplayback.js"></script>
    <script type="text/javascript" src="sound/app/js/filedropbox.js"></script>
    <script type="text/javascript" src="sound/app/js/fft.js"></script>
    <script type="text/javascript" src="sound/app/js/audioLayerControl.js"></script>
    <script type="text/javascript" src="sound/app/js/audiosequence.js"></script>
    <script type="text/javascript" src="sound/app/js/AudioSequenceEditor.js"></script>
    <script type="text/javascript" src="sound/app/js/mathutilities.js"></script>
    <script type="text/javascript" src="sound/app/js/wavetrack.js"></script>
    <script type="text/javascript" src="sound/app/js/binarytoolkit.js"></script>
    <script type="text/javascript" src="sound/app/js/filesystemutility.js"></script>
    <script type="text/javascript" src="sound/app/js/editorapp.js"></script>

    <script src="sound/js/lib/recorder.js"></script>
    <script src="sound/js/recordLive.js"></script>
    <script src="sound/js/sequencer.js"></script>
    <script src="sound/js/drone.js"></script>

    <script type="text/javascript">
        $(window).load(function () {
            $('.editor.container').addClass('invisible');
            onDocumentLoaded();
        });
    </script>
</asp:Content>

