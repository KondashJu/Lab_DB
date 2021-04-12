package com.app3.lab_bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BooksDataSource {

    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    private String[] allColumns = { SQLiteHelper.COLUMN_ID,
            SQLiteHelper.COLUMN_TITLE, SQLiteHelper.COLUMN_AUTHOR };

    public BooksDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Book createBook(Book book) {
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.COLUMN_TITLE, book.getTitle());
        values.put(SQLiteHelper.COLUMN_AUTHOR, book.getAuthor());
        long insertId = database.insert(SQLiteHelper.TABLE_BOOKS, null,
                values);
        Cursor cursor = database.query(SQLiteHelper.TABLE_BOOKS,
                allColumns, SQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Book newBook = cursorToBook(cursor);
        cursor.close();
        return newBook;
    }

    public void deleteBook(Book book) {
        long id = book.getId();
        System.out.println("Book deleted with id: " + id);
        database.delete(SQLiteHelper.TABLE_BOOKS, SQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Book> getAllBooksByAuthor(String bookAuthor) {
        List<Book> books = new ArrayList<Book>();

        Cursor cursor = database.query(true, SQLiteHelper.TABLE_BOOKS, allColumns, SQLiteHelper.COLUMN_AUTHOR + "=?" , new String[]{bookAuthor},
                null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }

        cursor.close();
        return books;
    }

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book(cursor.getString(1), cursor.getString(2));
        book.setId(cursor.getLong(0));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));
        return book;
    }
}
