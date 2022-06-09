package Dao;

public abstract class Dao {

	public int lastId;

	Dao() {
		lastId = 0;
	}

	public int getNewId() {
		return lastId + 1;

	}

	public int getLastId() {
		return lastId;
	}
}
