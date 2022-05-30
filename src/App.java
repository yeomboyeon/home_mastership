import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Article.Article;
import Article.Member;
import Controller.ArticleController;
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
			if (command.equals("member join")) {
				memberController.doJoin();
			} 
			if (command.equals("article write")) {
				articleController.doWrite();
	
			} else if (command.startsWith("article list")) {
				articleController.showlist(command);

			} else if (command.startsWith("article detail ")) {
				articleController.showdatail(command);
				
			} else if (command.startsWith("article modify ")) {
				articleController.showmodify(command);
				
			} else if (command.startsWith("article delete ")) {
				articleController.showdelete(command);
				
			} else {
				System.out.println("존재하지 않는 명령어 입니다");
			}
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