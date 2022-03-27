package com.example.firestore_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firestore_sample.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    userRecyclerViewAdapter adapter; //리싸이클러 뷰 어댑터
    RecyclerView recyclerView; //리싸이클러 뷰
    ArrayList<UserModel> userList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance(); // Access a Cloud Firestore instance from your Activity

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new userRecyclerViewAdapter(userList); //initialize Adapter

        binding.rvUserList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.rvUserList.setAdapter(adapter);
        binding.btnReload.setOnClickListener(view -> { //새로고침 버튼
            db.collection("users")
                    .get()
                    .addOnSuccessListener(e -> {
                        userList.clear();
                        for (QueryDocumentSnapshot document : e) {
                            UserModel user = new UserModel((String) document.get("firstName")
                                    , (String) document.get("lastName")
                                    , (Long) document.get("born")
                            );
                            userList.add(user);
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(this, "유저 데이터 로드 성공", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(exception -> {
                        Toast.makeText(this, "데이터 로드 실패", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "error! " + exception);
                    });
        });

        binding.btnWrite.setOnClickListener(view -> { //유저 등록
            //동적으로 AlertDialog 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            TextView tvFirstName = new TextView(this);
            TextView tvLastName = new TextView(this);
            TextView tvBorn = new TextView(this);
            tvFirstName.setText("firstName");
            tvLastName.setText("lastName");
            tvBorn.setText("born");

            EditText etFirstName = new EditText(this);
            EditText etLastName = new EditText(this);
            EditText etBorn = new EditText(this);
            etFirstName.setSingleLine(true);
            etLastName.setSingleLine(true);

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 16);
            layout.addView(tvFirstName);
            layout.addView(etFirstName);
            layout.addView(tvLastName);
            layout.addView(etLastName);
            layout.addView(tvBorn);
            layout.addView(etBorn);

            builder.setView(layout)
                    .setTitle("유저 추가")
                    .setPositiveButton(R.string.add, (dialogInterface, i) -> {
                        UserModel user = new UserModel(etFirstName.getText().toString()
                                , etLastName.getText().toString()
                                , Long.parseLong(etBorn.getText().toString())
                        );

                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(e -> {
                                    Toast.makeText(this, "유저 추가 성공", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(exception -> {
                                    Toast.makeText(this, "유저 추가 실패", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "error! " + exception);
                                });

                        binding.btnReload.callOnClick(); //새로고침 버튼 클릭

                    })
                    .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {

                    })
                    .show();
        });

        binding.btnDelete.setOnClickListener(view -> { //유저삭제
            db.collection("user")
                    .document()
                    .delete()
                    .addOnSuccessListener(e -> {
                        binding.btnReload.callOnClick(); //새로고침 버튼 클릭
                        Toast.makeText(this, "유저 삭제 성공", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(exception -> {
                        Toast.makeText(this, "유저 삭제 실패", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "error! " + exception);
                    });
        });

    }

}