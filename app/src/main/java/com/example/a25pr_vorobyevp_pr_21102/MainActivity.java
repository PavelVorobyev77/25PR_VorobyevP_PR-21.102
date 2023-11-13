package com.example.a25pr_vorobyevp_pr_21102;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mChickenSound, mCowSound, mDogSound,
            mDuckSound, mSheepSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        mCatSound = loadSound("cat.ogg");
        mChickenSound = loadSound("chicken.ogg");
        mCowSound = loadSound("cow.ogg");
        mDogSound = loadSound("dog.ogg");
        mDuckSound = loadSound("duck.ogg");
        mSheepSound = loadSound("sheep.ogg");

        ImageButton cowImageButton = findViewById(R.id.imageButtonCow);
        cowImageButton.setOnClickListener(onClickListener);

        ImageButton chickenImageButton = findViewById(R.id.imageButtonChicken);
        chickenImageButton.setOnClickListener(onClickListener);

        ImageButton catImageButton = findViewById(R.id.imageButtonCat);
        catImageButton.setOnClickListener(onClickListener);

        ImageButton duckImageButton = findViewById(R.id.imageButtonDuck);
        duckImageButton.setOnClickListener(onClickListener);

        ImageButton sheepImageButton = findViewById(R.id.imageButtonSheep);
        sheepImageButton.setOnClickListener(onClickListener);

        ImageButton dogImageButton = findViewById(R.id.imageButtonDog);
        dogImageButton.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == R.id.imageButtonCow) {
                playSound(mCowSound);
            } else if (viewId == R.id.imageButtonChicken) {
                playSound(mChickenSound);
            } else if (viewId == R.id.imageButtonCat) {
                playSound(mCatSound);
            } else if (viewId == R.id.imageButtonDuck) {
                playSound(mDuckSound);
            } else if (viewId == R.id.imageButtonSheep) {
                playSound(mSheepSound);
            } else if (viewId == R.id.imageButtonDog) {
                playSound(mDogSound);
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        try {
            AssetFileDescriptor afd = mAssetManager.openFd(fileName);
            return mSoundPool.load(afd, 1);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Не могу загрузить файл " + fileName, Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCatSound = loadSound("cat.ogg");
        mChickenSound = loadSound("chicken.ogg");
        mCowSound = loadSound("cow.ogg");
        mDogSound = loadSound("dog.ogg");
        mDuckSound = loadSound("duck.ogg");
        mSheepSound = loadSound("sheep.ogg");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}