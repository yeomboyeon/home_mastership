package Controller;

import Article.Member;

public abstract class Controller {

	public static Member loginedMember;
	public abstract void doAction(String command, String actionMethodName);

	public abstract void makeTestData();
	public static boolean islogined() { 
		return loginedMember != null;
	}
}
