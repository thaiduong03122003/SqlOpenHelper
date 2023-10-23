package com.example.sqlopenhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlopenhelper.R;
import com.example.sqlopenhelper.model.Employee;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {
    private Context context;
    private List<Employee> list;

    public EmployeeAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int iPosition) {
        return list.get(iPosition);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from( context).inflate(R.layout.layout_employee_item, null);
        }

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvSalary = view.findViewById(R.id.tvSalary);

        Employee emp = list.get(i);
        tvName.setText(emp.getName());
        tvSalary.setText("" + emp.getSalary());
        return view;
    }
}
