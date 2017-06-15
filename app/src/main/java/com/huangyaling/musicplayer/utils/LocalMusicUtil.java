package com.huangyaling.musicplayer.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.huangyaling.musicplayer.R;
import com.huangyaling.musicplayer.bean.SongBean;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by huangyaling on 2017/6/15.
 */
public class LocalMusicUtil {
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

    public static ArrayList<SongBean> getAllSongs(Context context){
        //获取本地音乐的详细信息，并添加到List中
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        ArrayList<SongBean> mp3Infos = new ArrayList<SongBean>();
        for(int i = 0;i<cursor.getCount();i++){
            cursor.moveToNext();
            SongBean mp3info = new SongBean();
            Long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            long albumId = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int isMusic = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.IS_MUSIC));

            if(isMusic != 0){
                mp3info.setId(id);
                mp3info.setTitle(title);
                mp3info.setArtist(artist);
                mp3info.setAlbum(album);
                mp3info.setDisplayName(displayName);
                mp3info.setAlbumId(albumId);
                mp3info.setDuration(duration);
                mp3info.setSize(size);
                mp3info.setUrl(url);
                mp3Infos.add(mp3info);
            }
        }
        return mp3Infos;
    }

    public static List<HashMap<String, String>> getMusicMaps(List<SongBean> mp3Infos){
        List<HashMap<String, String>> mp3list = new ArrayList<HashMap<String ,String>>();
        for(Iterator iterator = mp3Infos.iterator();iterator.hasNext();){
            SongBean mp3Info = (SongBean) iterator.next();
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("title",mp3Info.getTitle());
            map.put("artist",mp3Info.getArtist());
            map.put("album",mp3Info.getAlbum());
            map.put("displayName",mp3Info.getDisplayName());
            map.put("albumId",String.valueOf(mp3Info.getAlbumId()));
            map.put("duration",formatTime(mp3Info.getDuration()));
            map.put("size",String.valueOf(mp3Info.getSize()));
            map.put("url",mp3Info.getUrl());
            mp3list.add(map);
        }
        return mp3list;
    }

    public static String formatTime(long time){
        String min = time/(1000*60)+"";
        String sec = time%(1000*60)+"";
        if(min.length()<2){
            min = "0"+time/(1000*60)+"";
        }else{
            min = time/(1000*60)+"";
        }
        if(sec.length() == 4){
            sec = "0"+(time%(1000*60))+"";
        }else if(sec.length() == 3){
            sec = "00"+(time%(1000*60))+"";
        }else if(sec.length() == 2){
            sec = "000"+(time%(1000*60))+"";
        }else if(sec.length() == 1){
            sec = "0000"+(time%(1000*60))+"";
        }
        return min + ":" + sec.trim().substring(0,2);
    }

    private static final Uri sArtWorkUri = Uri.parse("content://media/external/audio/albumart");
    private static final BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();

    public static Bitmap getArtwork(Context context,long song_id,long album_id,boolean allowdefault){
        if(album_id<0){
            if(song_id >= 0){
                Bitmap bm = getArtworkFromFile(context,song_id,-1);
                if(bm != null){
                    return bm;
                }
            }
            if(allowdefault){
                return getDefaultArtwork(context);
            }
            return null;
        }
        ContentResolver res = context.getContentResolver();
        Uri uri = ContentUris.withAppendedId(sArtWorkUri,album_id);
        if(uri != null){
            InputStream in = null;
            try{
                in = res.openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(in, null,sBitmapOptions);
                if(bmp == null){
                    bmp = getDefaultArtwork(context);
                }
                return bmp;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Bitmap bm = getArtworkFromFile(context,song_id,album_id);
                if(bm != null){
                    if(bm.getConfig() == null){
                        bm = bm.copy(Bitmap.Config.RGB_565,false);
                        if(bm == null && allowdefault){
                            return getDefaultArtwork(context);
                        }
                    }
                }else if(allowdefault){
                    bm = getDefaultArtwork(context);
                }
                return bm;
            }finally {
                try{
                    if(in != null){
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private  static Bitmap getArtworkFromFile(Context context,long songid,long albumid){
        Bitmap bm = null;
        if(albumid<0 && songid<0){
            throw  new IllegalArgumentException("Must specify an album or a song id");
        }

        try{
            if(albumid<0){
                Uri uri = Uri.parse("content://media/external/audio/meida/"+songid+"/albumart");
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri,"r");
                if(pfd != null){
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            }else{
                Uri uri = ContentUris.withAppendedId(sArtWorkUri,albumid);
                ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri,"r");
                if(pfd != null){
                    FileDescriptor fd = pfd.getFileDescriptor();
                    bm = BitmapFactory.decodeFileDescriptor(fd);
                }
            }

        }catch(FileNotFoundException ex){

        }
        return bm;
    }

    private static Bitmap getDefaultArtwork(Context context){
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.circle_music),null,opts);
    }
}

