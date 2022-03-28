package Model;

//class defining all variables of the table
public class Item {
    private int id;
    private String item_name;
    private String item_description;
    private int item_selling_price;
    private int quantity;

    //empty constructor
    public Item() {
    }//end of empty constructor()

    //constructor without the id
    public Item( String item_name, String item_description,int quantity, int item_selling_price) {
        this.item_name = item_name;
        this.item_description = item_description;
        this.quantity = quantity;
        this.item_selling_price = item_selling_price;
    }// end of constructor() without the id

    //constructor with the id
    public Item(int id, String item_name, String item_description, int quantity, int item_selling_price) {
        this.id = id;
        this.item_name = item_name;
        this.item_description= item_description;
        this.quantity = quantity;
        this.item_selling_price = item_selling_price;
    }// end of constructor() with the id

//getter and setter for all variables
    public int    getId()                                          { return id;                                   }
    public void   setId(int id)                                    { this.id = id;                                }
    public int    getItemQuantity()                                { return quantity;                             }
    public void   setItemQuantity(int quantity)                    { this.quantity = quantity;                    }
    public String getItem_name()                                   { return item_name;                            }
    public void   setItem_name(String item_name)                   { this.item_name = item_name;                  }
    public String getItem_description()                            { return item_description;                     }
    public void   setItem_description(String item_description)     { this.item_description = item_description;    }
    public int getItem_selling_price()                             { return item_selling_price;                   }
    public void   setItem_selling_price(int item_selling_price)    { this.item_selling_price = item_selling_price;}
}// end class Person