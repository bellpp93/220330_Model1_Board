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
		} // end getBoardList() ================================================
		
		// 게시글 상세 보기 메소드 구현
		public BoardDO getBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 getBoard() 기능 처리됨!");
			
			BoardDO board = null;
			
			try {
				conn = JDBCUtil.getConnection();
				
				// [중요] 해당 게시글의 조회수(cnt)를 1증가 시킨다.
				String UPDATE_CNT = "update board set cnt=cnt+1 where seq=?";
				pstmt = conn.prepareStatement(UPDATE_CNT);
				pstmt.setInt(1, boardDO.getSeq());
				pstmt.executeUpdate();  // DML 작업은 executeUpdate() 호출함!
				
				// 해당 게시글 select하여 가져오기
				String BOARD_GET = "select * from board where seq=?";
				pstmt = conn.prepareStatement(BOARD_GET);
				pstmt.setInt(1, boardDO.getSeq());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					board = new BoardDO();
					board.setSeq(rs.getInt("SEQ"));
					board.setTitle(rs.getString("TITLE"));
					board.setWriter(rs.getString("WRITER"));
					board.setContent(rs.getString("CONTENT"));
					board.setRegdate(rs.getDate("REGDATE"));
					board.setCnt(rs.getInt("CNT"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(rs, pstmt, conn);
			}
			return board;
		}  // end getBoard() ====================================================================

		// 새 게시글 등록 메소드 구현
		public void insertBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 insertBoard() 메소드 처리됨!");
			
			try {
				conn = JDBCUtil.getConnection();
				
				String BOARD_INSERT 
					= "insert into board(seq,title,writer,content) values((select nvl(max(seq),0)+1 from board),?,?,?)";
				pstmt = conn.prepareStatement(BOARD_INSERT);
				pstmt.setString(1, boardDO.getTitle());
				pstmt.setString(2, boardDO.getWriter());				
				pstmt.setString(3, boardDO.getContent());				
				
				pstmt.executeUpdate();  // board 테이블에 데이터 저장 완료!!
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(pstmt, conn);
			}
		}  // end insertBoard() ================================================================
		
		// 게시글 수정 처리 메소드 구현
		public void updateBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 updateBoard() 메소드 처리됨!");
			
			try {
				conn = JDBCUtil.getConnection();
				
				String BOARD_UPDATE 
					= "update board set title=?, content=? where seq=?";
				pstmt = conn.prepareStatement(BOARD_UPDATE);
				pstmt.setString(1, boardDO.getTitle());
				pstmt.setString(2, boardDO.getContent());
				pstmt.setInt(3, boardDO.getSeq());
				
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(pstmt, conn);
			}
		}  // end updateBoard() ================================================================
		
		// 게시글 삭제 처리 메소드 구현
		public void deleteBoard(BoardDO boardDO) {
			System.out.println("===> JDBC로 deleteBoard() 메소드 처리됨!");
			
			try {
				conn = JDBCUtil.getConnection();
				
				String DELETE_BOARD = "delete from board where seq=?";
				pstmt = conn.prepareStatement(DELETE_BOARD);
				pstmt.setInt(1, boardDO.getSeq());
				pstmt.executeUpdate();  // seq 조건에 맞는 레코드 삭제 처리
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				JDBCUtil.close(pstmt, conn);
			}
		}
}
