package board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardDAO {
	// DB 관련 참조 변수 선언
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		
		// 전체 게시글 목록 보여주기 메소드 구현
		public List<BoardDO> getBoardList(String searchField, String searchText) {
			
		}
}
