package com.example.gamsung;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommunityMain extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private RecyclerView recyclerView;
    private CommunityCardAdapter communityCardAdapter;
    private List<Community> communitycardList;
    private List<Community> communityList;
    private ImageButton write;   // 글쓰기 버튼

    private String useremail, userDisplayname, communitycnt;
    private Uri profileurl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community);

        // 로그인 정보를 가지고 옴
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
//            Glide.with(this).load(account.getPhotoUrl()).circleCrop().into(user_profile);
            userDisplayname = account.getDisplayName();
            useremail = account.getEmail();
            profileurl = account.getPhotoUrl();
            Log.d("로그인 정보를 잘 불러왔음",  "이름 : " + account.getDisplayName() + "이메일 : " + account.getEmail() + "photourl : " + account.getPhotoUrl()) ;
        }

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        communitycardList = new ArrayList<>();
        communityList = new ArrayList<>();
        communityCardAdapter = new CommunityCardAdapter(this, communitycardList);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(communityCardAdapter);
        prepareCommunityCard();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 기존 title 지우기
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼 만들기
        actionBar.setHomeAsUpIndicator(R.drawable.list); //뒤로가기 버튼 이미지 지정

        // 글쓰기(연필) 버튼을 클릭 시
        write = findViewById(R.id.write);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CommunityActivity.class);
                /** 로그인한 사용자를 보내야함 **/
                Bundle extras = new Bundle(); // 번들은 인텐트 속에 있는 데이터 꾸러미
                extras.putString("userDisplayname", userDisplayname);
                extras.putString("useremail", useremail);
                extras.putString("profileurl", profileurl.toString());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }


    public void prepareCommunityCard() {
        int round[] = new int[]{
                R.drawable.hcafe25, R.drawable.hcafe2, R.drawable.hcafe19, R.drawable.hcafe18, R.drawable.hcafe11
        };

        databaseReference.child("커뮤니티").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                AllCommunity allCommunity = dataSnapshot.getValue(AllCommunity.class);
                Community c = new Community(allCommunity.getUserDisplayname(), allCommunity.getUseremail(),
                        allCommunity.getSubject(), allCommunity.getMaterial(), allCommunity.getText());
                communitycardList.add(c);
                System.out.println(String.valueOf(communitycardList.size()));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                AllCommunity allCommunity = dataSnapshot.getValue(AllCommunity.class);
                Community c = new Community(allCommunity.getUserDisplayname(), allCommunity.getUseremail(),
                        allCommunity.getSubject(), allCommunity.getMaterial(), allCommunity.getText());
                communitycardList.add(c);
                System.out.println(String.valueOf(communitycardList.size()));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        communityCardAdapter.notifyDataSetChanged();
//        Community community = new Community(round[0], "수제레몬청에이드","지녕이");
//        communitycardList.add(community);
//        Community community1 = new Community(round[1], "수제키위에이드","쩡오니");
//        communitycardList.add(community1);
//        Community community2 = new Community(round[2], "수제체리에이드","수혀니");
//        communitycardList.add(community2);
//        Community community3 = new Community(round[3], "수제청포도에이드","유비니");
//        communitycardList.add(community3);
//        Community community4 = new Community(round[4], "수제블루베리에이드","민경이");
//        communitycardList.add(community4);
    }
}
