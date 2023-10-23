package com.example.sqlopenhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sqlopenhelper.adapter.EmployeeAdapter;
import com.example.sqlopenhelper.model.Employee;
import com.example.sqlopenhelper.sqlite.EmployeeDao;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EmployeeAdapter employeeAdapter;
    private ListView lvEmployee;

    private String employeeId;
    private String employeeName;

    private List<Employee> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DBHelper dbhelper = new DBHelper(this);
//        SQLiteDatabase database = dbhelper.getReadableDatabase();
//        database.close();
        findViewById(R.id.btnInsert).setOnClickListener(this);
        findViewById(R.id.btnEdit).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);


        lvEmployee = findViewById(R.id.lvEmployee);
        EmployeeDao dao = new EmployeeDao(this);
        list = dao.getAll();
        employeeAdapter = new EmployeeAdapter(this, list);
        lvEmployee.setAdapter(employeeAdapter);
        lvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee emp = list.get(position);
                employeeId = emp.getId();
                employeeName = emp.getName();
                Toast.makeText(MainActivity.this, "Đã chọn nhân viên: " + "[" + employeeId + "] " + employeeName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Thay đổi nội dung trong List tức thì sau khi CRUD
    @Override
    protected void onResume() {
        super.onResume();
        EmployeeDao dao = new EmployeeDao(this);
        List<Employee> updatedList = dao.getAll();

        list.clear();
        updatedList.forEach(item->list.add(item));
        employeeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddOrEditEmployeeActivity.class);

        // Có thể dùng switch case cho gọn thay vì dùng if

        if (v.getId() == R.id.btnInsert) {
            startActivity(intent);
        }

        if (v.getId() == R.id.btnEdit) {
            if (employeeId == null) {
                Toast.makeText(this, "Bạn chưa chọn nhân viên nào cả!", Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("id", employeeId);
            intent.putExtra("data", bundle);

            startActivity(intent);
        }

        if (v.getId() == R.id.btnDelete) {
            if (employeeId == null) {
                Toast.makeText(this, "Bạn cần chọn một nhân viên để xóa!", Toast.LENGTH_SHORT).show();
                return;
            }

            EmployeeDao dao = new EmployeeDao(this);
            dao.delete(employeeId);
            onResume();
            Toast.makeText(this, "Đã xóa nhân viên: " + "[" + employeeId + "] " + employeeName, Toast.LENGTH_SHORT).show();
            employeeId = null;
        }

    }
}