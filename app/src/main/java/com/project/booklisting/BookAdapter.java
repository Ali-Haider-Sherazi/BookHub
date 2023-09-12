package com.project.booklisting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.URL;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final RecylerViewInterface recylerViewInterface;

    private ArrayList<BookInfo> books = new ArrayList<>();
    private Context mContext;
    public int  size;

    public BookAdapter(Context context, ArrayList<BookInfo> books, RecylerViewInterface recylerViewInterface)
    {
       /* int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, books.get(0).getBookName()+" in adapter ", duration);
        toast.show();*/
        mContext = context;
        this.books = books;
        this.recylerViewInterface = recylerViewInterface;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new BookViewHolder(view, recylerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.e("this is working ", " Wrong");
        onBindViewHolders((BookViewHolder) holder, position);
    }

    public void onBindViewHolders(@NonNull BookViewHolder holder, int position) {
        holder.bindBook(books.get(position));
        Log.e("this is working ", " correct");
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public BookInfo getItem(int position) {
        Log.e("position is ", " "+ position+" and value is "+books.get(position));
    return books.get(position);
    }

    public ArrayList<BookInfo> getBook()
    {
        return books;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView Name,author,publisher,date;
        ImageView bookpic;
        public BookViewHolder(View itemView, RecylerViewInterface recylerViewInterface) {

           super(itemView);
           Name = (TextView) itemView.findViewById(R.id.name);
           author = (TextView) itemView.findViewById(R.id.author);
           publisher = (TextView) itemView.findViewById(R.id.publisher);
           date = (TextView) itemView.findViewById(R.id.date);
           bookpic = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(v-> {
                Log.e(" Clicked pos is  "," ");
            });

           //Fetching the Position of item selected in recycler view
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                if(recylerViewInterface != null)
                {
                    int pos = getAdapterPosition();
                    Log.e(" Clicked pos is  "," "+ pos +" bOkk Size "+ books.size());
                    if(pos!= RecyclerView.NO_POSITION)
                    {
                        recylerViewInterface.onItemClick(pos);
                        size= books.size();
                    }
                }
                else
                {
                    Log.e(" Clicked pos is  "," wrong");
                }

               }

           });



        }
        @SuppressLint("LongLogTag")
        public void bindBook(BookInfo bookInfo)
        {
            date.setText("Date: "+bookInfo.getDate());
            Name.setText("Name: "+bookInfo.getBookName());
            publisher.setText("publisher: "+bookInfo.getPublisherName());
            author.setText("Author: "+bookInfo.getAuthorName());
            if(bookInfo.getImg()!= null && mContext!=null) {
               // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(bookpic);
                Glide.with(mContext)
                        .load(bookInfo.getImg())
                        .apply(new RequestOptions().override(100, 200))
                        .into(bookpic);

                Log.e("Book= ",bookInfo.getImg());
            }
            else {
                bookpic.setImageResource(R.mipmap.ic_launcher);
                Log.e("Book or context Not Found"," ");
            }
    //            bookpic.setImageResource(bookInfo.mImgResourceId());


        }


    }
}



/*
public BookAdapter(Activity context, ArrayList<BookInfo> info, int colors) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, info);
        ColorID=colors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        BookInfo bookInfo = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView Name = (TextView) listItemView.findViewById(R.id.name);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        Name.setText(bookInfo.getBookName());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView author = (TextView) listItemView.findViewById(R.id.author);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        author.setText(bookInfo.getAuthorName());
        // Find the ImageView in the list_item.xml layout with the ID image

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView publisher = (TextView) listItemView.findViewById(R.id.publisher);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        publisher.setText(bookInfo.getPublisherName());
        // Find the ImageView in the list_item.xml layout with the ID image

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        date.setText(bookInfo.getDate());
        // Find the ImageView in the list_item.xml layout with the ID image


        ImageView imageView = listItemView.findViewById(R.id.image);
        if(bookInfo.hasImg())
        {
            // set this text on the number ImageView
            imageView.setImageResource(bookInfo.mImgResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else
            imageView.setVisibility(View.GONE);
        return listItemView;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
 */