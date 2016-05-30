package com.around.around.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.around.around.R;
import com.around.around.model.People;
import com.around.around.service.UpdateUserLocation;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private Toolbar mToolbar;
    private AccountHeader mAccountHeader;
    private Drawer mNavigationDrawerLeft;
    private GoogleMap mGoogleMap;
    private double j = 0.000000000000001;
    private int arraySize;

    private Button btn_logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seta o mapa
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Seta o toolbar
        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Home");
        mToolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        setSupportActionBar(mToolbar);

        // Instacia o Account Header e seta os atributos
        mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withCompactStyle(false)
                .withHeaderBackground(getResources().getDrawable(R.drawable.header_bg))
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Fulano de Tal")
                                .withEmail("fulano.detal@gmail.com")
                                .withIcon(getResources().getDrawable(R.drawable.perfil))
                )
                .build();

        // Instacia o Navigation Drawer e seta os atributos
        mNavigationDrawerLeft = new DrawerBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withToolbar(mToolbar)
                .withDisplayBelowStatusBar(true)
                .withSelectedItem(1)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(mAccountHeader)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        return false;
                    }
                })
                .build();

        // Seta os itens do Navigation Drawer
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Home").withIdentifier(1).withIcon(getResources().getDrawable(R.mipmap.ic_home)));
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Histórico").withIdentifier(2).withIcon(getResources().getDrawable(R.mipmap.ic_calendar)));
        mNavigationDrawerLeft.addItem(new DividerDrawerItem());
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Configurações").withIdentifier(3).withIcon(getResources().getDrawable(R.mipmap.ic_settings)));
        mNavigationDrawerLeft.addItem(new PrimaryDrawerItem().withName("Sair").withIcon(getResources().getDrawable(R.mipmap.ic_exit_to_app)));

        final int delay = 4000;   // delay de 5 seg.
        final int interval = 4000;  // intervalo de 1 seg.

        final Timer timer = new Timer();

        Thread thread = new Thread() {
            @Override
            public void run() {
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {

                        try {
                            UpdateUserLocation.getInstance(MainActivity.this).getLocation(new IOnResult() {
                                @Override
                                public void onResult(ArrayList<People> peoples) {
                                    mGoogleMap.clear();
                                    insertUsers(peoples);
                                }

                                @Override
                                public void onFailed(String msg) {

                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, delay, interval);
            }
        };
        thread.start();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mGoogleMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.alerta)));
            }
        });
        //mGoogleMap.addPolygon(new PolygonOptions().)
    }

    private void insertUsers(ArrayList<People> peoples) {
        /*mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-8.039280 - j, 34.899300 - j))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.tag_red)));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-8.102020 - j, 33.899300 + j))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.tag_red)));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-8.036882 + j, -34.898184 - j))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.tag_red)));

        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(-8.036882 - j, -34.898184 + j))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.tag_red)));
        j++;*/

        if (peoples.size() > arraySize) {
            arraySize = peoples.size();
            Toast.makeText(this, peoples.get(arraySize - 1).getName() + " adicionada ao mapa...", Toast.LENGTH_SHORT).show();
            Log.i("NEW_USER", "Usuário: " + peoples.get(arraySize - 1).getName() + " adicionado ao mapa.");
        }
        for (int i = 0; i < peoples.size(); i++) {
            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(peoples.get(i).getLatitude() - j, peoples.get(i).getLongitude() + j)).title(peoples.get(i).getName())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tag_red)));
        }

    }
}
