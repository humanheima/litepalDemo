package com.hm.litepaldemo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.hm.litepaldemo.model.Album;
import com.hm.litepaldemo.model.Song;

import org.litepal.LitePal;
import org.litepal.LitePalDB;
import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_get_datebase)
    Button btnGetDatebase;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_upgrade)
    Button btnUpgrade;
    @BindView(R.id.btn_update_data)
    Button btnUpdateData;
    @BindView(R.id.btn_detele_data)
    Button btnDeteleData;
    @BindView(R.id.btn_query_data)
    Button btnQueryData;
    @BindView(R.id.btn_multiple_database)
    Button btnMultipleDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_get_datebase)
    public void onClick() {
        SQLiteDatabase db = LitePal.getDatabase();
    }

    @OnClick(R.id.btn_save)
    public void saveDate() {
        Album album = new Album();
        album.setName("album");
        album.setPrice(10.99f);
        album.setCover("human".getBytes());
        album.save();
        Song song1 = new Song();
        song1.setName("song1");
        song1.setDuration(320);
        song1.setAlbum(album);
        song1.save();
        Song song2 = new Song();
        song2.setName("song2");
        song2.setDuration(356);
        song2.setAlbum(album);
        song2.save();
    }

    /**
     * 升级表 修改实体类，修改litepal.xml文件
     */
    @OnClick(R.id.btn_upgrade)
    public void upgradeDateBase() {

    }

    @OnClick(R.id.btn_update_data)
    public void updateData() {

        /**
         * 先找到对应的记录，然后使用save方法更新
         */
       /* Album album = DataSupport.where("name = ?", "album").findFirst(Album.class);
        album.setName("human my dear");
        album.save();*/

        /**
         * 使用update 方法
         */
      /*  Album album1 = new Album();
        album1.setName("human");
        album1.update(1);*/

        /**
         * 使用 updateAll 方法
         */
        Album album2 = new Album();
        album2.setName("dumingwei");
        album2.updateAll("name = ?", "human");
    }

    @OnClick(R.id.btn_detele_data)
    public void deleteData() {

        /**
         * 根据id 删除
         */
        DataSupport.delete(Song.class, 1);
        /**
         * 根据条件删除
         */
        DataSupport.deleteAll(Song.class, "id > ?", "2");
    }

    @OnClick(R.id.btn_query_data)
    public void queryData() {

        /**
         * 根据id 来查
         */
        Song song = DataSupport.find(Song.class, 1);

        /**
         * 查询所有的数据
         */
        List<Song> list = DataSupport.findAll(Song.class);

        /**
         * 条件查询
         */
        List<Song> songList = DataSupport.where("name like ?", "song%").order("duration").find(Song.class);
    }

    /**
     * 创建多数据库
     */
    @OnClick(R.id.btn_multiple_database)
    public void multipleDatabase() {
        /**
         * 使用litepal.xml 文件里面的配置
         */
      /*  LitePalDB litePalDB = LitePalDB.fromDefault("human");
        LitePal.use(litePalDB);*/

        /**
         * 自定义配置
         */
        LitePalDB litePalDB1 = new LitePalDB("demo2", 1);
        litePalDB1.addClassName(Album.class.getName());
        litePalDB1.addClassName(Song.class.getName());
        LitePal.use(litePalDB1);
    }
}
