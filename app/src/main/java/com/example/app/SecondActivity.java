package com.example.app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app.utils.BaseActivity;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.model.VKGroup;
import com.vk.sdk.api.model.VKGroupArray;
import com.vk.sdk.api.model.VKUser;
import com.vk.sdk.api.model.VKUsersArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Created by Алексей on 09.02.14.
 */
public class SecondActivity extends BaseListActivity {
    ArrayList<VKGroup> list;
    GroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<VKGroup>();
        adapter = new GroupAdapter();
        setListAdapter(adapter);
        VKRequest request = new VKApiGroups().get(new VKParameters(new TreeMap<String, Object>() {
            {
                put("extended", 1);
                put("filter", "admin");
            }
        }));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKGroupArray obj = (VKGroupArray) response.parsedModel;
                list.clear();
                for (int i = 0; i < obj.size(); i++) {
                    list.add(obj.get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VKError error) {
                String errorUserName = "имя не получено";
//Do error stuff
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
            }
        });


    }

    protected class GroupAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return list.get(i).id;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) view = View.inflate(SecondActivity.this, R.layout.group_item, null);
            VKGroup currentGroup = (VKGroup) getItem(i);
            ((TextView) view.findViewById(R.id.group_name)).setText(currentGroup.name);
            ((TextView) view.findViewById(R.id.type_of_group)).setText(currentGroup.type);
            ImageView groupImage = (ImageView) view.findViewById(R.id.group_image);
            UrlImageViewHelper.setUrlDrawable(groupImage, currentGroup.photo_200);
            return view;

        }
    }


}
