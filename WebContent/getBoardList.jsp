<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp" %>
<!-- 추가 -->
<%@ page import="board.BoardDO" %>
<%@ page import="board.BoardDAO" %>
<%@ page import="java.util.List" %>

<!-- JSTL 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");

	String searchField = "";
	String searchText = "";
	
	if(request.getParameter("searchCondition") != "" 
			&& request.getParameter("searchKeyword") != "") {
		searchField = request.getParameter("searchCondition");
		searchText = request.getParameter("searchKeyword");
	}
	// BoardDAO 클래스 객체 생성
	BoardDAO boardDAO = new BoardDAO();
	List<BoardDO> boardList 
			= boardDAO.getBoardList(searchField, searchText);
	
	request.setAttribute("boardList", boardList);
	
	int totalList = boardList.size();
	request.setAttribute("totalList", totalList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 전체 목록 보기 페이지 => getBoardList.jsp</title>
</head>
<body>
	<div id="div_box">
		<h1>전체 게시글 목록 보기</h1>
		<h3>${IdKey}님 환영합니다.&nbsp;&nbsp;&nbsp;<a href="logout_proc.jsp">로그아웃</a></h3>
		<form name="boardListForm" method="POST" action="getBoardList.jsp">
			<p>총 게시글: ${totalList}건</p>
			<table border="1" cellpadding="0" cellspacing="0" width="700">
				<tr>
					<td align="right">
						<select name="searchCondition">
							<option value="TITLE">제목</option>
							<option value="WRITER">작성자</option>
						</select>
						<input type="text" name="searchKeyword"/>
						<input type="submit" value="검색"/>
					</td>
				</tr>
			</table>
		</form>
			<table border="1" cellpadding="0" cellspacing="0" width="700">
				<tr>
					<th bgcolor="orange" width="100">번호</th>
					<th bgcolor="orange" width="200">제목</th>
					<th bgcolor="orange" width="150">작성자</th>
					<th bgcolor="orange" width="150">등록일</th>
					<th bgcolor="orange" width="100">조회수</th>
				</tr>
				<%-- 주석 처리 
				<% for(BoardDO board : boardList) { %>  <!-- 자바의 확장된 for문 -->
					<tr>
						<td align="center"><%= board.getSeq() %></td>
						<td align="left">
							<a href="getBoard.jsp?seq=<%=board.getSeq() %>">
								<%= board.getTitle() %></a>
						</td>
						<td align="center"><%= board.getWriter() %></td>
						<td align="center"><%= board.getRegdate() %></td>
						<td align="center"><%= board.getCnt() %></td>
					</tr>	
				<% } %>
				--%>
				
				<%-- 위의 코드를 표현언어와 JSTL을 적용하여 소스 변경 --%>
				<c:forEach var="board" items="${boardList}">
					<tr>
						<td align="center">${board.seq}</td>
						<td align="left">
							<a href="getBoard.jsp?seq=${board.seq}">${board.title}</a>
						</td>
						<td align="center">${board.writer}</td>
						<td align="center">${board.regdate}</td>
						<td align="center">${board.cnt}</td>
					</tr>
				</c:forEach>
			</table>
			<br>
			<a href="insertBoard.jsp">새 게시글 등록</a>&nbsp;&nbsp;&nbsp;
			<a href="getBoardList.jsp">전체 게시글 목록 보기</a>
	</div>
</body>
</html>