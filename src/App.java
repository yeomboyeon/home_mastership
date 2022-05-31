import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Article.Article;
import Article.Member;
import Controller.ArticleController;
import Controller.Controller;
import Controller.MemberController;
import YeomIT.Util;

public class App {
	private List<Article> articles;
	private List<Member> members;

	public App() {
		articles = new ArrayList<>();
		members = new ArrayList<>();
	}

	public void start() {
		System.out.println("===== 프로그램 시작 =====");
		makeTestData();
//		이전 및 생성 진행중
//		makeTestData(article);
//		makeTestData(member);

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc, members);
		ArticleController articleController = new ArticleController(sc, articles);

		while (true) {
			System.out.printf("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("system exit")) {
				break;
			}

// 위 함수까지는 읽고 나서 실행하도록 이곳에 작성
// 다시 한번 문자열을 쪼개어 검색하도록 한다.
			String[] commandBits = command.split(" "); // article list
// commandBits 의 배열개수가 1이라면 정상적인 명령어를 작성하지 않은걸로 한다.
			if (commandBits.length == 1) {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}
// controllerName은 명령어상 첫번째 배열의 이름
			String controllerName = commandBits[0]; // article
// actionMethodName은 명령어상 두번째 배열의 이름
			String actionMethodName = commandBits[1]; // list
// controller 변수에 null값 입력
			Controller controller = null;
// 만약에 controllerName이 article라면 controller 변수값에 articleController 입력된다.
			if (controllerName.equals("article")) {
				controller = articleController;
	// 위 사항이 아니고 controllerName이 member라면 controller 변수값에 memberController 입력된다.
			} else if (controllerName.equals("member")) {
				controller = memberController;
	// 위 사항 모두 아니라면 출력하고 con~~~~
			} else {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}	// doAction 이라는 함수를 생성하고 누르면 command, actionMethodName 인자를 불러와서 실행하는 함수
			controller.doAction(command, actionMethodName);

//			if (command.equals("member join")) {
//				memberController.doJoin();
//			}
//			if (command.equals("article write")) {
//				articleController.doWrite();
//
//			} else if (command.startsWith("article list")) {
//				articleController.showlist(command);
//
//			} else if (command.startsWith("article detail ")) {
//				articleController.showdetail(command);
//
//			} else if (command.startsWith("article modify ")) {
//				articleController.domodify(command);
//
//			} else if (command.startsWith("article delete ")) {
//				articleController.dodelete(command);
//
//			} else {
//				System.out.println("존재하지 않는 명령어 입니다");
//			}
		}

		sc.close();

		System.out.println("===== 프로그램 끝 =====");
	}

	private void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다.");
		articles.add(new Article(1, Util.getNowDateStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateStr(), "제목3", "내용3", 33));

	}
}
// 글 삭제 후 다시 생성시 번호 중복 점검 필요