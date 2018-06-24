package club.zhengjiadi.problem5.fragment;

/**
 * Created by zjdzn on 2018/6/24.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import club.zhengjiadi.problem5.R;

public class MainFragment extends Fragment {
    private View rootView;
    private TextView textView;
    private String text;
    private String[] classDetail;


    public MainFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        classDetail = getResources().getStringArray(R.array.class_detail);

        if (getArguments().containsKey("class")){
            int position = getArguments().getInt("class");
            text = classDetail[position];
        } else {
            text = "点击左边滑动菜单查看介绍";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        textView = rootView.findViewById(R.id.label);
        setText(text);
        return rootView;
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

}