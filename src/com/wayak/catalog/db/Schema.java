package com.wayak.catalog.db;

public  final class Schema {
	public static class Products{
		public static final String TABLE_NAME="products";
		public static final String KEY_ID="id";
		public static final String KEY_NAME="name";
		public static final String KEY_DESCRIPTION="description";
		public static final String KEY_SIZE="size";
		public static final String KEY_CODE="code";
		public static final String KEY_PRICE="price";
		public static final String QUERY_CREATE="CREATE TABLE if not exists "+TABLE_NAME
				+" ( "
				+KEY_ID+" integer PRIMARY KEY,"
				+KEY_CODE+" real,"
				+KEY_PRICE+" real DEFAULT 0.0,"
				+KEY_NAME+" text DEFAULT '',"
				+KEY_DESCRIPTION+" text DEFAULT '',"
				+KEY_SIZE+" text DEFAULT ''"
				+" )";
		public static final String QUERY_UPDATE="DROP TABLE IF EXISTS "+TABLE_NAME;
	}
}
