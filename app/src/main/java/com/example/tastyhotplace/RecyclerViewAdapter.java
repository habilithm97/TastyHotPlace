package com.example.tastyhotplace;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
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

import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.tastyhotplace.R.id.foodImg;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements OnItemClickListener {

    TextView nameTv, locationTv, menuTv, sideTv, priceTv, timeTv, telTv, reviewTv, noteTv;

    Context context;

    public static int position;

    static ArrayList<CardItem> cardItems;
    //public static ArrayList<CardItem> filteredList = new ArrayList<CardItem>();
    Activity activity;

    public RecyclerViewAdapter(ArrayList<CardItem> cardItems, Activity activity) {
        this.cardItems = cardItems;
        this.activity = activity;
    }
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
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

        return new ViewHolder(itemView, this);
    }

    @Override
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

    public void updateItem(int position, CardItem item) {
        cardItems.set(position, item);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public ImageView foodImg;
        public TextView nameTv, locationTv, menuTv, sideTv, priceTv, timeTv, telTv, reviewTv, noteTv;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) { // ????????? ???????????? ???????????? ??? ????????? ?????????(??????????????? ?????? ???????????????, ?????? ???????????? ?????????)
            super(itemView); // ??? ??? ????????? ?????? ???????????? ????????? ?????????
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
                    position = getAdapterPosition(); // ????????? ????????? ?????????

                    String itemName = nameTv.getText().toString();
                    String itemLocation = locationTv.getText().toString();
                    String itemMenu = menuTv.getText().toString();
                    String itemSide = sideTv.getText().toString();
                    String itemPrice = priceTv.getText().toString();
                    String itemTime = timeTv.getText().toString();
                    String itemTel = telTv.getText().toString();
                    String itemReview = reviewTv.getText().toString();
                    String itemNote = noteTv.getText().toString();

                    Intent intent = new Intent(context, UpdateActivity.class);

                    /*
                    // putExtra??? ?????? ??? ?????? ???????????? ????????? 100KB?????? ??? ?????? ????????? ?????? ????????? ????????? ?????? ?????? ?????? ??????.
                    // ????????? : ?????? ????????? Byte Array??? ???????????? ?????? ??? ?????? ????????? ?????? ?????? ????????? ????????????.
                    Bitmap itemImg = BitmapFactory.decodeResource(context.getResources(), R.id.foodImg); // ????????? ????????? fooImg ????????? ????????? ???????????? ??????
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    itemImg.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                    intent.putExtra("image", byteArray); */

                    /*
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bitmap = ((BitmapDrawable)foodImg.getDrawable()).getBitmap();
                    float scale = (float)(1024/(float)bitmap.getWidth());
                    int img_w = (int)(bitmap.getWidth() * scale);
                    int img_h = (int)(bitmap.getHeight() * scale);
                    Bitmap resize = Bitmap.createScaledBitmap(bitmap, img_w, img_h, true);
                    resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray(); */

                    //intent.putExtra("itemImg", byteArray);
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

                    ((Activity)context).startActivityForResult(intent, 98);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    position = getAdapterPosition(); // ????????? ????????? ?????????

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("????????????");
                    builder.setMessage("????????? ????????? ????????? ???????????????????????? ?");
                    builder.setIcon(R.drawable.delete);
                    builder.setPositiveButton("????????????",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteItem(position);
                                }
                            });
                    builder.setNegativeButton("??????",
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

    public void filterList(ArrayList<CardItem> filteredList) {
        cardItems = filteredList;
        notifyDataSetChanged();
    }
}

/*
    @Override
    public Filter getFilter() { // Filter ??????????????? ?????????????????? ?????????
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) { // ?????? ???????????? ??????
                String str = charSequence.toString(); // ???????????? ????????? ?????????
                if(str.isEmpty()) { // ???????????? ????????? ????????? ?????????
                    filteredList = cardItems; // ?????????????????? ????????? ????????? ???????????? ??????
                } else { // ???????????? ????????? ????????? ?????????
                    ArrayList<CardItem> filteringList = new ArrayList<CardItem>();


                    for (CardItem item : cardItems) {
                        if (item.getName().contains(charSequence) || item.getLocation().contains(charSequence) || item.getMenu().contains(charSequence) || item.getSide().contains(charSequence) ||
                                item.getPrice().contains(charSequence) || item.getTime().contains(charSequence) || item.getTel().contains(charSequence) || item.getReview().contains(charSequence) ||
                                item.getNote().contains(charSequence)) {
                            filteringList.add(item);
                        }
                    }

                    for(CardItem item : cardItems) { // ????????????????????? ????????? ????????? ???????????? ???????????? ????????????
                        if (item.getName().toLowerCase().contains(str.toLowerCase()) || item.getLocation().toLowerCase().contains(str.toLowerCase()) || item.getMenu().toLowerCase().contains(str.toLowerCase()) ||
                                item.getSide().toLowerCase().contains(str.toLowerCase()) || item.getPrice().toLowerCase().contains(str.toLowerCase()) || item.getTime().toLowerCase().contains(str.toLowerCase()) ||
                                item.getTel().toLowerCase().contains(str.toLowerCase()) || item.getReview().toLowerCase().contains(str.toLowerCase()) || item.getNote().toLowerCase().contains(str.toLowerCase())) {
                            filteringList.add(item); // ???????????? ???????????? ????????? ????????? ?????? ???????????? ??????
                        }
                    }
                    filteredList = filteringList; // ????????? ?????? ???????????? ???????????? ???????????? ??????
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) { // ?????????????????? ????????????
                filteredList = (ArrayList<CardItem>)filterResults.values; // FilterResults??? values ????????? ???????????? ???????????? ?????????
                notifyDataSetChanged();
            }
        };
    } */