package utils;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {
    @DataProvider(name="LoginData")
    public static String[][] getData() throws IOException{
        String path=".\\testData\\demo.xlsx";
        XLUtility utility=new XLUtility(path);
        int totalRows= utility.getRowCount("Sheet2");
        int totalCols=utility.getCellCount("Sheet2",1);

        String loginData[][]=new String[totalRows][totalCols];
        for (int i=1;i<=totalRows;i++){
            for (int j=0;j<totalCols;j++){
                loginData[i-1][j]= utility.getCellData("Sheet2",i,j);
            }
        }
        return loginData;

    }
}
