package cabiso.daphny.com.gcompanion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cabiso.daphny.com.gcompanion.Fragments.About;
import cabiso.daphny.com.gcompanion.Fragments.Calendar;
import cabiso.daphny.com.gcompanion.Fragments.DIYCommunity;
import cabiso.daphny.com.gcompanion.Fragments.HomePage;
import cabiso.daphny.com.gcompanion.Fragments.InstantMessaging;
import cabiso.daphny.com.gcompanion.Fragments.Notification;
import cabiso.daphny.com.gcompanion.Fragments.Promos;
import cabiso.daphny.com.gcompanion.Fragments.SalesReport;
import cabiso.daphny.com.gcompanion.Fragments.WishLists;
import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;

    ImageView imageView;

    private final ClarifaiClient clarifaiClient = new ClarifaiBuilder("{b7aa33dc206c40a4b9cffc09a2e72a9d}").buildSync();

    // private final ClarifaiClient clarifaiClient = new ClarifaiBuilder(API_Credentials.CLIENT_ID,
    //    API_Credentials.CLIENT_SECRET).buildSync();

    private TextView tagText;
    private ArrayList<String> tags = new ArrayList<>();

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;

    private int[] tabIcons = {
            R.drawable.home_icon,
            R.drawable.diy_icon,
            R.drawable.promo_icon,
            R.drawable.chat_icon
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imgSave);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager vp_pages = (ViewPager) findViewById(R.id.vp_pages);
        PagerAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager());
        vp_pages.setAdapter(pagerAdapter);


        TabLayout tbl_pages = (TabLayout) findViewById(R.id.tbl_pages);
        tbl_pages.setupWithViewPager(vp_pages);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        for (int i = 0; i < tbl_pages.getTabCount(); i++) {
            tbl_pages.getTabAt(i).setIcon(tabIcons[i]);
        }

        tagText = (TextView) findViewById(R.id.tag_text);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);





        /*fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onClick(View view) {

                //   TODO: Snack bar for camera permission

              clearFields();
              //  Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
              //  startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

               // Intent iSecond=new Intent(MainActivity.this,HomePage.class);
                //startActivity(iSecond);

                dispatchTakePictureIntent();
                Snackbar.make(view, "Waiting.......", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                Log.d("Camera", "Fab 1");
                break;
            case R.id.fab2:

                Log.d("Camera", "Fab 2");
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("Camera", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("Camera","open");

        }
    }




    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public void clearFields() {
        tags.clear();
        tagText.setText("");
        ((ImageView)findViewById(R.id.picture)).setImageResource(android.R.color.transparent);
    }

    public void printTags() {
        String results = "First 3 tags: ";
        for(int i = 0; i < 3; i++) {
            results += "\n" + tags.get(i);
        }
        tagText.setText(results);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       InputStream inStream = null;

        //check if image was collected successfully
            if ((requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) &&
               resultCode == RESULT_OK) {
            try {
                inStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inStream);
                final ImageView preview = (ImageView)findViewById(R.id.picture);
                preview.setImageBitmap(bitmap);

                new AsyncTask<Bitmap, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {

                    // Model prediction
                    @Override
                    protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Bitmap... bitmaps) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        final ConceptModel general = clarifaiClient.getDefaultModels().generalModel();
                        return general.predict()
                                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(byteArray)))
                                .executeSync();
                    }

                    // Handling API response and then collecting and printing tags
                    @Override
                    protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "API contact error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final List<ClarifaiOutput<Concept>> predictions = response.get();
                        if (predictions.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "No results from API", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        final List<Concept> predictedTags = predictions.get(0).data();
                        for(int i = 0; i < predictedTags.size(); i++) {
                            tags.add(predictedTags.get(i).name());
                        }
                        printTags();
                    }
                }.execute(bitmap);
            } catch (FileNotFoundException e) {
                e.getMessage();
                Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_SHORT).show();
            } finally {
                if (inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            // User cancelled the image capture or selection.
            Toast.makeText(getApplicationContext(), "User Cancelled", Toast.LENGTH_SHORT).show();
        } else {
            // capture failed or did not find file.
            Toast.makeText(getApplicationContext(), "Unknown Failure. Please notify app owner.", Toast.LENGTH_SHORT).show();
        }
}



    /*    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == MainActivity.RESULT_OK) {
                Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                imageView.setImageBitmap(bitmap);

                new AsyncTask<Bitmap, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {

                    // Model prediction
                    @Override
                    protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Bitmap... bitmaps) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 90, stream);
                        byte[] byteArray = stream.toByteArray();
                        final ConceptModel general = clarifaiClient.getDefaultModels().generalModel();
                        return general.predict()
                                .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(byteArray)))
                                .executeSync();
                    }

                    // Handling API response and then collecting and printing tags
                    @Override
                    protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "API contact error", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final List<ClarifaiOutput<Concept>> predictions = response.get();
                        if (predictions.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "No results from API", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        final List<Concept> predictedTags = predictions.get(0).data();
                        for (int i = 0; i < predictedTags.size(); i++) {
                            tags.add(predictedTags.get(i).name());
                        }
                        printTags();
                    }
                }.execute(bitmap);
                throw new ClarifaiException("Maximum attempts reached of getting a default model.");
            }



        } else if (resultCode == RESULT_CANCELED) {
            // User cancelled the image capture or selection.
            Toast.makeText(getApplicationContext(), "User Cancelled", Toast.LENGTH_SHORT).show();
        } else {
            // capture failed or did not find file.
            Toast.makeText(getApplicationContext(), "Unknown Failure. Please notify app owner.", Toast.LENGTH_SHORT).show();
        }
        */

   //Problem: clarifai2.exception.ClarifaiException: Maximum attempts reached of getting a default model.












    static class FragmentAdapter extends FragmentPagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new HomePage();
                case 1:
                    return new DIYCommunity();
                case 2:
                    return new Promos();
                case 3:
                    return new InstantMessaging();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "Home";
                case 1:return "DIYs";
                case 2: return "Promos";
                case 3: return "Chat";
                default:return null;
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        display(item.getItemId());
        return true;
    }
    public void display(int itemID){
        Fragment fragment = null;
        android.support.v4.app.FragmentTransaction ft;
        switch (itemID){
            case R.id.nav_notif:
                fragment = new Notification();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
                break;
            case R.id.nav_calendar:
                fragment = new Calendar();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
                break;
            case R.id.nav_wishlist:
                fragment = new WishLists();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
                break;
            case R.id.nav_report:
                fragment = new SalesReport();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
                break;
            case R.id.nav_about:
                fragment = new About();
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame,fragment);
                ft.commit();
                break;
            case R.id.nav_logout:
//                finish();
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
//                        new ResultCallback<Status>() {
//                            @Override
//                            public void onResult(Status status) {
//                                Intent intent = new Intent(MainActivity.this, First.class);
//                                startActivity(intent);
//                            }
//                        });
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
