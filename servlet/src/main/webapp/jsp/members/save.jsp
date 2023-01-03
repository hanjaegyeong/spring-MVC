<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // %표시 넣으면 자바 코드 넣을 수 있음
    // 폼 저장 후 해당 데이터 보여주는 페이지
    // request, response는 그냥 사용 가능 jsp도 서블릿으로 자동 변환되기 때문
    MemberRepository memberRepository = MemberRepository.getInstance();

    System.out.println("MemberSaveServlet.service");
    String username = request.getParameter("username"); //GET의 쿼리스트링, 일반 POST, From 등 다 꺼낼 수 잇음
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="index.html"></a>
</body>
</html>
