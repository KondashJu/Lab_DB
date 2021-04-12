package com.app3.lab_bd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BooksDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List myList = new ArrayList();
        ListView bookList = (ListView) findViewById(R.id.theList);
        bookList.setAdapter(new BookList(this, R.layout.row_view, myList));


    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        EditText title = (EditText) findViewById(R.id.enterTitle);
        EditText author = (EditText) findViewById(R.id.enterAuthor);
        EditText booksView = (EditText) findViewById(R.id.retrieveBooks);

        Button saveBook = (Button) findViewById(R.id.setBook);
        Button showBooks = (Button) findViewById(R.id.getBooks);

        ListView bookList = (ListView) findViewById(R.id.theList);
        BookList bookadapter = (BookList) bookList.getAdapter();

        datasource = new BooksDataSource(this);
        datasource.open();

        Book newBook = null;
        switch (view.getId()) {
            case R.id.setBook:
                List myList = new ArrayList();
                String bookTitle = title.getText().toString();
                String bookAuthor = author.getText().toString();
                Book book = new Book(bookTitle, bookAuthor);
                myList.add(book);

                newBook = datasource.createBook(book);
                bookadapter.add(newBook);
                title.setText("");
                author.setText("");
                break;
            case R.id.getBooks:
                booksView.getText().toString();
                List<Book> books = datasource.getAllBooksByAuthor(booksView.getText().toString());
                bookList.setAdapter(new BookList(this, R.layout.row_view, books));
                booksView.setText("");
                break;
            case R.id.exit:
                finish();
                break;
        }
        bookadapter.notifyDataSetChanged();
    }



    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }


}
