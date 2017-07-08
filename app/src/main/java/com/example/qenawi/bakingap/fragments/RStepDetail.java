package com.example.qenawi.bakingap.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qenawi.bakingap.R;
import com.example.qenawi.bakingap.StepDetailActivity;
import com.example.qenawi.bakingap.adapters.AdapterMultiView2;
import com.example.qenawi.bakingap.items.RecipeItem;
import com.example.qenawi.bakingap.items.StepItem;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;


public class RStepDetail extends Fragment implements ExoPlayer.EventListener,AdapterMultiView2.Click_listnerx
{

    private AdapterMultiView2 adapter;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private StepItem stepItem;
    private ArrayList<StepItem> stepItemArr;
    private RecipeItem recipeItem;
    private OnFragmentInteractionListener mListener;
    private int idx;
    private  Boolean Tab_view;
    //--------------------
    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer player;
    private MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private ImageView img;
    //---------------------
    public RStepDetail()
    {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        int Activity=0;
        try
        {
        if (getActivity().getClass().getSimpleName().equals(StepDetailActivity.class.getSimpleName()))
        {
            Activity=1;
        }
        }catch ( Exception e){e.printStackTrace();}
        if(Activity==1&&getActivity().getIntent()!=null)
        {
            stepItem =new StepItem();
            recipeItem =new RecipeItem();
            stepItemArr =new ArrayList<>();
            recipeItem =(RecipeItem)getActivity().getIntent().getExtras().getSerializable(getString(R.string.bundleK1));
            stepItemArr = recipeItem != null ? recipeItem.getStepItems() : null;
            idx=getActivity().getIntent().getExtras().getInt(getString(R.string.bundleK2));
           Tab_view=getActivity().getIntent().getExtras().getBoolean(getString(R.string.bundleK4));
            stepItem = stepItemArr.get(idx);
        }
        else if(getArguments()!=null)
        {
            stepItem =new StepItem();
            recipeItem =(RecipeItem)getArguments().getSerializable(getString(R.string.bundleK1));
            idx=getArguments().getInt(getString(R.string.bundleK2));
            Tab_view=getArguments().getBoolean(getString(R.string.bundleK4));
            stepItemArr = recipeItem != null ? recipeItem.getStepItems() : null;
            stepItem = stepItemArr.get(idx);
        }
        try {
            getActivity().setTitle(recipeItem.getName());
        }catch (Exception e){e.printStackTrace();}
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_rstep_detail, container, false);
        //--------------------------------------------------------------------------
        if (savedInstanceState!=null)
        {
           idx=savedInstanceState.getInt(getString(R.string.bundleK5));
            stepItem=stepItemArr.get(idx);
        }
        //--------------------------------------------------------------------------
        img=(ImageView)root.findViewById(R.id.imageView2);
        recyclerView =(RecyclerView) root.findViewById(R.id.recycle);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AdapterMultiView2(stepItem,getContext(),this);
        recyclerView.setAdapter(adapter);
        //---------------------------------------------
        exoPlayerView=(SimpleExoPlayerView)root.findViewById(R.id.Simple_exo);
        //------------------------------------------------------------------------
        chkSum();
       return root;
    }

    void  chkSum()
    {
        if(!stepItem.getVideoURL().equals(""))
        {
            img.setVisibility(View.INVISIBLE);
            exoPlayerView.setVisibility(View.VISIBLE);
            if (!Dim()&&!Tab_view)
            {
                recyclerView.setVisibility(View.INVISIBLE);
                ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
            }
            initializeMediaSession();
            inshlize_player();
        }
        else
        {
            exoPlayerView.setVisibility(View.INVISIBLE);
            img.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    Boolean Dim()
    {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height > width;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionSelectRecipe");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnClickList(int c)
    {
        if (c==0)
        {
            idx+=1;
            idx%= stepItemArr.size();
            stepItem=stepItemArr.get(idx);
            Log.v("oRx","nxt");
            restExoPlayer(0,false);
            chkSum();
            Log.v("oRx",stepItem.getDescription());
            adapter.changeDataSet(stepItem);
        }
         else
        {
                    idx-=idx==0?0:1;
                    restExoPlayer(0,false);
                    stepItem=stepItemArr.get(idx);
                    restExoPlayer(0,false);
                    chkSum();
                    idx%= stepItemArr.size();
                    Log.v("oRx",stepItem.getDescription());
                    adapter.changeDataSet(stepItem);
        }

        }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Object uri);
    }
    void  inshlize_player()
    {
        if(player==null)
        {
            // 1. Create a default TrackSelector
        //    BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter(); ->green screen on pause r2.0.1
       //     TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);

            TrackSelector trackSelector = new DefaultTrackSelector();//add vedio trac selection
            // 2. Create a default LoadControl
            LoadControl loadControl = new DefaultLoadControl();
            // 3. Create the player
            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            exoPlayerView.setPlayer( player);
            exoPlayerView.setKeepScreenOn(true);
            player.addListener(this);
       //     player.prepare(MediaSource("http://www.quirksmode.org/html5/videos/big_buck_bunny.mp4"));
             player.prepare(MediaSource(stepItem.getVideoURL()));
            player.setPlayWhenReady(true);
        }
    }
    MediaSource MediaSource (String url)
    {

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "ExoPlayer"));
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, null, null);
        return videoSource;
    }
    private void initializeMediaSession()
    {

        mediaSession = new MediaSessionCompat(getContext(), "SingleStepPage");

        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        mediaSession.setMediaButtonReceiver(null);
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                player.setPlayWhenReady(true);
            }

            @Override
            public void onPause() {
                player.setPlayWhenReady(false);
            }

            @Override
            public void onSkipToPrevious() {
                player.seekTo(0);
            }
        });
        mediaSession.setActive(true);
    }
    private void releasePlayer() {
        if (player != null)
        {
            player.stop();
            player.release();
            player = null;
        }
        if (mediaSession != null) {
            mediaSession.setActive(false);
        }
    }
    private void restExoPlayer(long position, boolean playWhenReady)
    {
       if(player==null)return;;
        player.seekTo(position);
        player.setPlayWhenReady(playWhenReady);
        releasePlayer();
    }
    //----exo_listners---------------
    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest)
    {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState)
    {
if (playbackState==ExoPlayer.STATE_BUFFERING)
{
    mListener.onFragmentInteraction(true);
    Log.v("ERX","buffer");
    stateBuilder.setState(PlaybackStateCompat.STATE_BUFFERING,player.getCurrentPosition(),1f);
}
else if (playbackState == ExoPlayer.STATE_READY&&playWhenReady)
{
    Log.v("ERX","bufferA");
    stateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,player.getCurrentPosition(),1f);
    mListener.onFragmentInteraction(true);
}
else if(playbackState == ExoPlayer.STATE_READY)
{
    Log.v("ERX","bufferB");
    stateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, player.getCurrentPosition(), 1f);
    mListener.onFragmentInteraction(true);
}
mediaSession.setPlaybackState(stateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters)
    {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (player!=null) player.setPlayWhenReady(false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (player!=null) player.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putInt(getString(R.string.bundleK5),idx);
        super.onSaveInstanceState(outState);
    }
}
