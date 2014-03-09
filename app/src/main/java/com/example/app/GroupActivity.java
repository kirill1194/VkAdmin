package com.example.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app.utils.BaseActivity;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.model.VKGroup;
import com.vk.sdk.api.model.VKGroupArray;

import java.util.TreeMap;
import java.util.zip.Inflater;

public class GroupActivity extends BaseActivity {
    VKGroup currentGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        final Long groupId =  extras.getLong("groudId");
        VKRequest request = new VKApiGroups().getById(new VKParameters(new TreeMap<String, Object>() {
            {
                put("group_id", groupId.toString());
                put("fields", "description,wiki_page,members_count");
            }
        }));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKGroupArray currentGroups = (VKGroupArray) response.parsedModel;
                currentGroup = currentGroups.get(0);
                System.out.println(currentGroup.type);
                setCurrentGroup();

            }
            @Override
            public void onError(VKError error) {
                System.out.println("error");
                String errorGroup = "группа не получена";
            }
        });
    }

    public void setCurrentGroup() {
        TextView groupName = (TextView) findViewById(R.id.s_groupName);
        ImageView groupImage = (ImageView) findViewById(R.id.s_groupLogo);
        UrlImageViewHelper.setUrlDrawable(groupImage, currentGroup.photo_100);
        groupName.setText(currentGroup.name);
        TextView groupDescription = (TextView) findViewById(R.id.s_description);
        groupDescription.setText(currentGroup.description);
        Button membersButton = (Button) findViewById(R.id.s_listOfMembers);
        membersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // откроем здесь работу с пользователями
            }
        });
        Button wikiPage = (Button) findViewById(R.id.s_wiki_page);
        wikiPage.setText(currentGroup.wiki_page);
        // обработать нажатие кнопки вики
        Button photos = (Button) findViewById(R.id.s_photos);
        // обработать нажатие кнопки фото (!)
        Button links = (Button) findViewById(R.id.s_links);
        // обработать нажатие кнопки ссылки (!)
        //ListView wall = (ListView) findViewById(R.id.s_wallPosts);
        // обработать стену группы
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

