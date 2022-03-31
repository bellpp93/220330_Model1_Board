package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import common.JDBCUtil;

public class BoardDAO {
	// DB 관련 참조 변수 선언
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		
		// 전체 게시글 목록 보여주기 메소드 구현
		public List<BoardDO> getBoardList(String searchField, String searchText) {
			System.out.println("===> getBoardList() 처리됨!");
			
			// [중요] 자바의 자료구조(가변 배열)
			List<BoardDO> boardList = new ArrayList<BoardDO>();  // 기본 10개 확보
			
			try {
				conn = JDBCUtil.getConnection();
				
				// [중요] 반드시 이해 필요!!
				String where = "";
				
				if(searchField != null && searchText != null) {
					where = "where "+searchField+" like '%"+searchText+"%'";
				}
				String Condition_SQL 
							= "select * from board "+where+" order by seq desc";
				pstmt = conn.prepareStatement(Condition_SQL);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					BoardDO board = new BoardDO();
					board.setSeq(rs.getInt("SEQ"));
					board.setTitle(rs.getString("TITLE"));
					board.setWriter(rs.getString("WRITER"));
					board.setContent(rs.getString("CONTENT"));
					board.setRegdate(rs.getDate("REGDATE"));
					board.setCnt(rs.getInt("CNT"));
					
					boardList.add(board);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			return boardList;
		}
}
