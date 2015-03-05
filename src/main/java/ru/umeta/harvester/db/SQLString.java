package ru.umeta.harvester.db;

public class SQLString {
	public static String get(String str) {
		return str = "'"+str+"'";
	}
}
