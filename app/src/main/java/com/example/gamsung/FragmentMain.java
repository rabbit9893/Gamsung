package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FragmentMain extends Fragment {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public static List<AllCafe> allCafeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;

    private CircleAdapter circleadapter;
    private List<Circle> circleList;
    private List<Circle> circleList2;

    private EditText search;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 3;
    private String pos = "0";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootview = (ViewGroup)inflater.inflate(R.layout.fragment_main, container, false);
        search = (EditText)rootview.findViewById(R.id.myFilter);
        recyclerView = (RecyclerView)rootview.findViewById(R.id.recycler_view);
        recyclerView2 = (RecyclerView)rootview.findViewById(R.id.recycler_view2);
        circleList = new ArrayList<>();
        circleadapter = new CircleAdapter(getActivity(), circleList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        recyclerView.setAdapter(circleadapter);
        prepareCircles(1);

        circleList2 = new ArrayList<>();
        circleadapter = new CircleAdapter(getActivity(), circleList2);
        RecyclerView.LayoutManager manager1 = new GridLayoutManager(getActivity(), 4);
        recyclerView2.setLayoutManager(manager1);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(circleadapter);
        prepareCircles(2);

        mPager = (ViewPager)rootview.findViewById(R.id.pager);
        mPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new ZoomOutTransformer());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        search.setInputType(0);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putInt("where", 0);
                Intent intent = new Intent(getContext(), SearchActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        databaseReference.child("혜화").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                if(allCafeList.size() < 40) {
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("혜화");
                    System.out.println(allCafeList.size());
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(allCafeList.size() < 40) {
                    AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("혜화");
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("망원동").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                if(allCafeList.size() < 40) {
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("망원동");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                if(allCafeList.size() < 40) {
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("망원동");
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("익선동").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                if(allCafeList.size() < 40) {
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("익선동");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                if(allCafeList.size() < 40) {
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("익선동");
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("연남동").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                if(allCafeList.size() < 40) {
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("연남동");
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AllCafe allCafe = dataSnapshot.getValue(AllCafe.class);
                if(allCafeList.size() < 40) {
                    allCafeList.add(allCafe);
                    allCafeList.get(allCafeList.size() - 1).setTitle("연남동");
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootview;
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private String tabTitiles[] = {"Tab1", "Tab2", "Tab3"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public long getItemId(int position) {
            return System.currentTimeMillis();
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return SlideFirstPageFragment.newInstance(0, "Page 1");
                case 1:
                    return SlideSecondPageFragment.newInstance(1, "Page 2");
                case 2:
                    return SlideThirdPageFragment.newInstance(2, "Page 3");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {     // 페이지의 숫자
            return NUM_PAGES; //위에 상수로 정의 페이지 숫자를 상수로 정의함 다섯개로 정의되있음.
        }   //화면에서 사라지면 메모리에 유지해야되는게 효율적으로 유지시켜주는 어댑터

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitiles[position];
        }
    }

    private void prepareCircles(int i) {
        int[] covers = new int[]{
                R.drawable.circle, R.drawable.circle2, R.drawable.circle3, R.drawable.circle4,
                R.drawable.circle8, R.drawable.circle5, R.drawable.circle6, R.drawable.circle7
        };

        if (i == 1) {
            Circle c = new Circle("혜화", covers[0]);
            circleList.add(c);
            c = new Circle("망원동", covers[1]);
            circleList.add(c);
            c = new Circle("익선동", covers[2]);
            circleList.add(c);
            c = new Circle("연남동", covers[3]);
            circleList.add(c);
        }
        else if (i == 2) {
            Circle c = new Circle("마카롱", covers[4]);
            circleList2.add(c);
            c = new Circle("티라미수", covers[5]);
            circleList2.add(c);
            c = new Circle("브런치", covers[6]);
            circleList2.add(c);
            c = new Circle("타르트", covers[7]);
            circleList2.add(c);
        }
    }
}
