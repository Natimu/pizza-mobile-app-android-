package com.example.cheezytown;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cheezytown.databinding.ActivityMainBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final Map<Integer, Integer> pizzaPrice = new HashMap<>();
    private final Map<Integer, Integer> pizzaName = new HashMap<>();
     LinearLayout selectedPizza = null;
     double selectedPizzaPrice;
    @SuppressLint({"DefaultLocale", "SetTextI18n"})

    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    DecimalFormat formatDecimal = new DecimalFormat("#,###");


    @SuppressLint("StringFormatMatches")
    private void selectPizza(LinearLayout clickedLayout){
        if (selectedPizza != null){
            selectedPizza.setSelected(false);
        }
        clickedLayout.setSelected(true);
        selectedPizza = clickedLayout;

        Integer nameId = pizzaName.get(clickedLayout.getId());
        String selectedPizzaName;

        if (nameId != null){
            selectedPizzaName = getString(nameId);
        }else {
            selectedPizzaName = "Unknown Pizza";
        }

        Integer price = pizzaPrice.get(clickedLayout.getId());
         selectedPizzaPrice = (price != null) ? price : 0;

        TextView PizzaDis = binding.selectedPizzaDisplay;
        PizzaDis.setText(getString(R.string.formated_selected_pizza, selectedPizzaName));
        TextView PizzaPriceDis = binding.selectedPizzaPriceDis;
        String formatedPrice = currencyFormat.format(selectedPizzaPrice);
        PizzaPriceDis.setText(getString(R.string.formated_price, formatedPrice));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        Button clearButton = binding.clearButton;
        Button checkOut = binding.Checkout;
        RadioGroup pizzaRadioGroupSize = binding.radioForPizzaSize;
        CheckBox addGrilledChicken = binding.grilledChickenTopping;
        CheckBox addMushroomTopping = binding.mushroomTopping;
        CheckBox addExtraCheeseTopping = binding.extraCheeseTopping;
        CheckBox addPepperoniTopping = binding.pepperoniTopping;
        CheckBox addGroundBeefTopping = binding.groundBeefTopping;
        CheckBox addBaconTopping = binding.baconTopping;
        EditText numberOfPizzaInput = binding.pizzaQuantity;
        TextView totalPriceDisplay = binding.totalPriceView;
        TextView totalItemDisplay = binding.totalItemsView;

        pizzaPrice.put(R.id.margherita_pizza, 15);
        pizzaPrice.put(R.id.cheese, 12);
        pizzaPrice.put(R.id.pepperoni_pizza, 13);
        pizzaPrice.put(R.id.vegetariana, 10);
        pizzaPrice.put(R.id.quattro_formaggi, 14);

        pizzaName.put(R.id.margherita_pizza, R.string.margherita);
        pizzaName.put(R.id.cheese, R.string.cheese);
        pizzaName.put(R.id.pepperoni_pizza, R.string.pepperoni);
        pizzaName.put(R.id.vegetariana, R.string.vegetariana);
        pizzaName.put(R.id.quattro_formaggi, R.string.quattro_formaggi);

        for (int id: pizzaPrice.keySet()){
            LinearLayout pizzaLayout = findViewById(id);
            pizzaLayout.setOnClickListener(v -> selectPizza((LinearLayout) v));
        }

        clearButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                numberOfPizzaInput.setText("");
                addBaconTopping.setChecked(false);
                addGroundBeefTopping.setChecked(false);
                addGrilledChicken.setChecked(false);
                addPepperoniTopping.setChecked(false);
                addExtraCheeseTopping.setChecked(false);
                addMushroomTopping.setChecked(false);
                totalItemDisplay.setText(R.string.totalitems);
                totalPriceDisplay.setText(R.string.total_price);
                pizzaRadioGroupSize.check(R.id.smallPizza);

                Toast.makeText(MainActivity.this, "All filed are cleared", Toast.LENGTH_SHORT).show();


            }
        });


        checkOut.setOnClickListener(new View.OnClickListener() {
            double totalPrice;
            @SuppressLint({"DefaultLocale", "SetTextI18n", "StringFormatInvalid", "StringFormatMatches"})
            @Override
            public void onClick(View v) {

                EditText numberOfPizzaInput = binding.pizzaQuantity;
                String input = numberOfPizzaInput.getText().toString();
                int numberOfPizza = 0;
                if (!input.isEmpty()){
                    try{
                        numberOfPizza = Integer.parseInt(input);
                    }catch (NumberFormatException e){
                        Toast.makeText(MainActivity.this, "please select a valid pizza number", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "please insert pizza quantity", Toast.LENGTH_SHORT).show();
                }


                RadioGroup pizzaRadioGroupSize = binding.radioForPizzaSize;
                int selectedPizzaSize = pizzaRadioGroupSize.getCheckedRadioButtonId();


                CheckBox addGrilledChicken = binding.grilledChickenTopping;
                CheckBox addMushroomTopping = binding.mushroomTopping;
                CheckBox addExtraCheeseTopping = binding.extraCheeseTopping;
                CheckBox addPepperoniTopping = binding.pepperoniTopping;
                CheckBox addGroundBeefTopping = binding.groundBeefTopping;
                CheckBox addBaconTopping = binding.baconTopping;

                ArrayList<String> toppings = new ArrayList<>();

                if (addGrilledChicken.isChecked()) toppings.add("Grilled Chicken");
                if (addMushroomTopping.isChecked()) toppings.add("Mushroom");
                if (addExtraCheeseTopping.isChecked()) toppings.add("Extra Cheese");
                if (addPepperoniTopping.isChecked()) toppings.add("Pepperoni");
                if (addGroundBeefTopping.isChecked()) toppings.add("GroundBeef");
                if (addBaconTopping.isChecked()) toppings.add("Bacon");

                if(selectedPizzaPrice == 0){
                    Toast.makeText(MainActivity.this, "No pizza have been selected", Toast.LENGTH_SHORT).show();
                }

                if (selectedPizzaSize ==  R.id.smallPizza){
                    totalPrice = numberOfPizza * (selectedPizzaPrice + (toppings.size() * 0.5));
                } else if (selectedPizzaSize ==  R.id.mediumPizza) {
                    totalPrice = numberOfPizza * (selectedPizzaPrice + 3.99 + (toppings.size() * 1.0));
                }
                else if (selectedPizzaSize ==  R.id.largePizza) {
                    totalPrice = numberOfPizza *(selectedPizzaPrice + 5.99 + (toppings.size() * 1.5));
                }
                else if (selectedPizzaSize ==  R.id.eLargePizza) {
                    totalPrice = numberOfPizza *(selectedPizzaPrice + 7.99 + (toppings.size() * 2.0));
                }
                else {
                    Toast.makeText(MainActivity.this, "Please select a pizza size", Toast.LENGTH_SHORT).show();
                }

                TextView totalDis = binding.totalPriceView;
                String formatedTotalPrice = currencyFormat.format(totalPrice);
                totalDis.setText(getString(R.string.formated_total_price, formatedTotalPrice));

                TextView totalItem = binding.totalItemsView;
                String formatedTotalItem =formatDecimal.format(numberOfPizza);
                totalItem.setText(getString(R.string.formated_totalitems, formatedTotalItem));




            }
        });

    }


}