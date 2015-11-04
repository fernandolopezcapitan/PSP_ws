package com.salesianostriana.psp.picassotransformationreyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.ColorFilterTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import jp.wasabeef.picasso.transformations.MaskTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import jp.wasabeef.picasso.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.ToonFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.VignetteFilterTransformation;

public class PicassoTransformationsRecyclerView extends AppCompatActivity {


    //Array de transformaciones. Lo utilizaremos como colección para
    //pintarlos en la lista.
    Transformation[] transformaciones = new Transformation[]{
            new CropTransformation(),
            new CropCircleTransformation(),
            new CropSquareTransformation(),
            new RoundedCornersTransformation(10, 10),
            new ColorFilterTransformation(123),
            new GrayscaleTransformation(),
            new BlurTransformation(this),
            new MaskTransformation(this, android.R.drawable.ic_delete),
            new ToonFilterTransformation(this, (float) 0.1, (float) 0.1),
            new SepiaFilterTransformation(this),
            new ContrastFilterTransformation(this, (float) 4.0),
            new InvertFilterTransformation(this),
            new PixelationFilterTransformation(this),
            new SketchFilterTransformation(this),
            new SwirlFilterTransformation(this),
            new BrightnessFilterTransformation(this),
            new KuwaharaFilterTransformation(this),
            new VignetteFilterTransformation(this)
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_transformations_recycler_view);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.RecView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // Lo definimos como horizontal.
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        PicassoTransformationAdapter mAdapter = new PicassoTransformationAdapter(transformaciones, getBaseContext());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_picasso_transformations_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }







}
