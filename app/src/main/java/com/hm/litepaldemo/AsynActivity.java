package com.hm.litepaldemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.hm.litepaldemo.model.Album;
import com.hm.litepaldemo.model.Song;

import org.litepal.crud.DataSupport;
import org.litepal.crud.callback.FindMultiCallback;
import org.litepal.crud.callback.SaveCallback;
import org.litepal.crud.callback.UpdateOrDeleteCallback;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AsynActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_save, R.id.btn_update_data, R.id.btn_detele_data, R.id.btn_query_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                final List<Album> albumList = new ArrayList<>();
                for (int i = 0; i < 10000; i++) {
                    Album album = new Album();
                    album.setName("album" + i);
                    album.setPrice(i * 10);
                    album.setReleaseDate(new Date(System.currentTimeMillis()));
                    albumList.add(album);
                }
                DataSupport.saveAllAsync(albumList).listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        Log.e(TAG, "save asyn success?" + success);
                    }
                });
                break;
            case R.id.btn_update_data:
                update();
                break;
            case R.id.btn_detele_data:
                DataSupport.deleteAllAsync(Album.class)
                        .listen(new UpdateOrDeleteCallback() {
                            @Override
                            public void onFinish(int rowsAffected) {
                                Log.e(TAG, "deleteAllAsync success?" + rowsAffected);
                            }
                        });
                break;
            case R.id.btn_query_data:
                DataSupport.findAllAsync(Album.class)
                        .listen(new FindMultiCallback() {
                            @Override
                            public <T> void onFinish(List<T> t) {
                                List<Album> albumListFound = (List<Album>) t;
                                Log.e(TAG, "find asyn success?" + albumListFound.size());
                            }
                        });
                break;
        }
    }

    private void update() {
        Song song = new Song();
        song.setName("逆战");
        song.setDuration(1000);
        //如果存在name叫逆战的就更新，否则直接存储
        song.saveOrUpdate("name = ?", song.getName());
        //异步操作
        song.saveOrUpdateAsync("name = ?", song.getName())
                .listen(new SaveCallback() {
                    @Override
                    public void onFinish(boolean success) {
                        Log.e(TAG, "saveOrUpdateAsync success?" + success);
                    }
                });
    }
}
