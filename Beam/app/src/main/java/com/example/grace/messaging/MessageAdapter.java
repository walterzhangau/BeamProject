package com.example.grace.messaging;


//
// Based on code from 
// http://www.devexchanges.info/2016/03/design-chat-bubble-ui-in-android.html
//
//
//

/**
 * Created by evanl on 28/09/2017.
 */

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grace.myapplication.R;

import java.util.List;


//based on MessageAdapter.Java in 'Design Chat Bubble UI in Android' by Hong Thai
//
//http://www.devexchanges.info/2016/03/design-chat-bubble-ui-in-android.html

public class MessageAdapter extends ArrayAdapter<ChatMessage> {

    private Activity activity;
    private List<ChatMessage> messages;

    public MessageAdapter(Activity context, int resource, List<ChatMessage> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.messages = objects;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        ChatMessage chatMessage = getItem(position);
        int viewType = getItemViewType(position);

        if (chatMessage.isMine()) {
            layoutResource = R.layout.item_chat_right;
        } else {
            layoutResource = R.layout.item_chat_left;
        }

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        //set message content
        holder.msg.setText(chatMessage.getContent());
        holder.sender.setText(chatMessage.getSender());
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // return the total number of view types. this value should never change
        // at runtime
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        if (getItem(position).isMine()){
            return 1;
        }
        else{return 0;}
    }

    private class ViewHolder {
        private TextView msg;
        private TextView sender;

        private ViewHolder(View v) {
            msg = v.findViewById(R.id.txt_msg);
            sender = v.findViewById(R.id.msg_sender);
        }
    }
}