package com.blogspot.therightoveninc.codenamepuck;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class receive extends ActionBarActivity {
    public int messageCount = 3;
    private PopupWindow popupWindow;
    private Bitmap bitmap;
    private ImageView imageView;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.receive);

        setDimensions();

        if (messageCount > 0)
        {
            new GetImageAsyncTask().execute();
        }
    }

    // ensure views are accessed after being loaded
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        imageView = (ImageView)findViewById(R.id.imageView);
    }

    private class GetImageAsyncTask extends AsyncTask<URL, Void, Integer>
    {
        protected Integer doInBackground(URL... urls) {
            try
            {
                url = new URL("http://emilines.com/wp-content/uploads/2014/10/beautiful-celebrity-hd-wallpapers.jpg");
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            new DecodeSampledBitmapFromStream().execute();
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private class DecodeSampledBitmapFromStream extends AsyncTask<URL, Void, Integer>
    {
        protected Integer doInBackground(URL... urls)
        {
            ViewGroup.LayoutParams imageParams = imageView.getLayoutParams();

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            try {
                BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
                Log.e("zz","herro");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, imageParams.width, imageParams.height);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            try {
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
                Log.e("kk","crt");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return 0;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_settings:

                return true;
            case R.id.action_report:
                reportClick();
                return true;
            case R.id.action_camera:
                onCameraClick();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setDimensions()
    {
        // Get Action Bar Height (pixels)
        TypedValue tv = new TypedValue();
        this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);

        // Convert pixels to dp
        float fl_width = math.convertPixelsToDp(phoneSettings.xPixels);
        float fl_height = math.convertPixelsToDp(phoneSettings.yPixels);
        float fl_actionBarHeight = math.convertPixelsToDp(actionBarHeight);

        // Adjust dimensions to have 15 dp margins
        fl_width -= 30f;

        // Calculate dp values
        float fl_button = (fl_height - (fl_width + 15f + fl_actionBarHeight )) / 2;
        float fl_button_margin = (float) (fl_width - (2.5 * fl_button)) / 2;
        float fl_spacer_width = fl_button / 2;

        // Convert dp to pixels
        int pic_size = (int)math.convertDpToPixel(fl_width);
        int button_size = (int)math.convertDpToPixel(fl_button);
        int button_margin = (int)math.convertDpToPixel(fl_button_margin);
        int spacer_width = (int)math.convertDpToPixel(fl_spacer_width);

        // Set picture dimensions
        ImageView image = (ImageView) findViewById(R.id.imageView);
        ViewGroup.LayoutParams imageParams = image.getLayoutParams();
        imageParams.width = pic_size;
        imageParams.height = pic_size;
        image.setLayoutParams(imageParams);

        // Set button dimensions
        ImageButton shuck_button = (ImageButton) findViewById(R.id.shuck_button);
        Button puck_button = (Button) findViewById(R.id.puck_button);
        ViewGroup.LayoutParams buttonParams = shuck_button.getLayoutParams();
        buttonParams.width = button_size;
        buttonParams.height = button_size;
        shuck_button.setLayoutParams(buttonParams);
        puck_button.setLayoutParams(buttonParams);

        // Set spacer width
        View view = findViewById(R.id.view);
        ViewGroup.LayoutParams spacerParams = view.getLayoutParams();
        spacerParams.width = spacer_width;
        spacerParams.height = 1;
        view.setLayoutParams(spacerParams);

        // Set button spacing and locations
        View linearLayout = findViewById(R.id.linear_layout);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(button_margin,spacerParams.width/2,0,0);
        params.addRule(RelativeLayout.BELOW, R.id.imageView);
        linearLayout.setLayoutParams(params);
    }

    public void puckClick(View v)
    {
        Log.e("a", "hi");
        // send back url

        messageDelete();
    }

    public void shuckClick(View v)
    {
        Log.e("a", "shuck that!");

        messageDelete();
    }

    // Popup code (mostly) starts here
    public void reportClick()
    {
        LayoutInflater layoutInflater
                = (LayoutInflater)getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup, null);
        popupWindow = new PopupWindow(
                popupView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, false);
        popupWindow.setOutsideTouchable(false);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    public void  reportConfirmClick(View v)
    {
        // TODO: add user report logic here

        popupWindow.dismiss();
        messageDelete();
    }

    public void reportCancelClick(View v)
    {
        popupWindow.dismiss();
    }

    public void onCameraClick(){
        Intent cameraIntent = new Intent(this, cameraActivity.class);
        startActivity(cameraIntent);
    }

    private void messageDelete()
    {
        messageCount -= 1;

        if (messageCount <= 0)
        {
            setContentView(R.layout.intro);
        }
    }

}
