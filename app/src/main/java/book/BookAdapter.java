package book;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.demoapp.DetailActivity;
import com.example.demoapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import my_interface.IClickItemCategory;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> mBook;
    private Context mContext;

    public BookAdapter( Context context,List<Book> list) {
        this.mContext = context;
        this.mBook = list;
    }


    public void setData(List<Book> list){
        this.mBook = list;
        notifyDataSetChanged();   // hàm load dữ liệu lên adapter
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
       final Book book = mBook.get(position);
        if(book==null){
            return;
        }
        holder.imgBook.setImageResource(book.getResourceId());
        holder.tvTilte.setText(book.getTitle());

        holder.layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGotoDetail(book);
            }
        });
    }

    private void onClickGotoDetail(Book book){
        Intent intent = new Intent(mContext, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_book",book);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(mBook!=null){
            return mBook.size();
        }
        return 0;
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{

        private CardView layout_item;
        private ImageView imgBook;
        private TextView tvTilte;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            layout_item = itemView.findViewById(R.id.layout_item);
            imgBook = itemView.findViewById(R.id.img_book);
            tvTilte = itemView.findViewById(R.id.tv_titile);
        }
    }
}
