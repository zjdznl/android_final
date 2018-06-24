package club.zhengjiadi.problem3;

/**
 * Created by zjdzn on 2018/6/23.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private int resourceId;
    private Context context;

    public ContactAdapter(Context context, int textViewResourceId, List<Contact> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        this.context = context;
    }

    public View getView(int position, View convertView, final ViewGroup parent) {
        final Contact contact = getItem(position); //获取当前项的Contact实例
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(contact.getName());

        return view; //返回布局
    }

    class ViewHolder {
        TextView name;
        TextView number;
    }
}
