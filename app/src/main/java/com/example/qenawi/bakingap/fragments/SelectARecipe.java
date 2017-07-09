package com.example.qenawi.bakingap.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qenawi.bakingap.R;
import com.example.qenawi.bakingap.adapters.RecyclerViewAdapterMainActivity;
import com.example.qenawi.bakingap.items.IngredientItem;
import com.example.qenawi.bakingap.items.RecipeItem;
import com.example.qenawi.bakingap.items.StepItem;
import com.example.qenawi.bakingap.provider.Rcontract;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SelectARecipe extends Fragment implements RecyclerViewAdapterMainActivity.onClickListner
{

    private RecyclerViewAdapterMainActivity adapter;
    private RecyclerView rv;
    private OnFragmentInteractionSelectRecipe mListener;
    private  RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecipeItem>recipeItems;

    public SelectARecipe()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
      //  Json=loadJSONFromAsset();
        recipeItems=new ArrayList<>();
        try
        {
          GetJsonFromUrl A=new GetJsonFromUrl();
          A.execute("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
       //recipeItems  read it from bundle
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_select_arecipe, container, false);
        rv=(RecyclerView) root.findViewById(R.id.recipe_list);
        layoutManager=new GridLayoutManager(getContext(),2);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapterMainActivity(getContext(), this, recipeItems, 2);
        rv.setAdapter(adapter);
        return  root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onActioon(Object uri)
    {
        if (mListener != null)
        {
            mListener.onActionSelectRecipe(uri);
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionSelectRecipe) {
            mListener = (OnFragmentInteractionSelectRecipe) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionSelectRecipe");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(int Clickpos)
    {
        clean_add(Clickpos);
        mListener.onActionSelectRecipe(recipeItems.get(Clickpos));
    }

    public interface OnFragmentInteractionSelectRecipe
    {
        void onActionSelectRecipe(Object uri);
        void onAc();
    }

    private  void getData(String json) throws Exception
    {
        ArrayList<IngredientItem>ingredientItems;
        ArrayList<StepItem>stepItems;

        JSONArray arr,ingred,steps;
        JSONObject o,oi,os;
        RecipeItem f;
        IngredientItem ingredientItem;
        StepItem stepItem;
        arr = new JSONArray(json);
        for(int i=0;i<arr.length();i++) //recipi
        {
            o=arr.getJSONObject(i);
            f=new RecipeItem();
            f.setName(o.getString("name"));
            ingred=o.getJSONArray("ingredients");
            steps=o.getJSONArray("steps");
            ingredientItems=new ArrayList<>();
            stepItems=new ArrayList<>();
            for(int ii=0;ii<ingred.length();ii++)
            {
                oi=ingred.getJSONObject(ii);
                ingredientItem= new IngredientItem();
                ingredientItem.setMeasure(oi.getString("measure"));
                ingredientItem.setQuantity(oi.getInt("quantity"));
                ingredientItem.setIngredient(oi.getString("ingredient"));
                ingredientItems.add(ingredientItem);
            }
            for (int iii=0;iii<steps.length();iii++)
            {
                os=steps.getJSONObject(iii);
                stepItem=new StepItem();
                stepItem.setShortDescription(os.getString("shortDescription"));
                stepItem.setDescription(os.getString("description"));
                stepItem.setVideoURL(os.getString("videoURL"));
                stepItem.setThumbnailURL(os.getString("thumbnailURL"));
                stepItems.add(stepItem);
            }
            f.setServings(o.getInt("servings"));
            f.setImg_url(o.getString("image"));
            f.setIngredientItems(ingredientItems);
            f.setStepItems(stepItems);
            recipeItems.add(f);
        }
    adapter.notifyDataSetChanged();
    }
    void clean_add(int pos)
    {
    //    get();
        getActivity().getContentResolver().delete(Rcontract.CONTENT_URI, "ecoo", null);
     //   get();
        add_to_dp(pos);
        get();
    }
    void add_to_dp(int pos)
    {
        addH(recipeItems.get(pos).getName());
for(int i=0;i<recipeItems.get(pos).getIngredientItems().size();i++)
{
    add(recipeItems.get(pos).getIngredientItems().get(i),recipeItems.get(pos).getName());
}
    }
    public  void add(IngredientItem x,String Name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Rcontract.INGREDIENT_NAME,x.getIngredient()+" "+x.getQuantity()+" "+x.getMeasure());
        contentValues.put(Rcontract.RECIPE_NAME,Name);
        contentValues.put(Rcontract.RECIPE_TAG,"ecoo");
        getActivity().getContentResolver().insert(Rcontract.CONTENT_URI, contentValues);
    }
    public  void addH(String Name)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Rcontract.INGREDIENT_NAME,Name+'\n'+"INGREDIENTS:");
        contentValues.put(Rcontract.RECIPE_NAME,Name);
        contentValues.put(Rcontract.RECIPE_TAG,"ecoo");
        getActivity().getContentResolver().insert(Rcontract.CONTENT_URI, contentValues);
    }
    public  void get()
    {
        Log.v("Contra","geT-><>");
        Cursor result=getActivity().getContentResolver().query(Rcontract.CONTENT_URI,null,null,null,Rcontract.B_id);
        if (result!=null)
        {
            result.moveToFirst();
        }
        while (!result.isAfterLast())
        {
            Log.v("Contra",result.getString(result.getColumnIndex(Rcontract.INGREDIENT_NAME)));
            Log.v("Contra",result.getString(result.getColumnIndex(Rcontract.RECIPE_NAME)));
            Log.v("Contra",result.getString(result.getColumnIndex(Rcontract.RECIPE_TAG)));
            result.moveToNext();
        }

    }
void solv(String in)
{
    OkHttpClient client=new OkHttpClient();
    Request request=new Request.Builder().url(in).build();
    client.newCall(request).enqueue(new Callback()
    {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException
        {
            try {
                String G=response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}
    public class GetJsonFromUrl extends AsyncTask<Object,Void,String>
    {

        @Override
        protected String doInBackground(Object... params)
        {
            String RES="";
            try {
                RES= JSON_ST(Build_url((String)params[0]));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return RES;
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            mListener.onAc();
            try
            {
                getData(s);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //------------------------------------------------------

        public String readStream(InputStream in)
        {
            if (in ==null){return null;}
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));// b7ot data fe buffer 3a4an a3rf a2rha
            StringBuilder Sp=new StringBuilder();// 3a4an a5zn fe DAta eli ha2rha mn Buffer
            String Line;
            try {
                while ((Line=reader.readLine())!=null)
                {
                    Sp.append(Line).append("\n");
                    // read Line need CAtch Statment
                }
            }catch (IOException e) {//Do Some Thing}
            }finally
            {
                // Finaly is Done any way even if try and catch has return statmenT
                try {
                    in.close();
                    // Bardo close m7taga CAt4 3a4an mtDrp4
                }catch (IOException e)
                {
                    // Do Some Thing
                }
            }// finally
            return Sp.toString();
        }
        public URL Build_url(String S_t)
        {
            try {
                URL url;
                url = new URL(S_t);
                // 3a4an a3rf a3ml e new URL lzam Try we catc4
                return  url;
            }catch (IOException e){
                Log.e("TEST1","eror : ",e);}
            return null;
        }
        public String JSON_ST(URL url)
        {
            String RESULT = null;
            HttpURLConnection urlConnection=null;
            // EstaBli4 ConeCtion
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                RESULT=readStream(inputStream);
            } catch (IOException e) { e.printStackTrace();  // Do Some Thing
            }//cat4 end
            finally {
                if(urlConnection!=null)
                    urlConnection.disconnect();
            }
            return  RESULT;
        }
    }
    }
