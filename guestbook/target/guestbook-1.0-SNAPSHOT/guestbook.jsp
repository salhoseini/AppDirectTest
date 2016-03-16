<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%@ page import="com.example.guestbook.Player" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
</head>

<body>

<%
	String logoutUrl = "www.appdirect.com/applogout?openid=" + request.getParameter("openid");
	String userEmail = request.getParameter("useremail");
	System.out.println(userEmail);
    if (userEmail != null) {
%>

<p>Hello, <%=userEmail%>(You can
	<a href="<%=logoutUrl%>">sign out</a>.)</p>
<%
    } 
      List<Player> players = ObjectifyService.ofy()
          .load()
          .type(Player.class) // We want only Greetings
          .list();
    if (players.isEmpty()) {
%>
<p>No players have bought tickets yet</p>
<%
    } else {
%>
<p>Players stats :.</p>
<%
      // Look at all of our greetings
	  String dataPrinter = null;
	  
        for (Player player : players) {
			int ticketNo = 0;
            String firstName;
            if (player.getPlayerFirstName() != null) {
                firstName = player.getPlayerFirstName();
                userEmail = player.getPlayerEmail();
                System.out.println(userEmail);
				ticketNo = player.getTicketNumber();
				dataPrinter = firstName + " - " + userEmail + "bought ticket number : " + ticketNo + "\n";
            }
			
%>
<p><b><%=dataPrinter%></b></p>
<%
        }
    }
%>

<form action="/sign" method="post">
    <div><input type="submit" value="Buy Ticket"/></div>
    <input type="hidden" name="userEmail" value=<%=userEmail%> />
</form>

</body>
</html>