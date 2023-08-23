# SQLite Database in Android:
SQLite is a lightweight, embedded relational database management system that is widely used in Android applications to store and manage structured data. It's a part of the Android framework and provides a local storage solution for apps to store data locally on the device.

# Key Concepts:
Database: A structured collection of data organized into tables, rows, and columns.
Table: Represents a single type of data entity. It consists of rows (records) and columns (fields)
Row: A single record within a table, containing values for each column.
Column: Represents a specific attribute or property of the data stored in the table.

#Using SQLite Database in Android:
To use an SQLite database in your Android application, follow these steps:

Create a Database Helper: Extend the SQLiteOpenHelper class to manage database creation and version management. Override the onCreate and onUpgrade methods.

Define Database Schema: Define the tables and their columns in a contract class that extends BaseColumns.

Perform Database Operations:

Insert: Use SQLiteDatabase.insert() or SQLiteDatabase.insertOrThrow() to add data to a table.
Query: Use SQLiteDatabase.query() to retrieve data from a table.
Update: Use SQLiteDatabase.update() to modify existing records.
Delete: Use SQLiteDatabase.delete() to remove records from a table.
Close the Database: Always close the database connection when you're done with it using SQLiteDatabase.close().


#Example:
Here's a simplified example of creating an SQLite database to store and retrieve user data:

#Database Helper:

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_app.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.UserEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.UserEntry.SQL_DELETE_TABLE);
        onCreate(db);
    }
}



#Database Schema:

public class UserContract {
    private UserContract() {}

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_EMAIL = "email";

        public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME_USERNAME + " TEXT," +
            COLUMN_NAME_EMAIL + " TEXT)";

        public static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}





#Perform Database Operations:

// Insert
SQLiteDatabase db = myDatabaseHelper.getWritableDatabase();
ContentValues values = new ContentValues();
values.put(UserContract.UserEntry.COLUMN_NAME_USERNAME, "user123");
values.put(UserContract.UserEntry.COLUMN_NAME_EMAIL, "user@example.com");
long newRowId = db.insert(UserContract.UserEntry.TABLE_NAME, null, values);

// Query
String[] projection = {
    UserContract.UserEntry._ID,
    UserContract.UserEntry.COLUMN_NAME_USERNAME,
    UserContract.UserEntry.COLUMN_NAME_EMAIL
};
Cursor cursor = db.query(
    UserContract.UserEntry.TABLE_NAME,
    projection,
    null,
    null,
    null,
    null,
    null
);

// Don't forget to close the cursor and database when done
cursor.close();
db.close();
