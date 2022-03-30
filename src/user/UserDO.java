package user;

/*
 * MVC(Model-View-Controller) 패턴으로 개발 시
 * XxxDO 클래스(Xxx는 테이블명)
 * DO(Data Object) 또는 DTO(Data Transfer Object)
 * XxxDo 클래스는 계층간 데이터 교환을 위한 자바빈즈를 의미한다.
 * 계층간의 의미는 View, Controller 등을 말하며 각 계층간 데이터 교환을 위한 객체를 의미한다
 */

public class UserDO {
	
	// MVC패턴의 Model 파트
	// 멤버 필드(프로퍼티, 중간저장소)
	private String id;			// 아이디  test
	private String password;	// 패스워드  test1234
	private String name;		// 이름
	private String role;		// 직무
	
	// 멤버 필드 하나당 getter, setter 메소드
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
}
