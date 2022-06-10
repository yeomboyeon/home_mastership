public class Main {
	public static void main(String[] args) {
		new App().start();
	}
}

// 게시판 만들기 전체 구조틀 (MVC) Model View Controller (데이터와 비즈니스 로직 관리, 레이아웃과 화면 처리, 명령을 모델과 뷰 부분으로 라우팅)
 
// 고객 -> Main -> App -> ArticleController -> ArticleService -> ArticleDao -> articles
//                        MemberController     MemberService     MemberDao     members
//(사용자)(시작) (라우팅)   (고객응대)           (실무팀)        (창고지기)     (창고)
