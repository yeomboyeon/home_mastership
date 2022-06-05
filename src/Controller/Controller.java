package Controller;

import Article.Member;

public abstract class Controller {

	public static Member loginedMember; // static 로 부여해주면 각 클래스에서 접근 가능하도록 부여(클래스별 각각 활용하는 것이 아닌)

	public abstract void doAction(String command, String actionMethodName);

	public abstract void makeTestData(); // 추상으로 보완

	public static boolean islogined() { // islogined 함수도 각 클래스에서 활용하도록 보완
		return loginedMember != null; //
	}
}
