import java.util.Scanner;

import Controller.ArticleController;
import Controller.Controller;
import Controller.MemberController;

public class App {

	public App() {

	}

	public void start() {
		System.out.println("===== 프로그램 시작 =====");

		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(sc);
		ArticleController articleController = new ArticleController(sc);
		articleController.makeTestData();
		memberController.makeTestData();

		while (true) {
			System.out.printf("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("help")) { // help 명령어 추가
				System.out.println("======= article 주요 기능 =======");
				System.out.println("article list : 글 목록 기능");
				System.out.println("article write : 글 쓰기 기능");
				System.out.println("article delete : 글 삭제 기능");
				System.out.println("article detail : 글 상세보기 기능");
				System.out.printf("article modify : 글 수정 기능\n\n");
				System.out.println("======= member 주요 기능 =======");
				System.out.println("member join : 회원가입 기능");
				System.out.println("member login : 회원 로그인 기능");
				System.out.printf("member logout : 회원 로그아웃 기능\n\n");
				System.out.println("======= 기타 기능 =======");
				System.out.printf("system exit : 프로그램 종료 기능\n\n");
				continue;
			}

			if (command.equals("system exit")) {
				break;
			}

			String[] commandBits = command.split(" ");

			if (commandBits.length == 1) {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}

			String controllerName = commandBits[0];

			String actionMethodName = commandBits[1];

			Controller controller = null;

			if (controllerName.equals("article")) {
				controller = articleController;

			} else if (controllerName.equals("member")) {
				controller = memberController;

			} else {
				System.out.println("존재하지 않는 명령어입니다.");
				continue;
			}

			// 보완중
			String actionName = controllerName + "/" + actionMethodName;

			switch (actionName) {
			case "article/write":
			case "article/delete":
			case "article/modify":
			case "member/logout":
				if (Controller.islogined() == false) {
					System.out.println("로그인 후 사용해주세요");
					continue;
				}
				break;
			}
			switch (actionName) {
			case "member/login":
			case "member/join":
				if (Controller.islogined()) {
					System.out.println("로그아웃 후 사용하여 주세요.");
					continue;
				}
				break;
			}
			controller.doAction(command, actionMethodName);

		}

		sc.close();

		System.out.println("===== 프로그램 끝 =====");
	}

}