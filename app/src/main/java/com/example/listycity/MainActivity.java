package com.example.listycity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private ListView cityList;
    private CityArrayAdapter cityAdapter;

    // Firestore
    private FirebaseFirestore db;
    private CollectionReference citiesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start with empty list - Firestore will populate it
        dataList = new ArrayList<>();
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        citiesRef = db.collection("cities");

        // Snapshot listener - auto-updates list whenever Firestore changes
        citiesRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore", error.toString());
                return;
            }
            if (value != null) {
                dataList.clear();
                for (QueryDocumentSnapshot snapshot : value) {
                    String name = snapshot.getString("name");
                    String province = snapshot.getString("province");
                    dataList.add(new City(name, province));
                }
                cityAdapter.notifyDataSetChanged();
            }
        });

        // FAB - open Add City dialog
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            new AddCityFragment().show(getSupportFragmentManager(), "Add City");
        });

        // Single click - Delete city with confirmation dialog
        cityList.setOnItemClickListener((adapterView, view, position, id) -> {
            City selectedCity = dataList.get(position);
            showDeleteDialog(selectedCity);
        });
    }

    // Shows a confirmation dialog before deleting
    private void showDeleteDialog(City city) {
        new AlertDialog.Builder(this)
                .setTitle("Delete City")
                .setMessage("Delete " + city.getName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> deleteCity(city))
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Deletes from Firestore - snapshot listener will auto-update the list
    private void deleteCity(City city) {
        citiesRef.document(city.getName())
                .delete()
                .addOnSuccessListener(aVoid ->
                        Log.d("Firestore", city.getName() + " deleted successfully!"))
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Error deleting city", e));
    }

    // Called when AddCityFragment confirms add
    @Override
    public void addCity(City city) {
        citiesRef.document(city.getName())
                .set(city.toMap())
                .addOnSuccessListener(aVoid ->
                        Log.d("Firestore", city.getName() + " saved successfully!"))
                .addOnFailureListener(e ->
                        Log.e("Firestore", "Error saving city", e));
    }
}

//Citation:https://claude.ai/share/b6479cb0-4c38-455e-b70a-fd0380cbfc3d