package com.impactqa.utilities;

import java.util.List;

public class AppMessagingExcel {

    ExcelUtil excel = new ExcelUtil();
    List<String> values = null;
     String s[]= {"Information","Call","Website"};


    public List<String> readData(String exposerType) {


        if (exposerType.equals("No Exposure"))
            values = excel.getCellsValue(4, 2, 5);
        else if (exposerType.equals("Low Exposure"))
            values = excel.getCellsValue(4, 8, 11);
        else if (exposerType.equals("High Exposure"))
            values = excel.getCellsValue(4, 14, 17);
        else if (exposerType.equals("Verified Positive"))
            values = excel.getCellsValue(4, 20, 23);
        else if (exposerType.equals("Where is my code"))
            values = excel.getCellsValue(4, 26, 27);
        else if (exposerType.equals("Where is my Vaccine"))
            values = excel.getCellsValue(4, 32, 35);

        return values;
    }


    public void getValue(){




    }








}
