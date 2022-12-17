package com.example.computech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.computech.Fragments.CarritoFragment;
import com.example.computech.Fragments.FavoritoFragment;
import com.example.computech.Fragments.InicioFragment;
import com.example.computech.Fragments.UbicacionFragment;
import com.example.computech.Fragments.UsuarioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class InicioAdminActivity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_admin);
        verFragment(new InicioFragment());
        mBottomNavigationView=findViewById(R.id.bottom_navigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.page_1) {
                    verFragment(new InicioFragment());
                }
                if (item.getItemId() == R.id.page_2) {
                    verFragment(new FavoritoFragment());
                }
                if (item.getItemId() == R.id.page_3) {
                    verFragment(new UbicacionFragment());
                }
                if (item.getItemId() == R.id.page_4) {
                    verFragment(new CarritoFragment());
                }
                if (item.getItemId() == R.id.page_5) {
                    verFragment(new UsuarioFragment());
                }
                return true;
            }
        });
    }
    private void verFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
    public void LogoutAdmin(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),InicioSesionActivity.class));
        finish();
    }

}
