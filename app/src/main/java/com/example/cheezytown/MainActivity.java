package com.example.cheezytown;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final Map<Integer, Integer> pizzaPrice = new HashMap<>();
     LinearLayout selectedPizza = null;
     double selectedPizzaPrice;
    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    void selectPizza(LinearLayout clickedLayout){
        if (selectedPizza != null){
            selectedPizza.setSelected(false);
        }
        clickedLayout.setSelected(true);
        selectedPizza = clickedLayout;

        Integer price = pizzaPrice.get(clickedLayout.getId());
         selectedPizzaPrice = (price != null) ? price : 0;


        TextView PizzaDis = findViewById(R.id.selectedPizzaDisplay);
        PizzaDis.setText("You have selected what? this ninja");
        TextView PizzaPriceDis = findViewById(R.id.selectedPizzaPrice);
        PizzaPriceDis.setText(String.format("Price: $%.2f", selectedPizzaPrice));


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Button clearButton = findViewById(R.id.clearButton);
        Button checkOut = findViewById(R.id.Checkout);
        RadioGroup pizzaRadioGroupSize = findViewById(R.id.radioForPizzaSize);
        CheckBox addGrilledChicken = findViewById(R.id.grilledChickenTopping);
        CheckBox addMushroomTopping = findViewById(R.id.mushroomTopping);
        CheckBox addExtraCheeseTopping = findViewById(R.id.extraCheeseTopping);
        CheckBox addPepperoniTopping = findViewById(R.id.pepperoniTopping);
        CheckBox addGroundBeefTopping = findViewById(R.id.groundBeefTopping);
        CheckBox addBaconTopping = findViewById(R.id.baconTopping);
        EditText numberOfPizzaInput = findViewById(R.id.pizzaQuantity);
        TextView totalPriceDisplay = findViewById(R.id.totalPriceView);
        TextView totalItemDisplay = findViewById(R.id.totalItemsView);

        pizzaPrice.put(R.id.margherita_pizza, 15);
        pizzaPrice.put(R.id.cheese, 12);
        pizzaPrice.put(R.id.pepperoni_pizza, 13);
        pizzaPrice.put(R.id.vegetariana, 10);
        pizzaPrice.put(R.id.quattro_formaggi, 14);

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
                totalItemDisplay.setText("Total Items: ");
                totalPriceDisplay.setText("Total Price: ");
                pizzaRadioGroupSize.check(R.id.smallPizza);

                Toast.makeText(MainActivity.this, "All filed are cleared", Toast.LENGTH_SHORT).show();


            }
        });


        checkOut.setOnClickListener(new View.OnClickListener() {
            double totalPrice;
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onClick(View v) {

                EditText numberOfPizzaInput = findViewById(R.id.pizzaQuantity);
                String input = numberOfPizzaInput.getText().toString();
                int numberOfPizza = 0;
                if (!input.isEmpty()){
                    try{
                        numberOfPizza = Integer.parseInt(input);
                    }catch (NumberFormatException e){
                        Toast.makeText(MainActivity.this, "please select a valid pizza number", Toast.LENGTH_SHORT).show();
                    }
                }


                RadioGroup pizzaRadioGroupSize = findViewById(R.id.radioForPizzaSize);
                int selectedPizzaSize = pizzaRadioGroupSize.getCheckedRadioButtonId();


                CheckBox addGrilledChicken = findViewById(R.id.grilledChickenTopping);
                CheckBox addMushroomTopping = findViewById(R.id.mushroomTopping);
                CheckBox addExtraCheeseTopping = findViewById(R.id.extraCheeseTopping);
                CheckBox addPepperoniTopping = findViewById(R.id.pepperoniTopping);
                CheckBox addGroundBeefTopping = findViewById(R.id.groundBeefTopping);
                CheckBox addBaconTopping = findViewById(R.id.baconTopping);

                ArrayList<String> toppings = new ArrayList<>();

                if (addGrilledChicken.isChecked()) toppings.add("Grilled Chicken");
                if (addMushroomTopping.isChecked()) toppings.add("Mushroom");
                if (addExtraCheeseTopping.isChecked()) toppings.add("Extra Cheese");
                if (addPepperoniTopping.isChecked()) toppings.add("Pepperoni");
                if (addGroundBeefTopping.isChecked()) toppings.add("GroundBeef");
                if (addBaconTopping.isChecked()) toppings.add("Bacon");

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

                TextView totalPriceDisplay = findViewById(R.id.totalPriceView);
                totalPriceDisplay.setText(String.format("Total Price: $%.2f", totalPrice));
                TextView totalItemDisplay = findViewById(R.id.totalItemsView);
                totalItemDisplay.setText(String.format("Total item: %d" , numberOfPizza));


            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }


}