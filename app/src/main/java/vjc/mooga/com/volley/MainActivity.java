package vjc.mooga.com.volley;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=%2290404%22&mode=json&units=metric&cnt=7";
    private static String TAG = MainActivity.class.getSimpleName();
    String jResponse;

    TextView tv;

    RequestQueue mRequestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        tv = (TextView) findViewById(R.id.textView);

        JsonObjectRequest obj = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        try{

                            JSONObject city = response.getJSONObject("city");
                            String name = city.getString("name");
                            String country = city.getString("country");
                            long id = city.getLong("id");


                            jResponse = "Name: " + name +"\n" +"Country: "+ country+ "\n ID: " + id+"\n \t Minimum Temp";
                            JSONArray list = response.getJSONArray("list");
                            for(int i=0;i<list.length();i++){
                                JSONObject objects = list.getJSONObject(i);
                                JSONObject temp = objects.getJSONObject("temp");
                                double min = temp.getDouble("min");

                                jResponse += "\n"+ min;
                            }

                            tv.setText(jResponse);
                        }catch(JSONException ex){
                            ex.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.toString());
            }
        });

        mRequestQueue.add(obj);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
