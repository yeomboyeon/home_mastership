package Controller;

import java.util.List;
import java.util.Scanner;

import Article.Member;
import Container.Container;
import Service.MemberService;
import YeomIT.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private List<Member> members;
	private String command;
	private String actionMethodName;
	private MemberService memberService;

	public void doAction(String command, String actionMethodName) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		switch (actionMethodName) {
		case "join":
			doJoin();
			break;
		case "login":
			dologin();
			break;
		case "logout":
			dologout();
			break;
		default:
			System.out.println("존재하지 않는 명령어입니다.");
			break;
		}
	}

	private void dologout() {
		loginedMember = null;
		System.out.println("로그아웃 되었습니다.");
	}

	private void dologin() {
		System.out.printf("로그인 아이디 : ");
		String loginId = sc.nextLine();
		System.out.printf("로그인 비밀번호 : ");
		String loginPw = sc.nextLine();

		Member member = memberService.getMemberByLoginId(loginId); // service 에서 구현

		if (member == null) {
			System.out.println("해당 회원은 존재하지 않습니다.");
			return;
		}
		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호를 다시 확인해 주세요.");
			return;
		}
		loginedMember = member;
		System.out.printf("로그인 성공! %s님 환영합니다. \n", loginedMember.name);
	}

	public MemberController(Scanner sc) {
		this.sc = sc;
		memberService = Container.memberService; // service에서 구현
	}

	private void doJoin() {
		int id = Container.memberDao.getNewId();
		String regDate = Util.getNowDateStr();

		String loginId = null;
		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine().trim();

			if (loginId == "") {
				System.out.printf("아이디를 입력해주세요.\n");
				continue;
			}

			if (memberService.isJoinableLoginId(loginId) == false) { // service 에서 구현
				System.out.printf("%s는(은) 이미 사용중인 아이디입니다.\n", loginId);
				continue;
			}

			break;
		}

		String loginPw = null;
		String loginPwConfirm = null;

		while (true) {
			System.out.printf("로그인 비밀번호 : ");
			loginPw = sc.nextLine();

			if (loginPw == "") {
				System.out.printf("비밀번호를 입력해주세요.\n");
				continue;
			}

			System.out.printf("로그인 비밀번호 확인: ");
			loginPwConfirm = sc.nextLine();

			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 다시 입력해주세요");
				continue;
			}

			break;
		}

		String name = null;

		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine();
			if (name == "") {
				System.out.printf("이름을 입력해주세요.\n");
				continue;
			}

			break;
		}

		Member member = new Member(id, regDate, loginId, loginPw, name);
		Container.memberService.add(member); // service로 구현

		System.out.printf("%d번 회원이 가입하였습니다\n", id);

	}
	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		Container.memberDao.add(new Member(1, Util.getNowDateStr(), "admin", "admin", "관리자"));
		Container.memberDao.add(new Member(2, Util.getNowDateStr(), "test1", "test1", "유저1"));
		Container.memberDao.add(new Member(3, Util.getNowDateStr(), "test2", "test2", "유저2"));
		Container.memberDao.add(new Member(4, Util.getNowDateStr(), "test3", "test3", "유저3"));
		Container.memberDao.add(new Member(5, Util.getNowDateStr(), "test4", "test4", "유저4"));

	}
}
