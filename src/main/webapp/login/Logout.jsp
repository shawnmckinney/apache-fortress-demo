<html>
<head><title>Wicket Project Sample for RBAC</title></head>
<body>
  <%session.invalidate();%>
  <%
  HttpServletResponse res = (HttpServletResponse) response;
  res.sendRedirect("/fortressdemo2");
  %>
</body>
</html>