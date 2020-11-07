package com.example.gamsung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/** Community를 클릭했을 때 나오는 리스트 (CUPPER 커뮤니티 바로가기, 이번주 홈카페 BEST10 등) **/

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.MyViewHolder> {
    private Context context;
    private List<Community> communityList;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private List<Community> communitycardList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username,title,subtext,views;
        public ImageView image;
        public MyViewHolder(View view) {    // 뷰홀더가 만들어짐
            super(view);
            title = view.findViewById(R.id.title);
            username = view.findViewById(R.id.writer);
            subtext = view.findViewById(R.id.subtext);
            image = view.findViewById(R.id.image);
            views = view.findViewById(R.id.views);
        }
    }
    public CommunityAdapter(Context mContext, List<Community> data) {
        this.context = mContext;
        communityList = data;
    }
    @Override
    public int getItemCount() {
        return communityList.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item, parent, false);

        return new CommunityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Community community = communityList.get(position);
        holder.image.setImageResource(community.getImg());
        holder.title.setText(community.getTitle());
        holder.username.setText(community.getUsername());
        holder.subtext.setText(community.getSubtext());
        holder.title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String title = community.getTitle();
                Bundle extras = new Bundle();
                extras.putString("title", title);
                if(title.equals("CUPPER 커뮤니티 바로가기")) {
                    String communitycnt = community.getCommunitycnt();
                    extras.putString("communitycnt", communitycnt);
                   Intent intent = new Intent(view.getContext(), CommunityMain.class);
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                }
                if(title.equals("홈카페 TOP 10")) {
                    Intent intent = new Intent(view.getContext(), CommunityTop.class);
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                }
                if(title.equals("홈카페 인테리어 둘러보기")) {
                    Intent intent = new Intent(view.getContext(), CommunityHomecafe.class);
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                }
                if(title.equals("홈카페 기본용품 준비하기")) {
                    Intent intent = new Intent(view.getContext(), Community_tool.class);
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                }
                if(title.equals("지식 공유 게시판")) {
                    Intent intent = new Intent(view.getContext(), MessageBoard.class);
                    intent.putExtras(extras);
                    view.getContext().startActivity(intent);
                }


            }
        });
    }


}
