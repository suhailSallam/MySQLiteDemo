package com.example.mysqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Controller.DatabaseHandler;
import Model.Item;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler db ;
    EditText edtItemName, edtItemDescription, edtItemQuantity, edtItemSellingPrice;
    TextView tvt_id, tvt_ItemName, tvt_ItemDescription, tvt_ItemQuantity,tvt_ItemSellingPrice;
    Button btn_insert, btn_update, btn_delete,btn_display,btn_next,btn_previous;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        findValuesByID();
        btn_insert.setOnClickListener(v -> {
            if (edtItemName != null)
               if (edtItemDescription != null)
                  if (edtItemQuantity != null)
                     if (edtItemSellingPrice !=null)
                        insertData();
                     else
                        Toast.makeText(MainActivity.this,R.string.enter_item_selling_price,Toast.LENGTH_SHORT).show();
                  else
                     Toast.makeText(MainActivity.this,R.string.enter_item_quantity,Toast.LENGTH_SHORT).show();
               else
                   Toast.makeText(MainActivity.this,R.string.enter_item_description,Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this,R.string.enter_item_name,Toast.LENGTH_SHORT).show();
        }); //end of setOnClickListener() btn_insert
        btn_update.setOnClickListener(v -> updateData()); //end of setOnClickListener() btn_update
        btn_display.setOnClickListener(v -> {
            clearFields();
            Toast.makeText(this, "Their is "+ countRecords(),Toast.LENGTH_SHORT).show();
            try {
                Item i = db.getItem(1);
                String myID = i.getId()+"";
                tvt_id.setText(myID);
                edtItemName.setText(i.getItem_name());
                edtItemDescription.setText(i.getItem_description());
                String myItemQuantity = i.getItemQuantity()+"";
                edtItemQuantity.setText(myItemQuantity);
                String myItemSellingPrice = i.getItem_selling_price()+"";
                edtItemSellingPrice.setText(myItemSellingPrice);
            }catch (Exception e) {
                System.out.println("Thrown exception: " + e.getMessage());
            }//end try catch
        }); //end of setOnClickListener() btn_display
        btn_next.setOnClickListener(v -> {
            int tmp_Id = Integer.parseInt(tvt_id.getText().toString());
            tmp_Id = tmp_Id + 1;
            Toast.makeText(MainActivity.this,"tmpId ="+tmp_Id,Toast.LENGTH_SHORT).show();
            Item i = db.getItem(tmp_Id);
            if ( i != null){
                String myID = i.getId()+"";
                tvt_id.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                tvt_id.setText(myID);
                edtItemName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                edtItemName.setText(i.getItem_name());
                edtItemDescription.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                edtItemDescription.setText(i.getItem_description());
                String myItemQuantity = i.getItemQuantity()+"";
                edtItemQuantity.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                edtItemQuantity.setText(myItemQuantity);
                edtItemSellingPrice.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                String myItemSellingPrice = i.getItem_selling_price()+"";
                edtItemSellingPrice.setText(myItemSellingPrice);
            } else {
                edtItemName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemName.setText(R.string.error_not_found);
                edtItemDescription.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemDescription.setText(R.string.error_not_found);
                edtItemQuantity.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemQuantity.setText(R.string.error_not_found);
                edtItemSellingPrice.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemSellingPrice.setText(R.string.error_not_found);
            }//end if else
        }); //end of setOnClickListener() btn_next
        btn_previous.setOnClickListener(v -> {
            int tmpId = Integer.parseInt(tvt_id.getText().toString());
            tmpId = tmpId - 1;
            Toast.makeText(MainActivity.this,"tmpId ="+tmpId,Toast.LENGTH_SHORT).show();
            Item i = db.getItem(tmpId);
            if ( i != null){
                String myID = i.getId()+"";
                tvt_id.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                tvt_id.setText(myID);
                edtItemName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                edtItemName.setText(i.getItem_name());
                edtItemDescription.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                edtItemDescription.setText(i.getItem_description());
                String myItemQuantity = i.getItemQuantity()+"";
                edtItemQuantity.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                edtItemQuantity.setText(myItemQuantity);
                edtItemSellingPrice.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
                String myItemSellingPrice = i.getItem_selling_price()+"";
                edtItemSellingPrice.setText(myItemSellingPrice);
            } else {
                edtItemName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemName.setText(R.string.error_not_found);
                edtItemDescription.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemDescription.setText(R.string.error_not_found);
                edtItemQuantity.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemQuantity.setText(R.string.error_not_found);
                edtItemSellingPrice.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                edtItemSellingPrice.setText(R.string.error_not_found);
            }//end if else
        }); //end of setOnClickListener() btn_previous
        btn_delete.setOnClickListener(v -> deleteData()); //end of setOnClickListener() btn_delete
    }//end of onCreate()
    public void insertData(){
        db.addItem(new Item(edtItemName.getText().toString()
                ,edtItemDescription.getText().toString()
                ,Integer.parseInt(edtItemQuantity.getText().toString())
                ,Integer.parseInt(edtItemSellingPrice.getText().toString())));
        List<Item> itemList = db.getItems();// just to use it
        itemList.size();// just to use it
        db.close();
    }//end of InsertData()
    public void updateData(){
        int tmp_id = Integer.parseInt(tvt_id.getText().toString());
        Item i = db.getItem(tmp_id);
        i.setItem_name(edtItemName.getText().toString());
        i.setItem_description(edtItemDescription.getText().toString());
        i.setItemQuantity(Integer.parseInt(edtItemQuantity.getText().toString()));
        i.setItem_selling_price(Integer.parseInt(edtItemSellingPrice.getText().toString()));
        db.updateItem(i);
//        int updateInfo = db.updateItem(i);
        db.close();
    }//end updateData()
    public void deleteData(){
        alertDialog = new AlertDialog.Builder(this);                                                    // to show the alert in this ContextView
        alertDialog.setTitle( getResources().getString(R.string.alert_title) );                                             //alert title
        alertDialog.setMessage(getResources().getString(R.string.alert_message));                                           //alert message
        alertDialog.setCancelable(false);                                                                                   //to be not cancelable - force shown
        //if click Yes do this
        alertDialog.setPositiveButton(getResources().getString(R.string.yes_button), (dialog, which) -> {
            Item i = db.getItem(db.getNumItem());
            db.deleteItem(i);
            db.close();
        }); //end of OnClickListener()
        alertDialog.setNegativeButton(getResources().getString(R.string.no_button), (dialog, which) -> {
            dialog.cancel(); // cancel the dialog and return to MainActivity
        }); //end of OnClickListener()
        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }//end of deleteData() function
    public int countRecords(){
        return db.getNumItem();
    }//end of countRecords() function
    public void findValuesByID(){
        edtItemName   = findViewById(R.id.edtItemName);
        edtItemDescription = findViewById(R.id.edtItemDescription);
        edtItemQuantity    = findViewById(R.id.edtItemQuantity);
        edtItemSellingPrice= findViewById(R.id.edtItemSellingPrice);

        tvt_id     = findViewById(R.id.tvt_id);
        tvt_ItemName   = findViewById(R.id.tvt_ItemName);
        tvt_ItemDescription  = findViewById(R.id.tvt_ItemDescription);
        tvt_ItemQuantity    = findViewById(R.id.tvt_ItemQuantity);
        tvt_ItemSellingPrice= findViewById(R.id.tvt_ItemSellingPrice);

        btn_insert = findViewById(R.id.btn_insert);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_display   = findViewById(R.id.btn_display);
        btn_next   = findViewById(R.id.btn_next);
        btn_previous = findViewById(R.id.btn_previous);
    }
    private void clearFields() {
        edtItemName.setText("");
        edtItemDescription.setText("");
        edtItemQuantity.setText("");
        edtItemSellingPrice.setText("");
    }
}//end class MainActivity