package com.dbbest.databasemanager.loadingmanager.printers.mysql;

import com.dbbest.databasemanager.loadingmanager.constants.annotations.LoaderPrinterName;
import com.dbbest.databasemanager.loadingmanager.constants.attributes.AttributeSingleConstants;
import com.dbbest.exceptions.ContainerException;
import com.dbbest.xmlmanager.container.Container;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IndexPrinterHelperTest {

    @Test
    public void execute() throws ContainerException {
        Container tableContainer = new Container();
        tableContainer.addAttribute(AttributeSingleConstants.TABLE_NAME, "inventory");
        Container indexCategory = new Container();
        indexCategory.setName(LoaderPrinterName.TABLE_INDEXES);
        tableContainer.addChild(indexCategory);

        Container index1 = new Container();
        index1.addAttribute(AttributeSingleConstants.INDEX_NAME, "PRIMARY");
        Container index11 = new Container();
        index1.addChild(index11);
        index11.addAttribute(AttributeSingleConstants.INDEX_NAME, "PRIMARY");
        index11.addAttribute(AttributeSingleConstants.COLUMN_NAME, "inventory_id");
        index11.addAttribute(AttributeSingleConstants.SEQ_IN_INDEX, "1");
        index11.addAttribute(AttributeSingleConstants.SUB_PART, "null");
        index11.addAttribute(AttributeSingleConstants.INDEX_TYPE, "BTREE");

        Container index2 = new Container();
        index2.addAttribute(AttributeSingleConstants.INDEX_NAME, "idx_fk_film_id");
        Container index21 = new Container();
        index2.addChild(index21);
        index21.addAttribute(AttributeSingleConstants.INDEX_NAME, "idx_fk_film_id");
        index21.addAttribute(AttributeSingleConstants.COLUMN_NAME, "film_id");
        index21.addAttribute(AttributeSingleConstants.SEQ_IN_INDEX, "1");
        index21.addAttribute(AttributeSingleConstants.SUB_PART, "20");
        index21.addAttribute(AttributeSingleConstants.INDEX_TYPE, "null");

        Container index3 = new Container();
        index3.addAttribute(AttributeSingleConstants.INDEX_NAME, "idx_store_id_film_id");
        Container index31 = new Container();
        index3.addChild(index31);
        index31.addAttribute(AttributeSingleConstants.INDEX_NAME, "idx_store_id_film_id");
        index31.addAttribute(AttributeSingleConstants.COLUMN_NAME, "store_id");
        index31.addAttribute(AttributeSingleConstants.SEQ_IN_INDEX, "2");
        index31.addAttribute(AttributeSingleConstants.SUB_PART, "10");
        index31.addAttribute(AttributeSingleConstants.INDEX_TYPE, "BTREE");

        Container index32 = new Container();
        index3.addChild(index32);
        index32.addAttribute(AttributeSingleConstants.INDEX_NAME, "idx_store_id_film_id");
        index32.addAttribute(AttributeSingleConstants.COLUMN_NAME, "film_id");
        index32.addAttribute(AttributeSingleConstants.SEQ_IN_INDEX, "1");
        index32.addAttribute(AttributeSingleConstants.SUB_PART, "15");
        index32.addAttribute(AttributeSingleConstants.INDEX_TYPE, "BTREE");

        indexCategory.addChild(index1);
        indexCategory.addChild(index2);
        indexCategory.addChild(index3);

        IndexPrinterHelper indexPrinterBuilder = new IndexPrinterHelper();

        assertEquals(indexPrinterBuilder.execute(tableContainer), "INDEX idx_fk_film_id (film_id (20)),\n" +
            "INDEX idx_store_id_film_id USING BTREE (film_id (15), store_id (10)),\n");
    }
}