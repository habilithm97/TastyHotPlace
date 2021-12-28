package com.example.tastyhotplace;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements OnItemClickListener, Filterable {

    TextView nameTv, locationTv, menuTv, sideTv, priceTv, timeTv, telTv, reviewTv, noteTv;

    public Context context;

    public static int position;

    public static ArrayList<CardItem> cardItems = new ArrayList<CardItem>(); // 리사이클러뷰 아이템들을 담을 어레이 리스트 생성 // 필터되지않은 기본 어레이 리스트
    public static ArrayList<CardItem> filteredList = new ArrayList<CardItem>(); // 필터된 어레이 리스트

    public RecyclerViewAdapter(Context context, ArrayList<CardItem> cardItems) {
        this.cardItems = cardItems;
        this.filteredList = cardItems;
        this.context = context;
    }

    // https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=rkswlrbduf&logNo=221208233990
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // 생성되면서 자동으로 호출됨 -> card_item.xml을 인플레이션
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.card_item, viewGroup, false);

        context = viewGroup.getContext();

        nameTv = itemView.findViewById(R.id.nameTv);
        locationTv = itemView.findViewById(R.id.locationTv);
        menuTv = itemView.findViewById(R.id.menuTv);
        sideTv = itemView.findViewById(R.id.sideTv);
        priceTv = itemView.findViewById(R.id.priceTv);
        timeTv = itemView.findViewById(R.id.timeTv);
        telTv = itemView.findViewById(R.id.telTv);
        reviewTv = itemView.findViewById(R.id.reviewTv);
        noteTv = itemView.findViewById(R.id.noteTv);

        return new ViewHolder(itemView, this); // 뷰홀더 객체를 생성하면서 뷰 객체를 전달하고 그 뷰홀더 객체를 반환
    }

    @Override // 뷰홀더가 재사용될 때 호출됨, 뷰 객체는 기존 것을 그대로 사용하고 데이터만 바꿔줌
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        CardItem item = cardItems.get(position);
        holder.setItem(item);

        holder.nameTv.setText(item.getName());
        holder.locationTv.setText(item.getLocation());
        holder.menuTv.setText(item.getMenu());
        holder.sideTv.setText(item.getSide());
        holder.priceTv.setText(item.getPrice());
        holder.timeTv.setText(item.getTime());
        holder.telTv.setText(item.getTel());
        holder.reviewTv.setText(item.getReview());
        holder.noteTv.setText(item.getNote());

    }

    public void addItem(CardItem item) {
        cardItems.add(item);
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String str = charSequence.toString(); // 검색창에 입력된 문자열
                if(str.isEmpty()) { // 검색창에 입력한 문자가 없으면
                    cardItems = filteredList; // 기존의 어레이 리스트로
                } else {
                    ArrayList<CardItem> filteringList = new ArrayList<CardItem>();

                    for (CardItem item : cardItems) {
                        if (item.getName().contains(charSequence) || item.getLocation().contains(charSequence) || item.getMenu().contains(charSequence) || item.getSide().contains(charSequence) ||
                                item.getPrice().contains(charSequence) || item.getTime().contains(charSequence) || item.getTel().contains(charSequence) || item.getReview().contains(charSequence) ||
                                item.getNote().contains(charSequence)) {
                            filteringList.add(item);
                        }
                    }
                    filteredList = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredList = (ArrayList<CardItem>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public ImageView foodImg;
        public TextView nameTv, locationTv, menuTv, sideTv, priceTv, timeTv, telTv, reviewTv, noteTv;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) { // 뷰홀더 생성자로 전달되는 뷰 객체를 참조함(아이템들은 뷰로 만들어지고, 뷰는 뷰홀더에 담아둠)
            super(itemView); // 이 뷰 객체를 부모 클래스의 변수에 담아둠
            foodImg = (ImageView)itemView.findViewById(R.id.foodImg);
            nameTv = (TextView)itemView.findViewById(R.id.nameTv);
            locationTv = (TextView)itemView.findViewById(R.id.locationTv);
            menuTv = (TextView)itemView.findViewById(R.id.menuTv);
            sideTv =  (TextView)itemView.findViewById(R.id.sideTv);
            priceTv = (TextView)itemView.findViewById(R.id.priceTv);
            timeTv = (TextView)itemView.findViewById(R.id.timeTv);
            telTv = (TextView)itemView.findViewById(R.id.telTv);
            reviewTv = (TextView)itemView.findViewById(R.id.reviewTv);
            noteTv = (TextView)itemView.findViewById(R.id.noteTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = getAdapterPosition(); // 클릭한 위치를 가져옴

                    String itemName = nameTv.getText().toString();
                    String itemLocation = locationTv.getText().toString();
                    String itemMenu = menuTv.getText().toString();
                    String itemSide = sideTv.getText().toString();
                    String itemPrice = priceTv.getText().toString();
                    String itemTime = timeTv.getText().toString();
                    String itemTel = telTv.getText().toString();
                    String itemReview = reviewTv.getText().toString();
                    String itemNote = noteTv.getText().toString();

                    // https://twinw.tistory.com/31
                    Intent intent = new Intent(context, WritePlace.class);
                    // Bitmap sendBitmap = BitmapFactory.decodeResource(getResource(), )
                    intent.putExtra("itemName", itemName);
                    intent.putExtra("itemLocation", itemLocation);
                    intent.putExtra("itemMenu", itemMenu);
                    intent.putExtra("itemSide", itemSide);
                    intent.putExtra("itemPrice", itemPrice);
                    intent.putExtra("itemTime", itemTime);
                    intent.putExtra("itemTime", itemTime);
                    intent.putExtra("itemTel", itemTel);
                    intent.putExtra("itemReview", itemReview);
                    intent.putExtra("itemNote", itemNote);

                    ((Activity)context).startActivityForResult(intent, 97);

                    //WritePlace.updateBtn.setVisibility(View.VISIBLE);
                    //WritePlace.saveBtn.setVisibility(View.INVISIBLE);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    position = getAdapterPosition(); // 클릭한 위치를 가져옴

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제하기");
                    builder.setMessage("선택한 맛집을 정말로 삭제하시겠습니까 ?");
                    builder.setIcon(R.drawable.delete);
                    builder.setPositiveButton("삭제하기",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteItem(position);
                                }
                            });
                    builder.setNegativeButton("취소",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    builder.show();

                    return true;
                }
            });
        }

        public void setItem(CardItem item) {
            nameTv.setText(item.getName());
            locationTv.setText(item.getLocation());
            menuTv.setText(item.getMenu());
            sideTv.setText(item.getSide());
            priceTv.setText(item.getPrice());
            timeTv.setText(item.getTime());
            telTv.setText(item.getTel());
            reviewTv.setText(item.getReview());
            noteTv.setText(item.getNote());
        }

        @Override
        public boolean onLongClick(View view) {
            return true;
        }
    }

    public void deleteItem(int position) {
        cardItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cardItems.size());
    }
}