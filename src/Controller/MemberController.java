package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Article.Member;
import YeomIT.Util;

public class MemberController extends Controller {
	private Scanner sc;
	private List<Member> members;
	private String command;
	private String actionMethodName;

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

		Member member = getMemberByLoginId(loginId);

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

	private Member getMemberByLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return null;
		}

		return members.get(index);

	}

	public MemberController(Scanner sc) {
		this.sc = sc;
		members = new ArrayList<>();
	}

	private void doJoin() {
		int id = members.size() + 1;
		String regDate = Util.getNowDateStr();

		String loginId = null;
// 문제점 식별 : 명령어 입력 후 엔터를 치면 그냥 입력이 되는 문제
// "" 를 식별해서 걸러내는 코드 보완
		while (true) {
			System.out.printf("로그인 아이디 : ");
			// member join 하면 빈칸 없애기.
			loginId = sc.nextLine().trim();

			if (loginId == "") {
				System.out.printf("아이디를 입력해주세요.\n");
				continue;
			}

			if (isJoinableLoginId(loginId) == false) {
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
		members.add(member);

		System.out.printf("%d번 회원이 가입하였습니다\n", id);

	}

	public void makeTestData() {
		System.out.println("테스트를 위한 회원 데이터를 생성합니다.");
		members.add(new Member(1, Util.getNowDateStr(), "admin", "admin", "관리자"));
		members.add(new Member(2, Util.getNowDateStr(), "test1", "test1", "유저1"));
		members.add(new Member(3, Util.getNowDateStr(), "test2", "test2", "유저2"));

	}

	private boolean isJoinableLoginId(String loginId) {
		int index = getMemberIndexByLoginId(loginId);

		if (index == -1) {
			return true;
		}

		return false;
	}

	private int getMemberIndexByLoginId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) {
				return i;
			}
			i++;
		}

		return -1;
	}

}
