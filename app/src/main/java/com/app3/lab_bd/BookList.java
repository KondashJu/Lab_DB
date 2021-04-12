package com.app3.lab_bd;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


public class BookList extends ArrayAdapter<Book> {


        private int  resource;
        private LayoutInflater inflater;
        private Context context;


        public BookList(Context ctx, int resourceId, List<Book> objects) {
            super( ctx, resourceId, objects );
            resource = resourceId;
            inflater = LayoutInflater.from( ctx );
            context = ctx;
        }

        @Override
        public View getView ( int position, View convertView, ViewGroup parent ) {

            convertView = ( LinearLayout ) inflater.inflate( resource, null );

            Book book = getItem(position);

            TextView txtTitle = (TextView) convertView.findViewById(R.id.bookTitle);
            txtTitle.setText(book.getTitle() + "  |  ");

            TextView txtAuthor = (TextView) convertView.findViewById(R.id.bookAuthor);
            txtAuthor.setText(book.getAuthor());

            return convertView;
        }



}
