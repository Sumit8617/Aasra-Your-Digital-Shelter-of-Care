package com.example.babycare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private FusedLocationProviderClient fusedLocationClient;

    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    private ImageView topProfileImage;
    private TextView topUserName;
    private ImageView drawerProfileImage;
    private TextView drawerUserName;
    private TextView currentLocationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        auth = FirebaseAuth.getInstance();

        // Profile section
        topProfileImage = findViewById(R.id.top_profile_image);
        topUserName = findViewById(R.id.top_user_name);
        currentLocationText = findViewById(R.id.current_location_text);

        topProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
            startActivity(intent);
        });

        // Google sign-in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Drawer + Toolbar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set default title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Drawer Header Info
        View headerView = navigationView.getHeaderView(0);
        drawerProfileImage = headerView.findViewById(R.id.user_profile_image);
        drawerUserName = headerView.findViewById(R.id.user_name);
        TextView drawerUserEmail = headerView.findViewById(R.id.nav_header_subtitle);

        setUserProfileInfo();

        drawerProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Dashboard.this, ProfileActivity.class);
            startActivity(intent);
            drawerLayout.closeDrawers(); // close drawer if it's open
        });

        // Navigation actions
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                Toast.makeText(this, "Home clicked", Toast.LENGTH_SHORT).show();
            }else if (id == R.id.nav_child_care) {
                startActivity(new Intent(Dashboard.this, Child_Care_Activity.class));
            }else if (id == R.id.nav_medical_care) {
                startActivity(new Intent(Dashboard.this, Medical_Care.class));
            }else if (id == R.id.nav_elder_care) {
                startActivity(new Intent(Dashboard.this, Elder_Care.class));
            }else if (id == R.id.nav_house_keeping) {
                startActivity(new Intent(Dashboard.this, HomeServicesActivity.class));
            }else if (id == R.id.nav_profile) {
                startActivity(new Intent(Dashboard.this, ProfileActivity.class));
            } else if (id == R.id.nav_profile) {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show();
            }else if (id == R.id.nav_help) {
                startActivity(new Intent(Dashboard.this, HelpSupportActivity.class));
            } else if (id == R.id.nav_feedback) {
                startActivity(new Intent(Dashboard.this, FeedbackActivity.class));
            } else if (id == R.id.nav_logout) {
                auth.signOut();
                googleSignInClient.signOut().addOnCompleteListener(task -> {
                    Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }

            return true;
        });

        // ImageSlider
        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.elder, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.child, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.medical, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.home, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Categories
        findViewById(R.id.medicalCare).setOnClickListener(v -> {
            startActivity(new Intent(this, Medical_Care.class));
        });
        findViewById(R.id.elderCare).setOnClickListener(v -> {
            startActivity(new Intent(this, Elder_Care.class));
        });
        findViewById(R.id.childCare).setOnClickListener(v -> {
            startActivity(new Intent(this, Child_Care_Activity.class));
        });
        findViewById(R.id.homeServices).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeServicesActivity.class));
        });

        // Bottom nav (⬅️ UPDATED SECTION BELOW)
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(Dashboard.this, ProfileActivity.class));
            } else if (id == R.id.nav_booking) {
                startActivity(new Intent(Dashboard.this, BookingHistoryActivity.class));
            }
            return true;
        });

        // Location logic
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ImageButton locationBtn = findViewById(R.id.btn_location);
        EditText searchBar = findViewById(R.id.editTextText);

        locationBtn.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
            } else {
                getLastLocation(searchBar);
            }
        });
    }

    private void setUserProfileInfo() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser user = auth.getCurrentUser();

        String name = null;
        Uri photoUri = null;
        String email = null;

        if (account != null) {
            name = account.getDisplayName();
            photoUri = account.getPhotoUrl();
            email = account.getEmail();
        } else if (user != null) {
            name = user.getDisplayName();
            photoUri = user.getPhotoUrl();
            email = user.getEmail();
        }

        if (name != null) {
            topUserName.setText(name);
            drawerUserName.setText(name);
        }

        if (email != null) {
            TextView drawerUserEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle);
            drawerUserEmail.setText(email);
        }

        if (photoUri != null) {
            Glide.with(this).load(photoUri).transform(new CircleCrop()).into(topProfileImage);
            Glide.with(this).load(photoUri).transform(new CircleCrop()).into(drawerProfileImage);
        }
    }

    private void getLastLocation(EditText searchBar) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        String address = getAddressFromLocation(lat, lon);
                        currentLocationText.setText(address);
                        Toast.makeText(this, "Location fetched!", Toast.LENGTH_SHORT).show();
                    } else {
                        currentLocationText.setText("Location not available");
                    }
                });
    }

    private String getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                String street = address.getThoroughfare();
                String city = address.getLocality();
                String state = address.getAdminArea();

                if ((street == null || street.isEmpty()) &&
                        (city == null || city.isEmpty()) &&
                        (state == null || state.isEmpty())) {
                    return address.getAddressLine(0);
                }

                StringBuilder location = new StringBuilder();
                if (street != null && !street.isEmpty()) {
                    location.append(street).append(", ");
                }
                if (city != null && !city.isEmpty()) {
                    location.append(city).append(", ");
                }
                if (state != null && !state.isEmpty()) {
                    location.append(state);
                }

                String result = location.toString().trim();
                if (result.endsWith(",")) {
                    result = result.substring(0, result.length() - 1);
                }

                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown location";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EditText searchBar = findViewById(R.id.editTextText);
                getLastLocation(searchBar);
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
