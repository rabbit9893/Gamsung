package com.example.gamsung;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CafeReview extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static List<AllReview> allReviewList = new ArrayList<>();
    private RatingBar review_ratingBar;
    private ImageButton addButton, subButton;
    private TextView textView,search;
    private EditText reviewText;
    private String name = null, title;
    private String mood, coffee, dessert, rest, price, waiting, star, text, reviewcnt;
    private Button mood1, mood2, mood3, mood4, mood5, mood6, mood7, mood8; //분위기 버튼 8개
    private Button coffee1, coffee2, coffee3, dessert1, dessert2, dessert3; //커피와 디저트 버튼 각각 3개씩
    private Button rgood, rbad; //화장실 좋은지 나쁜지
    private Button price1,price2,waiting1,waiting2; //가격과 웨이팅
    private Button reviewbtn;
    private String button;
    private String pos;
    private String address, time, tel, restroom, views, imgone, imgtwo, imgthr;
    private int no = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafe_review);


        textView = (TextView)findViewById(R.id.textView);
        search = (TextView)findViewById(R.id.myFilter);
        reviewText = (EditText)findViewById(R.id.reviewText);
        addButton = (ImageButton)findViewById(R.id.add);
        subButton = (ImageButton)findViewById(R.id.sub);
        mood1 = (Button)findViewById(R.id.mood1);
        mood2 = (Button)findViewById(R.id.mood2);
        mood3 = (Button)findViewById(R.id.mood3);
        mood4 = (Button)findViewById(R.id.mood4);
        mood5 = (Button)findViewById(R.id.mood5);
        mood6 = (Button)findViewById(R.id.mood6);
        mood7 = (Button)findViewById(R.id.mood7);
        mood8 = (Button)findViewById(R.id.mood8);
        coffee1 = (Button)findViewById(R.id.coffe1);
        coffee2 = (Button)findViewById(R.id.coffe2);
        coffee3 = (Button)findViewById(R.id.coffe3);
        dessert1 = (Button)findViewById(R.id.dessert1);
        dessert2 = (Button)findViewById(R.id.dessert2);
        dessert3 = (Button)findViewById(R.id.dessert3);
        rgood = (Button)findViewById(R.id.rgood);
        rbad = (Button)findViewById(R.id.rbad);
        price1 = (Button)findViewById(R.id.price1);
        price2 = (Button)findViewById(R.id.price2);
        waiting1 = (Button)findViewById(R.id.waiting1);
        waiting2 = (Button)findViewById(R.id.waiting2);
        reviewbtn = (Button)findViewById(R.id.review);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        if(name != null) {
            search.setText(""+name);
        }
        price = intent.getStringExtra("price");
        dessert = intent.getStringExtra("dessert");
        address = intent.getStringExtra("address");
        time = intent.getStringExtra("time");
        star = intent.getStringExtra("star");
        tel = intent.getStringExtra("tel");
        restroom = intent.getStringExtra("restroom");
        views = intent.getStringExtra("views");
        title = intent.getStringExtra("title");
        pos = intent.getStringExtra("pos");
        reviewcnt = intent.getStringExtra("reviewcnt");
        imgone = intent.getStringExtra("imgone");
        imgtwo = intent.getStringExtra("imgtwo");
        imgthr = intent.getStringExtra("imgthr");

        reviewText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 글자 수가 변동되면 동작하는 메소드
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String input = reviewText.getText().toString();
                textView.setText(input.length() + "/ 800 자");
                text = reviewText.getText().toString();
                System.out.println(text);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        review_ratingBar = (RatingBar)findViewById(R.id.review_ratingbar);
        review_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                // RatingBar의 별점 값이 변하면 동작하는 메소드
                System.out.println(""+review_ratingBar.getRating());
                star = String.valueOf(review_ratingBar.getRating());
            }
        });

        // +버튼을 누르면 동작하는 메소드(0.5점씩 올려줌)
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review_ratingBar.setRating((float)(review_ratingBar.getRating() + 0.5));

            }
        });
        // -버튼을 누르면 동작하는 메소드(0.5점씩 내려줌)
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                review_ratingBar.setRating((float)(review_ratingBar.getRating() - 0.5));
            }
        });

        star = String.valueOf(review_ratingBar.getRating());

        // 검색창을 클릭하면 검색하는 Activity로 넘어감
        search.setInputType(0);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putInt("where", 1);
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        final GestureDetector gd = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                switch (button) {
                    case "mood1" :
                        mood1.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "mood2" :
                        mood2.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "mood3" :
                        mood3.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "mood4" :
                        mood4.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "mood5" :
                        mood5.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "mood6" :
                        mood6.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "mood7" :
                        mood7.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "mood8" :
                        mood8.setBackground(getDrawable(R.drawable.buttonborder));
                        mood = null;
                        break;
                    case "coffee1" :
                        coffee1.setBackground(getDrawable(R.drawable.buttonborder));
                        coffee = null;
                        break;
                    case "coffee2" :
                        coffee2.setBackground(getDrawable(R.drawable.buttonborder));
                        coffee = null;
                        break;
                    case "coffee3" :
                        coffee3.setBackground(getDrawable(R.drawable.buttonborder));
                        coffee = null;
                        break;
                    case "dessert1" :
                        dessert1.setBackground(getDrawable(R.drawable.buttonborder));
                        dessert = null;
                        break;
                    case "dessert2" :
                        dessert2.setBackground(getDrawable(R.drawable.buttonborder));
                        dessert = null;
                        break;
                    case "dessert3" :
                        dessert3.setBackground(getDrawable(R.drawable.buttonborder));
                        dessert = null;
                        break;
                    case "rgood" :
                        rgood.setBackground(getDrawable(R.drawable.buttonborder));
                        rest = null;
                        break;
                    case "rbad" :
                        rbad.setBackground(getDrawable(R.drawable.buttonborder));
                        rest = null;
                        break;
                    case "price1" :
                        price1.setBackground(getDrawable(R.drawable.buttonborder));
                        price = null;
                        break;
                    case "price2" :
                        price2.setBackground(getDrawable(R.drawable.buttonborder));
                        price = null;
                        break;
                    case "waiting1" :
                        waiting1.setBackground(getDrawable(R.drawable.buttonborder));
                        waiting = null;
                        break;
                    case "waiting2" :
                        waiting2.setBackground(getDrawable(R.drawable.buttonborder));
                        waiting = null;
                        break;
                }

                return true;
            }
            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return super.onDoubleTapEvent(e);
            }
        });

        mood1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood1.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood1.getText().toString();
                    System.out.println(mood);
                }
                button = "mood1";
                return gd.onTouchEvent(motionEvent);
            }
        });
        mood2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood2.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood2.getText().toString();
                    System.out.println(mood);
                }
                button = "mood2";
                return gd.onTouchEvent(motionEvent);
            }
        });
        mood3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood3.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood3.getText().toString();
                    System.out.println(mood);
                }
                button = "mood3";
                return gd.onTouchEvent(motionEvent);
            }
        });
        mood4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood4.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood4.getText().toString();
                    System.out.println(mood);
                }
                button = "mood4";
                return gd.onTouchEvent(motionEvent);
            }
        });
        mood5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood5.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood5.getText().toString();
                    System.out.println(mood);
                }
                button = "mood5";
                return gd.onTouchEvent(motionEvent);
            }
        });
        mood6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood6.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood6.getText().toString();
                    System.out.println(mood);
                }
                button = "mood6";
                return gd.onTouchEvent(motionEvent);
            }
        });
        mood7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood7.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood7.getText().toString();
                    System.out.println(mood);
                }
                button = "mood7";
                return gd.onTouchEvent(motionEvent);
            }
        });
        mood8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(mood == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    mood8.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    mood = mood8.getText().toString();
                    System.out.println(mood);
                }
                button = "mood8";
                return gd.onTouchEvent(motionEvent);
            }
        });

        coffee1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(coffee == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    coffee1.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    coffee = coffee1.getText().toString();
                    System.out.println(coffee);
                }
                button = "coffee1";
                return gd.onTouchEvent(motionEvent);
            }
        });
        coffee2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(coffee == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    coffee2.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    coffee = coffee2.getText().toString();
                    System.out.println(coffee);
                }
                button = "coffee2";
                return gd.onTouchEvent(motionEvent);
            }
        });
        coffee3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(coffee == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    coffee3.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    coffee = coffee3.getText().toString();
                    System.out.println(coffee);
                }
                button = "coffee3";
                return gd.onTouchEvent(motionEvent);
            }
        });

        dessert1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(dessert == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    dessert1.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    dessert = dessert1.getText().toString();
                    System.out.println(dessert);
                }
                button = "dessert1";
                return gd.onTouchEvent(motionEvent);
            }
        });
        dessert2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(dessert == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    dessert2.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    dessert = dessert2.getText().toString();
                    System.out.println(dessert);
                }
                button = "dessert2";
                return gd.onTouchEvent(motionEvent);
            }
        });
        dessert3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(dessert == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    dessert3.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    dessert = dessert3.getText().toString();
                    System.out.println(dessert);
                }
                button = "dessert3";
                return gd.onTouchEvent(motionEvent);
            }
        });

        rgood.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(rest == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    rgood.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    rest = rgood.getText().toString();
                    System.out.println(rest);
                }
                button = "rgood";
                return gd.onTouchEvent(motionEvent);
            }
        });
        rbad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(rest == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    rbad.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    rest = rbad.getText().toString();
                    System.out.println(rest);
                }
                button = "rbad";
                return gd.onTouchEvent(motionEvent);
            }
        });

        price1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(price == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    price1.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    price = price1.getText().toString();
                    System.out.println(price);
                }
                button = "price1";
                return gd.onTouchEvent(motionEvent);
            }
        });
        price2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(price == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    price2.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    price = price2.getText().toString();
                    System.out.println(price);
                }
                button = "price2";
                return gd.onTouchEvent(motionEvent);
            }
        });

        waiting1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(waiting == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    waiting1.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    waiting = waiting1.getText().toString();
                    System.out.println(waiting);
                }
                button = "waiting1";
                return gd.onTouchEvent(motionEvent);
            }
        });
        waiting2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(waiting == null && motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    waiting2.setBackground(getDrawable(R.drawable.buttonfillboarder));
                    waiting = waiting2.getText().toString();
                    System.out.println(waiting);
                }
                button = "waiting2";
                return gd.onTouchEvent(motionEvent);
            }
        });

        // TODO REVIEW 제출하기 버튼을 누르면 동작하는 메소드
        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference(title+"/");
                AllReview review = new AllReview(text, mood, coffee, dessert, rest, price, star, waiting);
                Map<String, Object> reviewValues = review.toMap();
                reviewValues.putAll(reviewValues);

                databaseReference.child(pos).child("review").child(reviewcnt).updateChildren(reviewValues).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("SuccessFul!!!!!!!!!!!!!!!!!!!11");
                        AlertDialog.Builder builder = new AlertDialog.Builder(CafeReview.this);
                        builder.setMessage("리뷰가 작성되었습니다\uD83D\uDE0D");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        System.out.println(allReviewList.size());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        Bundle extras = new Bundle(); // 번들은 인텐트 속에 있는 데이터 꾸러미
                        extras.putString("name", name);
                        extras.putString("address", address);
                        extras.putString("dessert", dessert);
                        extras.putString("time", time);
                        extras.putString("tel", tel);
                        extras.putString("restroom", restroom);
                        extras.putString("views", views);
                        extras.putString("imgone", imgone);
                        extras.putString("imgtwo", imgtwo);
                        extras.putString("imgthr", imgthr);
                        extras.putString("title", title);
                        extras.putString("price", price);
                        extras.putString("star", star);
                        extras.putString("reviewcnt", reviewcnt);
                        extras.putString("pos", pos);
                        extras.putInt("no",no);
                        extras.putString("text", text);
                        extras.putString("mood", mood);
                        extras.putString("coffee", coffee);
                        extras.putString("dessert", dessert);
                        extras.putString("rest", rest);
                        extras.putString("price", price);
                        extras.putString("star", star);
                        extras.putString("waiting", waiting);

                        intent.putExtras(extras);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failure!!!!!!!!!!!!!!!!!!!!11");
                    }
                });

                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("reviewcnt", String.valueOf(Integer.parseInt(reviewcnt)+1));
                databaseReference.child(pos).updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println("SuccessFul!!!!!!!!!!!!!!!!!!!11");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Failure!!!!!!!!!!!!!!!!!!!!11");
                    }
                });
            }
        });
    }
}

