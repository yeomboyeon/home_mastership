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
	private Member loginedMember;

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
		if (islogined() == false) { // islogined 함수 추가
			System.out.println("로그인 상태가 아닙니다.");
			return;
		}
		loginedMember = null; // loginedMember 에 로그인 된 상태를 null로 바꾸면 로그아웃이 된다는 의미
		System.out.println("로그아웃 되었습니다.");
	}

	private boolean islogined() { // loginedMember 가 null 값이 아니다라면 로그인 된 상태를 의미 로그인이 됬니 안됬니 판단
		return loginedMember != null; // 로그인 상태가 null과 같지 같다 false를 리턴
	}

	private void dologin() {
		if (islogined()) { // 로그인이 되어 있다면 막아야하기에 코드 작성
			System.out.println("이미 로그인 되어 있습니다.");
			return;
		}
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
// 5번째 인자를 받아올 때 게시판과는 다르게 숫자가 아닌 문자로 받던 했어야 했음.
	public void makeTestData() {
		System.out.println("테스트를 위한 회원을 생성합니다.");
		members.add(new Member(1, Util.getNowDateStr(), "admin", "admin", "관리자"));
		members.add(new Member(2, Util.getNowDateStr(), "asd", "asd", "유저2"));
		members.add(new Member(3, Util.getNowDateStr(), "qqq", "qqq", "유저3"));

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

		while (true) {
			System.out.printf("로그인 아이디 : ");
			loginId = sc.nextLine();

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
			System.out.printf("로그인 비밀번호 확인: ");
			loginPwConfirm = sc.nextLine();

			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 다시 입력해주세요");
				continue;
			}

			break;
		}

		System.out.printf("이름 : ");
		String name = sc.nextLine();

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
